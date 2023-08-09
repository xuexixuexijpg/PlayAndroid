package com.dargon.playandroid.langtest

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import com.dragon.playandoird.R

val LocalLanguage = compositionLocalOf {
    "1"
}

class LanguageViewModel : ViewModel(){
    val langState = MutableStateFlow("1")
}

@Composable
fun Test(languageViewModel: LanguageViewModel = viewModel()){
    val stringState = languageViewModel.langState.collectAsState()
    CompositionLocalProvider(LocalLanguage provides stringState.value) {
        SetLanguage(stringState.value)
        Column {

            Text(text = stringResource(id = R.string.test))
            Text(text = getLangString(id = R.string.test))

            Button(onClick = {
                if (languageViewModel.langState.value == "1"){
                    languageViewModel.langState.value = "2"
                }else  languageViewModel.langState.value = "1"
            }) {
                Text(text = getLangString(id = R.string.test))
            }

            LangText(R.string.test)

            Card {
                Log.e("测试", "Test: ", )
                Box(modifier = Modifier.fillMaxSize()){
                    Log.e("测试", "Test: 2", )
                    Text(text = getLangString(id = R.string.test))
                    Text(text = stringResource(id = R.string.test))
                }
                Box(modifier = Modifier.fillMaxSize()){
                    LangText(R.string.test)
                }
                LangText(R.string.test)
                Text(text = getLangString(id = R.string.test))
            }
        }
    }
}

@Composable
fun LangText(@StringRes id : Int){
    Text(text = getLangString(id = id))
}

@Composable
fun getLangString(@StringRes id : Int):String{
    return if (LocalLanguage.current == "1") LocalContext.current.getString(id)
    else LocalContext.current.getString(id)
}

@Composable
fun SetLanguage(lang:String){
    val local =
        if (lang == "1"){
            Locale.CHINESE
        }else Locale.ENGLISH

    val context = LocalContext.current
    if (Locale.getDefault().language != local.language){
        Locale.setDefault(local)
        val configuration = context.resources.configuration
        configuration.setLocale(local)
        context.resources.updateConfiguration(configuration,context.resources.displayMetrics)
    }
}