package data

import repositories.PurchaseRepository
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate


class User(private val id: Long, private val nickName: String,
           private val password: String, private val name: String,
           private val surname: String, private var money: Double = 0.0,
           createdDateAux: String ) {

    private val purchasedGames: MutableList<Purchase> = mutableListOf()
    private val createdDate: LocalDate

    init {
        for(purchase in PurchaseRepository.get()){
            if(purchase.userId == this.id)
                purchasedGames.add(purchase)
        }

        createdDate = LocalDate.of(
            createdDateAux.substring(0..3).toInt(),
            createdDateAux.substring(5..6).toInt(),
            createdDateAux.substring(8..9).toInt())
    }

    fun makePurchase(purchase: Purchase) {
        purchasedGames.add(purchase)
        this.money -= purchase.amount
    }

    fun getPurchasedGames(): List<Purchase> = purchasedGames

    fun getNickname(): String = this.nickName

    fun getPassword(): String = this.password

    fun getMoney(): Double = this.money

    fun getNameComplete(): String = "${this.name} ${this.surname}"

    fun getCreatedDate(): LocalDate = createdDate

    fun getId(): Long = id
    fun chargeMoney(amount: Double) {
         if (amount > 0.0)
             this.money += amount
    }

    fun showMoney(): String {
        val format = DecimalFormat("#.##")
        format.roundingMode = RoundingMode.DOWN
        val money = format.format(this.money)
        return "$$money"
    }

}
