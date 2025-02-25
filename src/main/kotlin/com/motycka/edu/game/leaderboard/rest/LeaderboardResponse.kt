package com.motycka.edu.game.leaderboard.rest

import com.motycka.edu.game.character.rest.CharacterResponse

data class LeaderboardResponse(
    val position: Int,
    val character: CharacterResponse,
    val wins: Int,
    val losses: Int,
    val draws: Int
)
