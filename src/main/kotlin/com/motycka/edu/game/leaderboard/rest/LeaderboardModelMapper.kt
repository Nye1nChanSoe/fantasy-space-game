package com.motycka.edu.game.leaderboard.rest

import com.motycka.edu.game.character.rest.toCharacterResponse
import com.motycka.edu.game.leaderboard.model.Leaderboard

/**
 * Maps domain model to response DTO.
 */
fun Leaderboard.toLeaderboardResponse(currentAccountId: Long) = LeaderboardResponse(
    position = 0,
    character = character.toCharacterResponse(currentAccountId),
    wins = wins,
    losses = losses,
    draws = draws
)