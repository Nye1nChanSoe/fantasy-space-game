package com.motycka.edu.game.character

import com.motycka.edu.game.account.model.AccountId
import com.motycka.edu.game.character.model.Character
import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.character.rest.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CharacterService(
    private val characterRepository: CharacterRepository
) {
    /**
     * Fetch all characters or
     * filter by name and/or class.
     */
    fun getCharacters(
        name: String? = "",
        characterClass: CharacterClass? = null
    ) : List<Character> {
        return try {
            if (name == null && characterClass == null) {
                characterRepository.findAll()
            } else {
                characterRepository.findByFilters(name, characterClass)
            }
        } catch (ex: Exception) {
            throw RuntimeException("Failed to fetch characters.", ex)
        }
    }

    /**
     * Fetch a character by id.
     */
    fun getCharacterById(
        id: CharacterId
    ): Character? {
        return characterRepository.findById(id).orElseThrow {
            NoSuchElementException("Character with id $id not found.")
        }
    }

    /**
     * Create a character.
     */
    @Transactional
    fun createCharacter(
        character: Character
    ): Character {
        if (characterRepository.findByName(character.name) != null) {
            throw IllegalArgumentException("Character with name ${character.name} already exists.")
        }
        validateCharacterAttributes(character)

        return try {
            characterRepository.save(character)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to create character.", ex)
        }
    }

    /**
     * Get all characters for an account.
     */
    fun getChallengers(
        accountId: AccountId
    ) : List<Character> {
        return try {
            characterRepository.findChallengers(accountId)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to fetch characters for account $accountId.", ex)
        }
    }

    /**
     * Get all characters that are opponents for an account.
     */
    fun getOpponents(
        accountId: AccountId
    ) : List<Character> {
        return try {
            characterRepository.findOpponents(accountId)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to fetch characters for account $accountId.", ex)
        }
    }

    @Transactional
    fun updateCharacter(
        characterId: CharacterId,
        updateRequest: CharacterUpdateRequest
    ): Character? {
        val character = characterRepository.findById(characterId)
            .orElse(null) ?: return null

        character.apply {
            name = updateRequest.name
            health = updateRequest.health
            attackPower = updateRequest.attackPower
        }

        when {
            character.isWarrior() -> {
                character.defensePower = updateRequest.defensePower
                character.stamina = updateRequest.stamina
            }
            character.isSorcerer() -> {
                character.mana = updateRequest.mana
                character.healingPower = updateRequest.healingPower
            }
        }

        val levelUpSuccess = character.shouldLevelUp && characterRepository.levelUpCharacter(characterId) > 0
        if (levelUpSuccess) {
            character.level++
        }

        return characterRepository.save(character)
    }


    /**
     * Business rules for character creation.
     */
    private fun validateCharacterAttributes(character: Character) {
        when (character.characterClass) {
            CharacterClass.WARRIOR -> {
                requireNotNull(character.stamina) {"Warrior must have stamina."}
                requireNotNull(character.defensePower) {"Warrior must have defense power."}
                require(character.mana == null) {"Warrior cannout have mana."}
                require(character.healingPower == null) {"Warrior cannot have healing power."}
            }
            CharacterClass.SORCERER -> {
                requireNotNull(character.mana) {"Sorcerer must have mana."}
                requireNotNull(character.healingPower) {"Sorcerer must have healing power."}
                require(character.stamina == null) {"Sorcerer cannot have stamina."}
                require(character.defensePower == null) {"Sorcerer cannot have defense power."}
            }
        }
    }
}
