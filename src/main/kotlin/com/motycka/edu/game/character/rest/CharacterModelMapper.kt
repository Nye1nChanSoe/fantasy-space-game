package com.motycka.edu.game.character.rest

import com.motycka.edu.game.account.model.AccountId
import com.motycka.edu.game.character.model.Character

/**
 * Maps between the REST model and the domain model for characters.
 */
fun CharacterCreateRequest.toCharacter(
    accountId: AccountId
) = Character(
    id = null,
    accountId = accountId,
    name = name,
    health = health,
    attackPower = attackPower,
    stamina = stamina,
    defensePower = defensePower,
    mana = mana,
    healingPower = healingPower,
    characterClass = characterClass,
    experience = 0,
)

/**
 * Maps domain model to response DTO
 */
fun Character.toCharacterResponse(currentAccountId: Long) = CharacterResponse(
    id = requireNotNull(id) { "Character ID must not be null" },
    name = name,
    health = health,
    attackPower = attackPower,
    stamina = stamina,
    defensePower = defensePower,
    mana = mana,
    healingPower = healingPower,
    characterClass = characterClass,
    experience = experience,
    level = "LEVEL_" + level.toString(),
    shouldLevelUp = shouldLevelUp,
    isOwner = accountId == currentAccountId
)

fun Character.toCharacterSummary(expGained: Int) = CharacterSummary(
    id = requireNotNull(id) { "Character ID must not be null" },
    name = name,
    characterClass = characterClass,
    level = level.toString(),
    experienceTotal = experience - expGained,
    experienceGained = expGained
)


