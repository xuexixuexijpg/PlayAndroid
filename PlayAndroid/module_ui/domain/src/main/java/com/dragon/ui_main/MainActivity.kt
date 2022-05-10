package com.dragon.ui_main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.canFinish
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentNavigator.Transmitter {

//    private val binding by viewBinding(A)

    private lateinit var navigator: FragmentNavigator
    override fun getNavigator(): FragmentNavigator = navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onBackPressed() {
        if (navigator.canFinish()) super.onBackPressed()
    }
}