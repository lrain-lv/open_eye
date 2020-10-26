package com.app.eye.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.app.eye.R

const val STATUS_CONTENT = 0X00
const val STATUS_LOADING = 0x01
const val STATUS_EMPTY = 0x02
const val STATUS_ERROR = 0x03
const val STATUS_NO_NETWORK = 0x04

class MultipleStatusView(context: Context, attr: AttributeSet) : FrameLayout(context, attr) {

    private var emptyViewResId: Int? = null
    private var errorViewResId: Int? = null
    private var noNetworkViewResId: Int? = null
    private var loadingViewResId: Int? = null

    private var emptyView: View? = null
    private var errorView: View? = null
    private var noNetworkView: View? = null
    private var loadingView: View? = null

    private var viewStatus = -1;

    private var onRetryClickListener: OnRetryClickListener? = null

    fun setOnRetryClickListener(onClickListener: OnRetryClickListener) {
        this.onRetryClickListener = onClickListener
    }

    private val inflater by lazy { LayoutInflater.from(context) }

    private val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
    )

    init {
        val attrs = context.obtainStyledAttributes(attr, R.styleable.MultipleStatusView).apply {
            emptyViewResId = getResourceId(
                R.styleable.MultipleStatusView_emptyView,
                R.layout.default_empty_view
            )
            errorViewResId = getResourceId(
                R.styleable.MultipleStatusView_errorView,
                R.layout.default_empty_view
            )
            noNetworkViewResId = getResourceId(
                R.styleable.MultipleStatusView_noNetworkView,
                R.layout.default_error_view
            )
            loadingViewResId = getResourceId(
                R.styleable.MultipleStatusView_loadingView,
                R.layout.default_loading_view
            )
            recycle()
            setBackgroundColor(Color.WHITE)
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        showLoadingView()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear(emptyView, errorView, loadingView, noNetworkView)
        if (null != onRetryClickListener) onRetryClickListener = null
    }

    /**
     * 显示内容视图
     */

    fun showContentView() {
        visibility = View.GONE
        changeViewStatus(STATUS_CONTENT)
    }

    /**
     * 显示空视图
     */

    fun showEmptyView() {
        visibility = View.VISIBLE
        showEmptyView(emptyViewResId, layoutParams)
    }

    fun showEmptyView(layoutId: Int?, layoutParams: FrameLayout.LayoutParams) {
        showEmptyView(
            if (emptyView == null) inflateView(emptyViewResId!!) else emptyView, layoutParams
        )
    }

    fun showEmptyView(view: View?, layoutParams: FrameLayout.LayoutParams) {
        changeViewStatus(STATUS_EMPTY)
        if (emptyView == null) {
            emptyView = view
            val retryView = emptyView!!.findViewById<ImageView>(R.id.retry_view)
            if (null != retryView && onRetryClickListener != null) {
                retryView.setOnClickListener { onRetryClickListener!!.onRetryClick() }
            }
            addView(emptyView, 0, layoutParams)
        }
        showViewById(emptyView!!.id)
    }

    /**
     * 显示错误视图
     */

    fun showErrorView() {
        visibility = View.VISIBLE
        showErrorView(errorViewResId, layoutParams)
    }

    fun showErrorView(layoutId: Int?, layoutParams: FrameLayout.LayoutParams) {
        showErrorView(
            if (errorView == null) inflateView(errorViewResId!!) else errorView, layoutParams
        )
    }

    fun showErrorView(view: View?, layoutParams: FrameLayout.LayoutParams) {
        changeViewStatus(STATUS_ERROR)
        if (errorView == null) {
            errorView = view
            val retryView = errorView!!.findViewById<ImageView>(R.id.retry_view)
            if (null != retryView && onRetryClickListener != null) {
                retryView.setOnClickListener { onRetryClickListener!!.onRetryClick() }
            }
            addView(errorView, 0, layoutParams)
        }
        showViewById(errorView!!.id)
    }

    /**
     * 显示无网视图
     */

    fun showNoNetworkView() {
        visibility = View.VISIBLE
        showNoNetworkView(noNetworkViewResId, layoutParams)
    }


    fun showNoNetworkView(layoutId: Int?, layoutParams: FrameLayout.LayoutParams) {
        showNoNetworkView(
            if (noNetworkView == null) inflateView(noNetworkViewResId!!) else noNetworkView,
            layoutParams
        )
    }

    fun showNoNetworkView(view: View?, layoutParams: FrameLayout.LayoutParams) {
        changeViewStatus(STATUS_NO_NETWORK)
        if (noNetworkView == null) {
            noNetworkView = view
            val retryView = noNetworkView!!.findViewById<ImageView>(R.id.retry_view)
            if (null != retryView && onRetryClickListener != null) {
                retryView.setOnClickListener { onRetryClickListener!!.onRetryClick() }
            }
            addView(noNetworkView, 0, layoutParams)
        }
        showViewById(noNetworkView!!.id)
    }

    /**
     * 显示loading视图
     */

    fun showLoadingView() {
        visibility = View.VISIBLE
        showLoadingView(loadingViewResId, layoutParams)
    }


    fun showLoadingView(layoutId: Int?, layoutParams: FrameLayout.LayoutParams) {
        showLoadingView(
            if (loadingView == null) inflateView(loadingViewResId!!) else loadingView,
            layoutParams
        )
    }

    fun showLoadingView(view: View?, layoutParams: FrameLayout.LayoutParams) {
        changeViewStatus(STATUS_LOADING)
        if (loadingView == null) {
            loadingView = view
            addView(loadingView, 0, layoutParams)
        }
        showViewById(loadingView!!.id)
    }

    fun getViewStatus() = viewStatus

    private fun changeViewStatus(viewStatus: Int) {
        if (this.viewStatus == viewStatus) return
        this.viewStatus = viewStatus
    }

    private fun inflateView(resId: Int): View = inflater.inflate(resId, null)

    private fun showViewById(viewId: Int) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.visibility = if (child.id == viewId) View.VISIBLE else View.GONE
        }
    }

    private fun clear(vararg views: View?) {
        views.forEach {
            removeView(it)
        }
    }

    interface OnRetryClickListener {
        fun onRetryClick()
    }

}