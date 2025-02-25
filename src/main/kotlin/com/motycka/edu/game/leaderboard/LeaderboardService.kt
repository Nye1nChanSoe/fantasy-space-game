package com.motycka.edu.game.leaderboard

import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.leaderboard.rest.LeaderboardResponse
import com.motycka.edu.game.leaderboard.rest.toLeaderboardResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val leaderboardRepository: LeaderboardRepository
) {
    private val logger = LoggerFactory.getLogger(LeaderboardService::class.java)

    /**
     * Fetch the leaderboard sorted by position.
     * Optionally filter by character class.
     */
    fun getLeaderboard(
        characterClass: CharacterClass?,
        currentAccountId: Long
    ): List<LeaderboardResponse> {
        return try {
            val leaderboard = leaderboardRepository.findLeaderboard(characterClass)
                .mapIndexed { index, lb -> lb.copy(id = lb.id, character = lb.character, wins = lb.wins, losses = lb.losses, draws = lb.draws) }
                .mapIndexed { index, lb -> lb.toLeaderboardResponse(currentAccountId).copy(position = index + 1) }

            leaderboard
        } catch (ex: Exception) {
            logger.error("Failed to fetch leaderboard: ${ex.message}", ex)
            throw RuntimeException("Failed to fetch leaderboard.", ex)
        }
    }
}
