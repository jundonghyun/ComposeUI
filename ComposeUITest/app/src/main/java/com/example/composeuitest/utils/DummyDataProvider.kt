package com.example.composeuitest.utils

data class RandomUser(
    val name: String = "개발하는 정대리 \uD83D\uDE0A",
    val description: String = "오늘도 빡코딩 하고 계신가요",
    val profileImage: String = "https://randomuser.me/api/portraits/women/18.jpg"
){

}

object DummyDataProvider {

    val userList = List<RandomUser>(200){ RandomUser() }

}