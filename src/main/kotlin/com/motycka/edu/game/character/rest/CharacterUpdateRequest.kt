package com.motycka.edu.game.character.rest

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Request object for character level up
 */


data class CharacterUpdateRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    val name: String,

    @field:Min(value = 1, message = "Health must be at least 1")
    @field:Max(value = 1000, message = "Health must be at most 1000")
    val health: Int,

    @field:Min(value = 1, message = "Attack power must be at least 1")
    @field:Max(value = 300, message = "Attack power must be at most 300")
    val attackPower: Int,

    // Warrior-specific fields
    val defensePower: Int? = null,
    val stamina: Int? = null,

    // Sorcerer-specific fields
    val mana: Int? = null,
    val healingPower: Int? = null
)
