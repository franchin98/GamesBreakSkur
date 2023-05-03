package intermediaries

import data.Game
import data.User
import interfaces.Intermediary

class Nakama : Company(), Intermediary {
    override fun processPurchase(user: User, game: Game) {
        // implementen este perritos
    }

}
