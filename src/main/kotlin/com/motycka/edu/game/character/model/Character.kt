package com.motycka.edu.game.character.model

import com.motycka.edu.game.account.model.AccountId
import jakarta.persistence.*
import kotlin.math.max
import kotlin.math.min

@Entity
@Table(name = "character")
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
    open var defensePower: Int? = null,

    @Column(name = "level", nullable = false)
    var level: Int = 1,

    @Column(name = "stamina")
    open var stamina: Int? = null,

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

    fun attack(opponent: Character): Int {
        return if (isWarrior()) warriorAttack(opponent) else sorcererAttack(opponent)
    }

    private fun warriorAttack(opponent: Character): Int {
        if (stamina == null || stamina!! <= 0) return 0

        stamina = max(stamina!! - 5, 0)
        val damage = max(attackPower - (opponent.defensePower ?: 0), 1)
        opponent.takeDamage(damage)

        return damage
    }

    private fun sorcererAttack(opponent: Character): Int {
        if (mana == null || mana!! <= 0) return 0

        mana = max(mana!! - 5, 0)
        opponent.takeDamage(attackPower)

        return attackPower
    }

    fun takeDamage(damage: Int) {
        val reducedDamage = if (isWarrior()) {
            max(damage - (defensePower ?: 0), 1)
        } else {
            damage
        }
        health = max(health - reducedDamage, 0)
    }

    fun heal(): Int {
        if (!isSorcerer() || mana == null || mana!! < 5) return 0

        mana = max(mana!! - 5, 0)
        val healAmount = min(healingPower ?: 0, 100 - health)
        health += healAmount

        return healAmount
    }

    fun beforeRound() {
        if (isWarrior()) {
            stamina = min((stamina ?: 0) + 3, 25)
        } else if (isSorcerer()) {
            mana = min((mana ?: 0) + 3, 20)
            if (mana!! >= 5) {
                heal()
            }
        }
    }

    fun isDefeated(): Boolean = health <= 0
}