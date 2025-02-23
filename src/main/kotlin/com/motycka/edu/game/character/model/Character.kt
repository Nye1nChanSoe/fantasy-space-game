package com.motycka.edu.game.character.model

import com.motycka.edu.game.account.model.AccountId
import jakarta.persistence.*

@Entity
@Table(name = "character") // Matches DB table name
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: CharacterId? = null,

    @Column(name = "account_id", nullable = false)
    val accountId: AccountId,

    @Column(name = "name", nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "class", nullable = false)
    val characterClass: CharacterClass,

    @Column(name = "health", nullable = false)
    var health: Int,

    @Column(name = "attack", nullable = false)
    var attackPower: Int,

    @Column(name = "experience", nullable = false)
    var experience: Int = 0,

    @Column(name = "defense")
    var defensePower: Int? = null,

    @Column(name = "level", nullable = false)
    var level: Int = 1,

    @Column(name = "stamina")
    var stamina: Int? = null,

    @Column(name = "healing")
    var healingPower: Int? = null,

    @Column(name = "mana")
    var mana: Int? = null,
) {
    constructor() : this(
        id = null,
        accountId = 0,
        name = "",
        characterClass = CharacterClass.WARRIOR,
        health = 0,
        attackPower = 0,
        experience = 0,
        level = 1,
        defensePower = 25,
        stamina = 25,
        healingPower = 25,
        mana = 25,
    )

    companion object {
        private const val LEVEL_UP_XP = 1000
    }

    val shouldLevelUp: Boolean
        get() = experience >= level * LEVEL_UP_XP

    fun isWarrior() = characterClass == CharacterClass.WARRIOR
    fun isSorcerer() = characterClass == CharacterClass.SORCERER
}