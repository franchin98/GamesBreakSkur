package intermediaries

import data.Purchase
import data.User
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate

abstract class Company{

    protected fun applyCashback(user: User, purchase: Purchase) {
        fun calculateCashback(percentage: Double) {
            val format = DecimalFormat("#.##")
            format.roundingMode = RoundingMode.DOWN

            if(percentage == 0.0)
                println("**** No aplica cashback. ****")
            else {
                val cashback = format.format(purchase.amount.times(percentage))
                user.chargeMoney(purchase.amount.times(percentage))
                println("**** Cashback otorgado: $$cashback ****")
            }
        }

        when(LocalDate.now().toEpochDay().minus(user.getCreatedDate().toEpochDay())) {
            in 91L..365L -> calculateCashback(0.03)
            in 1L .. 90L -> calculateCashback(0.05)
            else -> {calculateCashback(0.0)}
        }
    }
}
