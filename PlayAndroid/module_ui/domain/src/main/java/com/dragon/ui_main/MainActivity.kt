package com.dragon.ui_main


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.common_utils.mmkvutil.MMKVOwner
import com.dragon.common_utils.mmkvutil.mmkvParcelable
import com.dragon.ft_main.MainFragment
import com.dragon.module_data.mmkv.LayoutChangeInfo
import com.dragon.ui_main.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), MMKVOwner {

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

}
