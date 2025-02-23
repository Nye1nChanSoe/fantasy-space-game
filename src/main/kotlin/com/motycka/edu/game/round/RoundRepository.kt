package com.motycka.edu.game.round

import com.motycka.edu.game.round.model.Round
import com.motycka.edu.game.round.model.RoundId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoundRepository: JpaRepository<Round, RoundId>