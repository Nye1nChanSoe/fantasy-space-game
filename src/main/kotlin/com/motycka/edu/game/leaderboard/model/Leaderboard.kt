package com.motycka.edu.game.leaderboard.model

import com.motycka.edu.game.character.model.Character
import jakarta.persistence.*

@Entity
@Table(name = "leaderboard")
data class Leaderboard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    val character: Character,

    @Column(nullable = false)
    var wins: Int,

    @Column(nullable = false)
    var losses: Int,

    @Column(nullable = false)
    var draws: Int
) {
    constructor() : this(0, Character(), 0, 0, 0)
}
