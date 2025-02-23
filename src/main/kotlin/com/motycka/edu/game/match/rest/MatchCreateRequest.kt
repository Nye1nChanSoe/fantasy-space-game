package com.motycka.edu.game.match.rest

import com.motycka.edu.game.character.model.CharacterId

data class MatchCreateRequest(
    val rounds: Int,
    val challengerId: CharacterId,
    val opponentId: CharacterId
)

/**
 * {
 *   "rounds": 10,
 *   "challengerId": 1,
 *   "opponentId": 2
 * }
 */