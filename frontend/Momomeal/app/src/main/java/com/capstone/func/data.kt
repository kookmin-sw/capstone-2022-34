package com.capstone.func

import java.util.*

data class User(
    val name: String = "",
    val idUser: Int = 0,
    val email: String ="",
    val profileImgUrl: String = "",
    val totalRate: Int = 50,
    val listReview: List<Int> = listOf()
)

data class Chatroom (
    val nameRoom: String = "",
    val idChatroom: Int = 0,
    val category: Category,
    val maxCapacity: Int = 4,
    val namePickupPlace: String ="",
    val coordPickupPlace: Double = 0.0,
    val listUid: List<Int> = listOf()
)

data class Review (
    val idReview: Int = 0,
    val ratePoint: Int = 0,
    val conReview: String = "",
    val dateReview: Date = Date(2022, 0,1)
)

enum class Category {
    Chicken, Pizza, Korean, Chinese, Japanese,
    Western, Snackbar, MidnightSnack, BoiledPork, CafeAndDesert,
    Fastfood,
}