package com.dragon.data.model

import kotlinx.serialization.Serializable

//@Serializable
//data class HotWord(
//    val data: MutableList<Data>,
////    val errorCode: Int,
////    val errorMsg: String
//)

@Serializable
data class HotWord(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)