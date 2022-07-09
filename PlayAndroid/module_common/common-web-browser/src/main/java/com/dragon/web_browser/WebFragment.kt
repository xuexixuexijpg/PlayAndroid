package com.dragon.web_browser

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.common_utils.ext.toPx
import com.dragon.module_base.base.callback.BackPressedOwner
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.web_browser.databinding.FragmentWebBrowserBinding
import com.dragon.web_browser.helper.WebViewHelper
import com.dragon.web_browser.helper.WebViewManager
import com.dylanc.longan.design.snackbar
import com.google.android.material.shape.MaterialShapeDrawable
import com.tencent.smtt.sdk.WebView

class WebFragment : BaseFragment(R.layout.fragment_web_browser) {

    private val _binding by viewBinding(FragmentWebBrowserBinding::bind)
    private val binding get() = _binding
    private var _webViewHelper: WebViewHelper? = null
    private val webViewHelper get() = _webViewHelper!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.web_topappbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                snackbar(menuItem.title)
                return true
            }
        }, viewLifecycleOwner)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        webViewHelper.onResume()
        super.onResume()
    }

    override fun onPause() {
        webViewHelper.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webViewHelper.onDestroyView()
        _webViewHelper = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myActivity.setSupportActionBar(binding.toolbar)
        binding.appbarlayout.statusBarForeground =
            MaterialShapeDrawable.createWithElevationOverlay(requireContext())
        val url = Uri.decode(requireArguments().getString(com.dragon.common_data.constant.Keys.URL))
        val title =
            Uri.decode(requireArguments().getString(com.dragon.common_data.constant.Keys.VALUE))
        val webView = WebViewManager.obtain(myActivity)
        binding.webContainer.addView(
            webView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        //滑动处理 TODO 太闪了 换成CoordinatorLayout 结合 NestedScrollView webview显示不全
//        if (Build.VERSION.SDK_INT >= 23) {
//            val child = binding.appbarlayout
//            var hasOffset = false
//            var oldCompute = 0
//            webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//                val compute = scrollY - oldScrollY
//                Log.e("测试位移", "onViewCreated: $compute", )
//                if (oldCompute != compute && oldCompute != -compute) {
//                    oldCompute = compute
//                    if (compute > 0 && !hasOffset) {
//                        hasOffset = true
//                        binding.guideline2.setGuidelineBegin(0)
//                    } else if (compute < 0 && hasOffset) {
//                        hasOffset = false
//                        binding.guideline2.setGuidelineBegin(toPx(45).toInt())
//                    }
//                }
//            }
//        }
        _webViewHelper = WebViewHelper(webView).apply {
            setOnPageChangedListener(object : WebViewHelper.OnPageChangedListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.isVisible
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.isGone
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progressBar.progress = newProgress
                }
            })
            loadUrl(url)
        }
        binding.toolbar.title = title
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
//        binding.forward.setOnClickListener {
//            webViewHelper.canGoForward()
//        }
//        binding.refresh.setOnClickListener {
//            webViewHelper.reload()
//        }
//        binding.browse.setOnClickListener {
//            try {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                activity.startActivity(intent)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

    override fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
        return webViewHelper.canGoBack()
    }


}