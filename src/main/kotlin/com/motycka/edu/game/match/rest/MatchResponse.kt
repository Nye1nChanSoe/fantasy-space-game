package com.motycka.edu.game.match.rest

import com.motycka.edu.game.character.rest.CharacterSummary
import com.motycka.edu.game.match.model.MatchId
import com.motycka.edu.game.round.rest.RoundResponse

data class MatchResponse(
    val id: MatchId,
    val challenger: CharacterSummary,
    val opponent: CharacterSummary,
    val rounds: List<RoundResponse>,
    val matchOutcome: String
)

/**
 * {
 *   "id": "1",
 *   "challenger": {
 *     "id": "1",
 *     "name": "Aragorn",
 *     "characterClass": "WARRIOR",
 *     "level": "5",
 *     "experienceTotal": 2000,
 *     "experienceGained": 100
 *   },
 *   "opponent": {
 *     "id": "2",
 *     "name": "Gandalf",
 *     "characterClass": "SORCERER",
 *     "level": "5",
 *     "experienceTotal": 2000,
 *     "experienceGained": 100
 *   },
 *   "rounds": [
 *     {
 *       "round": 1,
 *       "characterId": "1",
 *       "healthDelta": -10,
 *       "staminaDelta": -5,
 *       "manaDelta": 0
 *     },
 *     {
 *       "round": 1,
 *       "characterId": "2",
 *       "healthDelta": -5,
 *       "staminaDelta": 0,
 *       "manaDelta": -10
 *     }
 *   ],
 *   "matchOutcome": "CHALLENGER_WON"
 * }
 */