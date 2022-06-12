package com.dragon.ft_main


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.common_utils.mmkvutil.MMKVOwner
import com.dragon.ft_main.databinding.ActivityMainBinding
import com.dragon.module_base.base.activity.BaseActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main), MMKVOwner {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding by viewBinding(ActivityMainBinding::bind)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNav)
        if (hostFragment != null) {
            //不先在布局中
            navController = hostFragment.findNavController()
            navController.setGraph(R.navigation.activity_graph)

            Log.e(TAG, "onCreate: $navController", )
        }
    }

    /**
     * 将导航返出去使用
     */
    fun routeControl():NavController{
        return navController
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
