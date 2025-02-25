package com.motycka.edu.game.leaderboard

import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.leaderboard.model.Leaderboard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LeaderboardRepository : JpaRepository<Leaderboard, Long> {

    /**
     * Fetch leaderboard sorted by wins. Optionally filter by character class.
     */
    @Query(
        "SELECT l FROM Leaderboard l " +
                "JOIN FETCH l.character c " +
                "WHERE (:characterClass IS NULL OR c.characterClass = :characterClass) " +
                "ORDER BY l.wins DESC"
    )
    fun findLeaderboard(@Param("characterClass") characterClass: CharacterClass?): List<Leaderboard>

    /**
     * Find leaderboard entry by character ID.
     */
    @Query("SELECT l FROM Leaderboard l WHERE l.character.id = :characterId")
    fun findByCharacterId(@Param("characterId") characterId: Long): Leaderboard?
}
