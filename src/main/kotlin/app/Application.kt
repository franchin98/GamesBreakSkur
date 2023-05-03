package app

import data.Game
import data.User
import interfaces.Intermediary
import intermediaries.EpicGames
import intermediaries.Nakama
import intermediaries.Steam
import repositories.GameRepository
import repositories.UserRepository

class Application {
    companion object {

        fun run(gameRepository: GameRepository, userRepository: UserRepository) {
            loginMenu(gameRepository, userRepository)
        }

        private fun loginMenu(games: GameRepository, userRepository: UserRepository) {
            print("********** Bienvenido **********" +
                    "\nIngrese su nombre de usuario: ")
            val inputNickname = readln()

            print("Ingrese su contraseña: ")
            val inputPassword = readln()

            val user = userRepository.login(inputNickname, inputPassword)

            if (user != null)
                mainMenu(user)
            else {
                println("\nIdentificación incorrecta.")
                var response: Int

                println("¿Desea registrarse?")

                do {
                    print("\n- Ingrese 1 para registrarse" +
                            "\n- Ingrese 2 para salir" +
                            "\nIngrese su respuesta: ")
                    response = readln().toInt()

                    if(response < 1 || response > 2)
                        println("\nOpción incorrecta")

                } while(response < 1 || response > 2)

                if(response == 1)
                    //siginUpMenu(userRepository)
                else{
                    println("\n¡Hasta luego!\n")
                    run(games, userRepository)
                }
            }

        }

//        private fun siginUpMenu(userRepository: UserRepository) {
//            implementar a futuro
//        }

        private fun mainMenu(user: User) {
            println("\n******* ¡Bienvenido/a ${user.getNameComplete()}! *******")

            do {
                println("\n******* MENÚ PRINCIPAL *******" +
                        "\n1. Comprar videojuegeos" +
                        "\n2. Consultar mis videojuegos" +
                        "\n3. Cerrar sesión")

                print("\nSeleccione una opción: ")
                val response = readlnOrNull()?.toInt()

                when(response) {
                    1 -> gamePurchaseMenu(user)
                    2 -> showPurchasedGamesOf(user)
                    3 -> { println("**** Hasta Luego ****")
                        run(GameRepository, UserRepository)
                    }
                }

            } while(response != 3)

        }

        private fun showPurchasedGamesOf(user: User) {
            println("********** Historial de compras **********" +
                    "\nUsuario activo: ${user.getNameComplete()}" +
                    "\n****************************************")

            for(purchase in user.getPurchasedGames())
                println(purchase)
        }

        private fun gamePurchaseMenu(user: User) {
            println("Sesión activa: ${user.getNameComplete()}" +
                    "\nSaldo: ${user.showMoney()}")
            println("\n***********************" +
                    "\n Seleccione una empresa:" +
                    "\n1. Epic Games" +
                    "\n2. Steam" +
                    "\n3. Nakama")
            print("\nIngrese la opción elegida: ")


            val response = readlnOrNull()?.toInt()

            val intermediary: Intermediary = when(response){
                1 -> EpicGames()
                2 -> Steam()
                3 -> Nakama()
                else -> EpicGames() //the default is epic games.
            }

            val chosenGame: Game? = showAndChooseAvailableGames()

            if (chosenGame != null)
                intermediary.processPurchase(user, chosenGame)

        }

        private fun showAndChooseAvailableGames(): Game? {

            println("********** JUEGOS DISPONIBLES **********")
            for(game in GameRepository.getGames())
                println("${game.id} - $game")

            var chosenGame: Int

            do{
                print("\nSeleccione un juego: ")
                chosenGame = readln().toInt()

                if(chosenGame < 1 || chosenGame > GameRepository.getGames().size)
                    println("\n¡Opción incorrecta! Reingrese la opción nuevamente.")

            }while(chosenGame < 1 || chosenGame > GameRepository.getGames().size)

           return GameRepository.getById(chosenGame.toLong())
        }
    }
}
