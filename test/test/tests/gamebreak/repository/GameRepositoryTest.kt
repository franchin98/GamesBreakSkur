package tests.gamebreak.repository

import data.Game
import org.junit.Assert.*
import org.junit.Test
import repositories.GameRepository

class GameRepositoryTest {

    @Test
    fun `should search for a game by id`() {
        //given
        val repository: GameRepository = given_a_repositoryGame()
        //when
        val gameCaptured = whenLookingForAGameById(1L, repository)
        //then
        thenTheExpectedGameIsReceived(gameCaptured, repository.getById(1L))
    }


    private fun thenTheExpectedGameIsReceived(gameExpected: Game?, gameValue: Game?) {
        assertEquals(gameExpected, gameValue)
    }

    private fun whenLookingForAGameById(id: Long, repository: GameRepository): Game? = repository.getById(id)

    private fun given_a_repositoryGame(): GameRepository = GameRepository

}