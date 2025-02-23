package com.motycka.edu.game.round.rest

import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.round.model.RoundId

data class RoundResponse(
    val round: Int,
    val characterId: CharacterId,
    val healthDelta: Int,
    val staminaDelta: Int,
    val manaDelta: Int
)