package intermediaries

import data.Game
import data.Purchase
import data.User
import interfaces.Intermediary
import repositories.PurchaseRepository
import java.time.LocalDate
import java.time.LocalTime

class EpicGames : Company(),  Intermediary {

    override fun processPurchase(user: User, game: Game) {
        val time = LocalTime.now()

        val commission: Double = if(time.hour in 20..23)
            game.price.times(0.01)
        else
            game.price.times(0.03)

        if(user.getMoney() >= game.price.plus(commission)) {
            val purchase = Purchase(
                id = PurchaseRepository.getTotalPurchases().plus(1).toLong(),
                userId = user.getId(), gameId = game.id,
                amount = game.price.plus(commission),
                createdDate = LocalDate.now().toString()
            )

            if(PurchaseRepository.add(purchase)) {
                user.makePurchase(purchase)
                println("********* COMPRA REALIZADA CON ÉXITO *********")
                applyCashback(user, purchase)
            } else
                println("EL JUEGO SELECCIONADO YA SE ENCUENTRA EN LA BIBLIOTECA DE JUEGOS." +
                        "\nSELECCIONE UN JUEGO DISTINTO.")

        } else {
            println("¡FONDOS INSUFICIENTES! Te faltan recargar: $${game.price.plus(commission) - user.getMoney()}")
        }

    }

}
