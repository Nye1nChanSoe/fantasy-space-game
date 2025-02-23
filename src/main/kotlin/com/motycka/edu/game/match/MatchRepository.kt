package com.motycka.edu.game.match

import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.match.model.MatchId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: JpaRepository<Match, MatchId>