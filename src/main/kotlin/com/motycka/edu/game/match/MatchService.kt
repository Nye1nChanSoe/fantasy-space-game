package com.motycka.edu.game.match

import com.motycka.edu.game.character.CharacterRepository
import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.character.rest.toCharacterSummary
import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.match.model.MatchId
import com.motycka.edu.game.match.rest.MatchResponse
import com.motycka.edu.game.round.RoundRepository
import com.motycka.edu.game.round.model.Round
import com.motycka.edu.game.round.rest.toRoundResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val roundRepository: RoundRepository,
    private val characterRepository: CharacterRepository
) {
    /**
     * Fetch all matches.
     */
    fun getMatches(): List<Match> {
        return try {
            matchRepository.findAll()
        } catch (ex: Exception) {
            throw RuntimeException("Failed to fetch matches.", ex)
        }
    }

    /**
     * Fetch a match by id.
     */
    fun getMatchById(
        id: MatchId
    ): Match? {
        return matchRepository.findById(id).orElseThrow {
            NoSuchElementException("Match with id $id not found.")
        }
    }

    @Transactional
    fun simulateMatch(
        challengerId: CharacterId,
        opponentId: CharacterId,
        numRounds: Int
    ): MatchResponse {
        val challenger = characterRepository.findById(challengerId).orElseThrow {
            NoSuchElementException("Character with id $challengerId not found.")
        }
        val opponent = characterRepository.findById(opponentId).orElseThrow {
            NoSuchElementException("Character with id $opponentId not found.")
        }

        val match = Match(
            challenger = challenger,
            opponent = opponent,
            matchOutcome = "ONGOING",
            challengerXp = 0,
            opponentXp = 0
        )
        val savedMatch = matchRepository.save(match)

        val rounds = mutableListOf<Round>()
        var matchOutcome: String? = null

        var challengerExpGain = 0
        var opponentExpGain = 0

        for (i in 1..numRounds) {
            println(challenger)
            println(opponent)
            if (challenger.isDefeated()) {
                matchOutcome = "OPPONENT_WON"
                break
            } else if (opponent.isDefeated()) {
                matchOutcome = "CHALLENGER_WON"
                break
            }

            challenger.beforeRound()
            opponent.beforeRound()

            val challengerDamage = challenger.attack(opponent)
            val challengerRound = Round(
                match = savedMatch,
                roundNumber = i,
                character = challenger,
                healthDelta = challengerDamage,
                staminaDelta = challenger.stamina ?: 0,
                manaDelta = challenger.mana ?: 0
            )
            challengerExpGain += challengerDamage

            rounds.add(challengerRound)
            roundRepository.save(challengerRound)

            if (opponent.isDefeated()) {
                challengerExpGain += 50
                matchOutcome = "CHALLENGER_WON"
                break
            }

            val opponentDamage = opponent.attack(challenger)
            val opponentRound = Round(
                match = savedMatch,
                roundNumber = i,
                character = opponent,
                healthDelta = -opponentDamage,
                staminaDelta = challenger.stamina ?: 0,
                manaDelta = challenger.mana ?: 0
            )

            opponentExpGain += opponentDamage

            rounds.add(opponentRound)
            roundRepository.save(opponentRound)

            if (challenger.isDefeated()) {
                opponentExpGain += 50
                matchOutcome = "OPPONENT_WON"
                break
            }
        }

        if (matchOutcome == null) {
            challengerExpGain += 25
            opponentExpGain += 25
            matchOutcome = "DRAW"
        }

        challenger.experience += challengerExpGain
        opponent.experience += opponentExpGain

        val updatedMatch = savedMatch.copy(
            matchOutcome = matchOutcome,
            challengerXp = challengerExpGain,
            opponentXp = opponentExpGain
        )
        matchRepository.save(updatedMatch)

        val roundResponses = rounds.map { it.toRoundResponse() }

        return MatchResponse(
            id = requireNotNull(updatedMatch.id) { "Match id is null." },
            challenger = challenger.toCharacterSummary(challengerExpGain),
            opponent = opponent.toCharacterSummary(opponentExpGain),
            rounds = roundResponses,
            matchOutcome = matchOutcome,
        )
    }
}