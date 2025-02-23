package com.motycka.edu.game.match.model

import com.motycka.edu.game.character.model.Character
import com.motycka.edu.game.round.model.Round
import jakarta.persistence.*

@Entity
@Table(name = "match")
data class Match (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: MatchId? = null,

    @ManyToOne
    @JoinColumn(name = "challenger_id", nullable = false)
    val challenger: Character,

    @ManyToOne
    @JoinColumn(name = "opponent_id", nullable = false)
    val opponent: Character,

    @Column(name = "match_outcome", nullable = false)
    val matchOutcome: String,

    @Column(name = "challenger_xp", nullable = false)
    val challengerXp: Int,

    @Column(name = "opponent_xp", nullable = false)
    val opponentXp: Int,

    @OneToMany(mappedBy = "match", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val rounds: List<Round> = mutableListOf()
) {
    constructor() : this(
        id = null,
        challenger = Character(),
        opponent = Character(),
        matchOutcome = "",
        challengerXp = 0,
        opponentXp = 0
    )
}