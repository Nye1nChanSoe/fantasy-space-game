package com.motycka.edu.game.character.rest

import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.character.model.CharacterId

data class CharacterSummary(
    val id: CharacterId,
    val name: String,
    val characterClass: CharacterClass,
    val level: String,
    val experienceTotal: Int,
    val experienceGained: Int
)

/**
 *   "challenger": {
 *     "id": "1",
 *     "name": "Aragorn",
 *     "characterClass": "WARRIOR",
 *     "level": "5",
 *     "experienceTotal": 2000,
 *     "experienceGained": 100
 *   },
 */