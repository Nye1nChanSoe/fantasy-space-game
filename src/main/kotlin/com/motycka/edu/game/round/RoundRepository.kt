package com.motycka.edu.game.round

import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.round.model.Round
import com.motycka.edu.game.round.model.RoundId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoundRepository: JpaRepository<Round, RoundId> {
    /**
     * Find all rounds belonging to a specific match.
     */
    fun findByMatch(match: Match): List<Round>
}