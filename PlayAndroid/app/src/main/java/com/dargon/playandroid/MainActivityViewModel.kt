package com.dargon.playandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale

//@HiltViewModel
class MainActivityViewModel  : ViewModel(

) {
//    // 使用LiveData来存储和更新语言
//    private val _language = MutableLiveData(Locale.getDefault().language)
//    val language: LiveData<String> = _language
//
//    // 定义一个方法来切换语言
//    fun switchLanguage(lang: String) {
//        _language.value = lang
//    }
}

// 定义一个枚举类，用于表示不同的语言选项
enum class Language(val code: String) {
    ENGLISH("en"),
    CHINESE("zh"),
    JAPANESE("ja"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de")
}