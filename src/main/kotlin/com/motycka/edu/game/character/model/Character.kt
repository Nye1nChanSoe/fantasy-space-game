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

    // Convenience secondary constructor
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
        private const val LEVEL_UP_XP = 500
    }

    val shouldLevelUp: Boolean
        get() = experience >= level * LEVEL_UP_XP

    fun isWarrior() = characterClass == CharacterClass.WARRIOR
    fun isSorcerer() = characterClass == CharacterClass.SORCERER

    /**
     * High-level attack entry point. Redirects to the warrior or sorcerer logic.
     */
    fun attack(opponent: Character): Int {
        return if (isWarrior()) warriorAttack(opponent) else sorcererAttack(opponent)
    }

    /**
     * Warrior attack logic. Uses stamina, reduces stamina by 2 if available.
     */
    private fun warriorAttack(opponent: Character): Int {
        val currentStamina = stamina ?: 0
        if (currentStamina < 2) {
            return 0
        }

        stamina = (currentStamina - 2).coerceAtLeast(0)
        val damage = (attackPower - (opponent.defensePower ?: 0)).coerceAtLeast(1)

        opponent.takeDamage(damage)
        return damage
    }

    /**
     * Sorcerer attack logic. Uses mana, reduces mana by 2 if available.
     */
    private fun sorcererAttack(opponent: Character): Int {
        val currentMana = mana ?: 0
        if (currentMana < 2) {
            return 0
        }

        mana = (currentMana - 2).coerceAtLeast(0)

        opponent.takeDamage(attackPower)
        return attackPower
    }

    /**
     * Called when this character takes damage. Warriors benefit from defensePower.
     */
    fun takeDamage(damage: Int) {
        val finalDamage = if (isWarrior()) {
            (damage - (defensePower ?: 0)).coerceAtLeast(1)
        } else {
            damage
        }
        health = (health - finalDamage).coerceAtLeast(0)
    }

    /**
     * Sorcerer heal. Costs 5 mana and can’t exceed total health of 100.
     */
    fun heal(): Int {
        if (!isSorcerer()) return 0

        val currentMana = mana ?: 0
        if (currentMana < 5) {
            return 0
        }
        mana = (currentMana - 5).coerceAtLeast(0)

        val healAmount = (healingPower ?: 0)
            .coerceAtMost(100 - health)
            .coerceAtLeast(0) // ensure no negative

        health += healAmount
        return healAmount
    }

    /**
     * Regenerates resource before each round.
     * Warriors: stamina between 2..5, max 30
     * Sorcerers: mana between 2..4, max 20, optionally auto-heal if health < 80
     */
    fun beforeRound() {
        if (isWarrior()) {
            val regen = (2..5).random()
            stamina = ((stamina ?: 0) + regen).coerceAtMost(30)
        } else if (isSorcerer()) {
            val regen = (2..4).random()
            mana = ((mana ?: 0) + regen).coerceAtMost(20)

            val hpThreshold = (100 * 0.8).toInt()
            if (health < hpThreshold && (mana ?: 0) >= 5) {
                heal()
            }
        }
    }

    fun isDefeated(): Boolean = health <= 0
}