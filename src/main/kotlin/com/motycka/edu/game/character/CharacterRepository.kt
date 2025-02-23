package com.motycka.edu.game.character

import com.motycka.edu.game.character.model.Character
import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.character.model.CharacterId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CharacterRepository: JpaRepository<Character, CharacterId> {

    fun findByCharacterClass(characterClass: CharacterClass): List<Character>

    fun findByName(name: String): Character?

    /**
     * Filters characters by class or name.
     * Ensures column names match DB.
     */
    @Query(
        "SELECT c FROM Character c " +
                "WHERE (:name IS NULL OR c.name = :name) " +
                "AND (:characterClass IS NULL OR c.characterClass = :characterClass)"
    )
    fun findByFilters(
        @Param("name") name: String?,
        @Param("characterClass") characterClass: CharacterClass?
    ): List<Character>

    /**
     * Retrieves all characters for a given account.
     */
    @Query(
        "SELECT c FROM Character c" +
        " WHERE c.accountId = :accountId"
    )
    fun findChallengers(
        @Param("accountId") accountId: Long
    ): List<Character>

    /**
     * Retrieves all characters that are not owned
     * by the given account.
     */
    @Query(
        "SELECT c FROM Character c" +
         " WHERE c.accountId != :accountId"
    )
    fun findOpponents(
        @Param("accountId") accountId: Long
    ): List<Character>

    /**
     * Handle level up
     */
    @Modifying
    @Transactional
    @Query(
        "UPDATE Character c " +
        "SET c.level = c.level + 1 " +
        "WHERE c.id = :characterId AND c.experience >= c.level * 500"
    )
    fun levelUpCharacter(@Param("characterId") characterId: Long): Int
}
