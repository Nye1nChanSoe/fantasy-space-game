package com.motycka.edu.game.round.rest

import com.motycka.edu.game.round.model.Round

fun Round.toRoundResponse(): RoundResponse {
    return RoundResponse(
        round = roundNumber,
        characterId = requireNotNull(character.id) { "Character ID must not be null" },
        healthDelta = healthDelta,
        staminaDelta = staminaDelta,
        manaDelta = manaDelta
    )
}