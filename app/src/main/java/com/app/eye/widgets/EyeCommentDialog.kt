package com.app.eye.widgets

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.app.eye.R
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.layout_comment.*


class EyeCommentDialog : DialogFragment(),
    View.OnClickListener {

    private var cxt: Context? = null
    private var sDecorViewDelta = 0

    private var isChoose = false

    companion object {
        fun createCommentDialog(name: String? = ""): EyeCommentDialog {
            val bundle = Bundle().apply {
                putString("name", name)
            }
            return EyeCommentDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        return inflater.inflate(R.layout.layout_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = arguments?.getString("name", "")
        tv_title.text = if (name.isNullOrEmpty()) "评论" else "@${name}"
        et_content.postDelayed({
            KeyboardUtils.showSoftInput(et_content)
        }, 100)
        initListener()
    }

    private fun initListener() {
        iv_close.setOnClickListener(this)
        iv_submit.setOnClickListener(this)
        iv_photo.setOnClickListener(this)
        tv_photo.setOnClickListener(this)
        iv_img.setOnClickListener(this)
        et_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_count.text = (500 - s.toString().trim().length).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        activity?.let {
            KeyboardUtils.registerSoftInputChangedListener(
                it
            ) { height ->
                if (height > 0) {
                    isChoose = false
                } else if (height <= 0 && !isChoose) {
                    dismiss()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        val params = window!!.attributes
        params.dimAmount = 0.6f
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.BOTTOM
        window.attributes = params
    }


    fun showDialog(manager: FragmentManager) {
        show(manager, "")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 10000 && resultCode == RESULT_OK) {
            val result = Matisse.obtainResult(data)[0]
            iv_img.visibility = View.VISIBLE
            iv_photo.visibility = View.GONE
            tv_photo.visibility = View.GONE
            Glide.with(this@EyeCommentDialog)
                .load(result)
                .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                .into(iv_img)
        }
    }

    override fun onResume() {
        super.onResume()
        et_content.post { KeyboardUtils.showSoftInput(et_content) }
    }

    override fun onPause() {
        super.onPause()
        KeyboardUtils.hideSoftInput(et_content)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> {
                KeyboardUtils.hideSoftInput(et_content)
                dismiss()
            }
            R.id.iv_submit -> {

            }
            R.id.tv_photo, R.id.iv_photo -> {
                isChoose = true
                Matisse.from(this@EyeCommentDialog)
                    .choose(MimeType.ofImage())
                    .countable(true)
                    .maxSelectable(1)
//                    .gridExpectedSize(SizeUtils.dp2px(70f))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                    .thumbnailScale(0.85f)
                    .spanCount(4)
                    .imageEngine(GlideEngine())
                    .showPreview(true)
                    .forResult(10000)
            }

            R.id.iv_img -> {
                iv_img.visibility = View.GONE
                iv_photo.visibility = View.VISIBLE
                tv_photo.visibility = View.VISIBLE
            }
        }
    }

}