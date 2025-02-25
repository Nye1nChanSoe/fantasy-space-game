package com.motycka.edu.game.leaderboard

import com.motycka.edu.game.account.AccountService
import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.leaderboard.rest.LeaderboardResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/leaderboards")
class LeaderboardController(
    private val leaderboardService: LeaderboardService,
    private val accountService: AccountService
) {
    /**
     * Fetch the leaderboard sorted by position.
     * Allows filtering by character class.
     */
    @GetMapping
    fun getLeaderboard(
        @RequestParam(required = false) characterClass: CharacterClass?,
    ): ResponseEntity<List<LeaderboardResponse>> {
        val  currentAccountId = accountService.getCurrentAccountId()
        val leaderboard = leaderboardService.getLeaderboard(characterClass, currentAccountId)
        return ResponseEntity.ok(leaderboard)
    }
}
