package com.dragon.data.model

//b站的建议关键词

@kotlinx.serialization.Serializable
data class Word(
    val name: String,
    val ref: Int,
    val spid: Int,
    val term: String,
    val value: String
)