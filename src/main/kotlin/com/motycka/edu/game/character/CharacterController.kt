package com.motycka.edu.game.character

import com.motycka.edu.game.account.AccountService
import com.motycka.edu.game.character.model.CharacterClass
import com.motycka.edu.game.character.model.CharacterId
import com.motycka.edu.game.character.rest.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/characters")
class CharacterController(
    private val characterService: CharacterService,
    private val accountService: AccountService
) {
    @GetMapping
    fun getCharacters(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) characterClass: CharacterClass?
    ): ResponseEntity<List<CharacterResponse>>
    {
        val accountId = accountService.getCurrentAccountId()
        val characters = characterService.getCharacters(name, characterClass)
        return ResponseEntity.ok(characters.map { it.toCharacterResponse(accountId) })
    }

    @GetMapping("/{id}")
    fun getCharacterById(
        @PathVariable id: CharacterId
    ): ResponseEntity<CharacterResponse>
    {
        val accountId = accountService.getCurrentAccountId()
        val character = characterService.getCharacterById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(character.toCharacterResponse(accountId))
    }

    @PostMapping
    fun postCharacter(
        @RequestBody @Valid request: CharacterCreateRequest
    ): ResponseEntity<CharacterResponse>
    {
        request.validate()
        val accountId = accountService.getCurrentAccountId()
        val character = request.toCharacter(accountId)
        val newCharacter = characterService.createCharacter(character)
        return ResponseEntity.ok(newCharacter.toCharacterResponse(accountId))
    }

    @PutMapping("/{characterId}")
    fun updateCharacter(
        @PathVariable characterId: CharacterId,
        @RequestBody @Valid updateRequest: CharacterUpdateRequest
    ): ResponseEntity<CharacterResponse> {
        val updatedCharacter = characterService.updateCharacter(characterId, updateRequest)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(updatedCharacter.toCharacterResponse(characterId))
    }

    @GetMapping("/challengers")
    fun getChallengers()
        : ResponseEntity<List<CharacterResponse>>
    {
        val accountId = accountService.getCurrentAccountId()
        val challengers = characterService.getChallengers(accountId)
        return ResponseEntity.ok(challengers.map { it.toCharacterResponse(accountId) })
    }

    @GetMapping("/opponents")
    fun getOpponents()
        : ResponseEntity<List<CharacterResponse>>
    {
        val accountId = accountService.getCurrentAccountId()
        val opponents = characterService.getOpponents(accountId)
        return ResponseEntity.ok(opponents.map { it.toCharacterResponse(accountId) })
    }
}
