package tests.gamebreak.repository

import data.User
import org.junit.Assert.*
import org.junit.Test
import repositories.UserRepository

class UserRepositoryTest {

    @Test
    fun `allow the user to log in`() {
        //given
        val repository: UserRepository = givenARepositoryOfUsers()

        val userCaptured: User?
        //when
        userCaptured = whenTheUserLogsin(repository, "AHOZ", "lock_password")

        thenReturnsA_ValidUser(userCaptured, repository)

    }

    @Test
    fun `should be null if the user does not exist`() {
        //given
        val repository = givenARepositoryOfUsers()

        //when
        val anUserNotRegistered = whenTheUserLogsin(repository, "Sin", "Registrar")

        //then is null
        assertNull(anUserNotRegistered)

    }


    private fun thenReturnsA_ValidUser(userCaptured: User?, repository: UserRepository) {
        assertEquals(userCaptured, repository.getUsers()[1])
    }

    private fun whenTheUserLogsin(repository: UserRepository, nickName: String, password: String): User? =
        repository.login("$nickName", "$password")


    private fun givenARepositoryOfUsers(): UserRepository = UserRepository
}