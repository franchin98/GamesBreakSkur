package interfaces

import data.Game
import data.User

interface Intermediary {
    fun processPurchase(user: User, game: Game)
}
