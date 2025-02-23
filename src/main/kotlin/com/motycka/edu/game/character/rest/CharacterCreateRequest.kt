package com.motycka.edu.game.character.rest

import com.motycka.edu.game.character.model.CharacterClass
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Request object for creating a new character.
 */
data class CharacterCreateRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    val name: String,

    @field:NotNull(message = "Character class is required")
    val characterClass: CharacterClass,

    @field:Min(value = 1, message = "Health must be at least 1")
    @field:Max(value = 1000, message = "Health must be at most 1000")
    val health: Int,

    @field:Min(value = 1, message = "Attack power must be at least 1")
    @field:Max(value = 300, message = "Attack power must be at most 300")
    val attackPower: Int,

    /** Warrior */
    val defensePower: Int? = null,
    val stamina: Int? = null,

    /** Sorcerer */
    val healingPower: Int? = null,
    val mana: Int? = null,
) {
    fun totalPoints(): Int {
        return health + attackPower + (defensePower ?: 0) + (stamina ?: 0) + (healingPower ?: 0) + (mana ?: 0)
    }

    fun validate() {
        require(totalPoints() <= 200) { "Total attribute points cannot exceed 200 for new characters." }
    }
}
