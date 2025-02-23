package com.motycka.edu.game.match

import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.character.rest.CharacterCreateRequest
import com.motycka.edu.game.match.model.Match
import com.motycka.edu.game.match.model.MatchId
import com.motycka.edu.game.match.rest.MatchCreateRequest
import com.motycka.edu.game.match.rest.MatchResponse
import com.motycka.edu.game.match.rest.toMatchResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/matches")
class MatchController(
    private val matchService: MatchService
) {
    /**
     * Fetch all matches.
     */
    @GetMapping
    fun getMatches(): ResponseEntity<List<MatchResponse>> {
        val matches = matchService.getMatches()
        return ResponseEntity.ok(matches.map { it.toMatchResponse() })
    }

    /**
     * Fetch a match by id.
     */
    @GetMapping("/{id}")
    fun getMatchById(
        id: MatchId
    ): ResponseEntity<Match?> {
        return ResponseEntity.ok(matchService.getMatchById(id))
    }

    /**
     * Simulate a match between two characters.
     */
    @PostMapping
    fun simulateMatch(
        @RequestBody @Valid request: MatchCreateRequest
    ): ResponseEntity<MatchResponse> {
        val challengerId = request.challengerId
        val opponentId = request.opponentId
        val numRounds = request.rounds
        return ResponseEntity.ok(matchService.simulateMatch(challengerId, opponentId, numRounds))
    }
}