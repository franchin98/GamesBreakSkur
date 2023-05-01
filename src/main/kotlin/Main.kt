import app.Application
import repositories.GameRepository
import repositories.UserRepository

fun main() {
    Application.run(GameRepository, UserRepository)

}