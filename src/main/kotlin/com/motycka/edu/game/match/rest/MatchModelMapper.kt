package com.motycka.edu.game.match.rest

import com.motycka.edu.game.character.rest.toCharacterSummary
import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.round.model.Round
import com.motycka.edu.game.round.rest.toRoundResponse

fun Match.toMatchResponse(): MatchResponse {
    return MatchResponse(
        id = requireNotNull(id) { "Character ID must not be null" },
        challenger = challenger.toCharacterSummary(challengerXp),
        opponent = opponent.toCharacterSummary(opponentXp),
        rounds = rounds.map { it.toRoundResponse() },
        matchOutcome = matchOutcome
    )
}