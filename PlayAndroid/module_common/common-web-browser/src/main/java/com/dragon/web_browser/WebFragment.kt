package com.dragon.web_browser

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.module_base.base.callback.BackPressedOwner
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.common_data.constant.Keys
import com.dragon.web_browser.databinding.FragmentWebBrowserBinding
import com.dragon.web_browser.helper.WebViewHelper
import com.dragon.web_browser.helper.WebViewManager

import com.tencent.smtt.sdk.WebView
import dagger.hilt.android.AndroidEntryPoint

class WebFragment : BaseFragment(R.layout.fragment_web_browser) {

    private val _binding by viewBinding(FragmentWebBrowserBinding::bind)
    private val binding get() = _binding
    private var _webViewHelper: WebViewHelper? = null
    private val webViewHelper get() = _webViewHelper!!


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
        val url = Uri.decode(requireArguments().getString(com.dragon.common_data.constant.Keys.URL))
        val webView = WebViewManager.obtain(myActivity)
        binding.webContainer.addView(
            webView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        _webViewHelper = WebViewHelper(webView).apply {
            setOnPageChangedListener(object : WebViewHelper.OnPageChangedListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progressBar.progress = newProgress
                }
            })
            loadUrl(url)
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