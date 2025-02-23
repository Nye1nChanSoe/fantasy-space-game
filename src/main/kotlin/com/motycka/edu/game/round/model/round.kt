package com.motycka.edu.game.round.model

import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.character.model.Character
import jakarta.persistence.*


@Entity
@Table(name = "round")
data class Round(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    val match: Match,

    @Column(name = "round_number", nullable = false)
    val roundNumber: Int,

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    val character: Character,

    @Column(name = "health_delta", nullable = false)
    val healthDelta: Int,

    @Column(name = "stamina_delta", nullable = false)
    val staminaDelta: Int,

    @Column(name = "mana_delta", nullable = false)
    val manaDelta: Int
) {
    constructor() : this(
        id = null,
        match = Match(),
        roundNumber = 0,
        character = Character(),
        healthDelta = 0,
        staminaDelta = 0,
        manaDelta = 0
    )
}