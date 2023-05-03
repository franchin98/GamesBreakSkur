package intermediaries

import data.Game
import data.Purchase
import data.User
import interfaces.Intermediary
import repositories.PurchaseRepository
import java.time.LocalDate

class Steam : Company(),  Intermediary {

    override fun processPurchase(user: User, game: Game) {
        val commission: Double = game.price.times(0.02)

        if(user.getMoney() >= game.price.plus(commission)) {
            val purchase =
                Purchase( id = PurchaseRepository.getTotalPurchases().plus(1).toLong(),
                        userId = user.getId(), gameId = game.id,
                        amount = game.price.plus(commission),
                        createdDate = LocalDate.now().toString())

            if(PurchaseRepository.add(purchase)) {
                user.makePurchase(purchase)
                println("********* COMPRA REALIZADA CON ÉXITO *********")
                applyCashback(user, purchase)
            } else
                println("EL JUEGO SELECCIONADO YA SE ENCUENTRA EN LA BIBLIOTECA DE JUEGOS." +
                        "\nSELECCIONE UN JUEGO DISTINTO.")

        } else
            println("¡FONDOS INSUFICIENTES! Te faltan recargar: $${game.price.plus(commission) - user.getMoney()}")

    }

}
