package com.motycka.edu.game.match

import com.motycka.edu.game.character.CharacterRepository
import com.motycka.edu.game.character.model.Character
import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.character.rest.toCharacterSummary
import com.motycka.edu.game.leaderboard.LeaderboardRepository
import com.motycka.edu.game.leaderboard.model.Leaderboard
import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.match.model.MatchId
import com.motycka.edu.game.match.rest.MatchResponse
import com.motycka.edu.game.round.RoundRepository
import com.motycka.edu.game.round.model.Round
import com.motycka.edu.game.round.rest.toRoundResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val roundRepository: RoundRepository,
    private val characterRepository: CharacterRepository,
    private val leaderboardRepository: LeaderboardRepository
) {
    private val logger = LoggerFactory.getLogger(MatchService::class.java)

    /**
     * Fetch all matches.
     */
    fun getMatches(): List<Match> = try {
        matchRepository.findAll()
    } catch (ex: Exception) {
        throw RuntimeException("Failed to fetch matches.", ex)
    }

    /**
     * Fetch a match by ID.
     */
    fun getMatchById(id: MatchId): Match = matchRepository.findById(id).orElseThrow {
        NoSuchElementException("Match with ID $id not found.")
    }

    /**
     * Simulate a match between two characters.
     * Updates leaderboard with results.
     */
    @Transactional
    fun simulateMatch(
        challengerId: CharacterId,
        opponentId: CharacterId,
        numRounds: Int
    ): MatchResponse {
        val challenger = getCharacterById(challengerId)
        val opponent = getCharacterById(opponentId)

        val savedMatch = matchRepository.save(
            Match(
                challenger = challenger,
                opponent = opponent,
                matchOutcome = "ONGOING",
                challengerXp = 0,
                opponentXp = 0
            )
        )

        val matchOutcome = executeRounds(savedMatch, challenger, opponent, numRounds)

        val updatedMatch = finalizeMatch(savedMatch, matchOutcome, challenger, opponent)
        matchRepository.save(updatedMatch)

        updateLeaderboard(challenger, opponent, matchOutcome) // ✅ Update Leaderboard

        return buildMatchResponse(updatedMatch)
    }

    /**
     * Retrieves a character by ID or throws an exception.
     */
    private fun getCharacterById(id: CharacterId): Character =
        characterRepository.findById(id).orElseThrow {
            NoSuchElementException("Character with ID $id not found.")
        }

    /**
     * Executes rounds and determines match outcome.
     */
    private fun executeRounds(
        match: Match,
        challenger: Character,
        opponent: Character,
        numRounds: Int
    ): String {
        val rounds = mutableListOf<Round>()
        var matchOutcome: String? = null

        for (roundNum in 1..numRounds) {
            if (challenger.isDefeated()) {
                matchOutcome = "OPPONENT_WON"
                break
            }
            if (opponent.isDefeated()) {
                matchOutcome = "CHALLENGER_WON"
                break
            }

            playRound(match, roundNum, challenger, opponent, rounds)
            playRound(match, roundNum, opponent, challenger, rounds)

            if (challenger.isDefeated()) {
                matchOutcome = "OPPONENT_WON"
                break
            }
            if (opponent.isDefeated()) {
                matchOutcome = "CHALLENGER_WON"
                break
            }
        }

        return matchOutcome ?: "DRAW"
    }

    /**
     * Handles a single round of combat.
     */
    private fun playRound(
        match: Match,
        roundNumber: Int,
        attacker: Character,
        defender: Character,
        rounds: MutableList<Round>
    ) {
        attacker.beforeRound()

        val damageDealt = attacker.attack(defender)
        val round = Round(
            match = match,
            roundNumber = roundNumber,
            character = attacker,
            healthDelta = -damageDealt,
            staminaDelta = attacker.stamina ?: 0,
            manaDelta = attacker.mana ?: 0
        )

        rounds.add(round)
        roundRepository.save(round)
    }

    /**
     * Finalizes the match, updates XP, and returns the updated match entity.
     */
    private fun finalizeMatch(
        match: Match,
        matchOutcome: String,
        challenger: Character,
        opponent: Character
    ): Match {
        val (challengerXp, opponentXp) = calculateExperienceGain(matchOutcome)

        challenger.experience += challengerXp
        opponent.experience += opponentXp

        return match.copy(
            matchOutcome = matchOutcome,
            challengerXp = challengerXp,
            opponentXp = opponentXp
        )
    }

    /**
     * Updates the leaderboard with match results.
     */
    private fun updateLeaderboard(challenger: Character, opponent: Character, matchOutcome: String) {
        val challengerLeaderboard = challenger.id?.let { leaderboardRepository.findByCharacterId(it) }
            ?: Leaderboard(character = challenger, wins = 0, losses = 0, draws = 0)

        val opponentLeaderboard = opponent.id?.let { leaderboardRepository.findByCharacterId(it) }
            ?: Leaderboard(character = opponent, wins = 0, losses = 0, draws = 0)

        when (matchOutcome) {
            "CHALLENGER_WON" -> {
                challengerLeaderboard.wins += 1
                opponentLeaderboard.losses += 1
            }
            "OPPONENT_WON" -> {
                opponentLeaderboard.wins += 1
                challengerLeaderboard.losses += 1
            }
            "DRAW" -> {
                challengerLeaderboard.draws += 1
                opponentLeaderboard.draws += 1
            }
        }

        leaderboardRepository.save(challengerLeaderboard)
        leaderboardRepository.save(opponentLeaderboard)
    }

    /**
     * Determines experience gain based on match outcome.
     */
    private fun calculateExperienceGain(matchOutcome: String): Pair<Int, Int> = when (matchOutcome) {
        "CHALLENGER_WON" -> Pair(50, 0)
        "OPPONENT_WON" -> Pair(0, 50)
        "DRAW" -> Pair(25, 25)
        else -> Pair(0, 0) // fallback
    }

    /**
     * Builds the MatchResponse object.
     */
    private fun buildMatchResponse(match: Match): MatchResponse {
        val rounds = roundRepository.findByMatch(match).map { it.toRoundResponse() }

        return MatchResponse(
            id = requireNotNull(match.id) { "Match ID is null." },
            challenger = match.challenger.toCharacterSummary(match.challengerXp),
            opponent = match.opponent.toCharacterSummary(match.opponentXp),
            rounds = rounds,
            matchOutcome = match.matchOutcome
        )
    }
}
