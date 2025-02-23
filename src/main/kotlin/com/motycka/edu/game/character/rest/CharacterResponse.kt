package com.motycka.edu.game.character.rest

import com.motycka.edu.game.character.model.Character
import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.character.model.CharacterId

/**
 * Response object for character creation.
 */
data class CharacterResponse (
    val id: CharacterId,
    val name: String,
    val health: Int,
    val attackPower: Int,
    val stamina: Int?,
    val defensePower: Int?,
    val mana: Int?,
    val healingPower: Int?,
    val characterClass: CharacterClass,
    val level: String,
    val experience: Int,
    val shouldLevelUp: Boolean,
    val isOwner: Boolean,
)

// TODO: remove
fun calculateLevel(experience: Int): Int {
    return (experience / 1000) + 1
}