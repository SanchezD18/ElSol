package com.example.elsol

data class CardElSol(
    val id: Int,
    val imageRes: Int,
    val nombre: String = "",
)

object CardElSolRepository {
    fun getCardsElSol(): List<CardElSol> = listOf(
        CardElSol(
            id = 1,
            imageRes = R.drawable.corona_solar,
            nombre = "Corona Solar"
        ),
        CardElSol(
            id = 2,
            imageRes = R.drawable.erupcionsolar,
            nombre = "Erupción Solar"
        ),
        CardElSol(
            id = 3,
            imageRes = R.drawable.espiculas,
            nombre = "Espiculas"
        ),
        CardElSol(
            id = 4,
            imageRes = R.drawable.filamentos,
            nombre = "Filamentos"
        ),
        CardElSol(
            id = 5,
            imageRes = R.drawable.magnetosfera,
            nombre = "Magnetosfera"
        ),
        CardElSol(
            id = 6,
            imageRes = R.drawable.manchasolar,
            nombre = "Manchas Solar"
        )
    )
}