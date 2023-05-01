package tests.gamebreak.repository

import data.Purchase
import org.junit.Assert
import org.junit.Test
import repositories.PurchaseRepository

class PurchaseRepositoryTest {

    @Test
    fun `que pueda registrar una nueva compra`() {
        //Given
        val repository: PurchaseRepository = givenAPurchaseRepository()

        //when-then
        whenAddANewPurchaseTheMethodsReturnsTrue(repository)

    }

    @Test
    fun `que no pueda registrar una nueva compra si el usuario ya tiene el mismo juego`(){
       //Given
        val repository = givenAPurchaseRepository()

        //when
        val purchase = Purchase(12, 1504, 1, 350.50, "2023/02/03")
        cuandoIntentaComprarUnMismoJuego(repository, purchase)

        //then
        entoncesElMetodoDevuelveFalsoYNoAgregaLaCompra(repository, purchase)


    }

    private fun entoncesElMetodoDevuelveFalsoYNoAgregaLaCompra(repository: PurchaseRepository, purchase: Purchase) {
        Assert.assertFalse(repository.add(purchase))
    }

    private fun cuandoIntentaComprarUnMismoJuego(repository: PurchaseRepository, purchase: Purchase) {
        repository.add(purchase)
    }

    private fun whenAddANewPurchaseTheMethodsReturnsTrue(repository: PurchaseRepository) {
        Assert.assertTrue(repository.
                    add(Purchase(11, 1510, 12, 50.75, "2023/04/29"))
        )
    }

    private fun givenAPurchaseRepository(): PurchaseRepository = PurchaseRepository

}