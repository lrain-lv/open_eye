package com.app.eye.widgets

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.app.eye.R
import com.app.eye.rx.loadImageWithTransform
import com.app.eye.ui.entity.BadgeItem
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.layout_comment.iv_img
import kotlinx.android.synthetic.main.layout_comment.tv_title
import kotlinx.android.synthetic.main.layout_medal_dialog.*


class MedalDialog : DialogFragment() {

    companion object {
        fun createDialog(badgeItem: BadgeItem): MedalDialog {
            val bundle = Bundle().apply {
                putSerializable("data", badgeItem)
            }
            return MedalDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MedalDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setCanceledOnTouchOutside(true)
        return inflater.inflate(R.layout.layout_medal_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = arguments?.getSerializable("data") as BadgeItem
        iv_img.loadImageWithTransform(
            requireContext(), item.data.tagBgPicture,
            RoundedCornersTransformation(
                SizeUtils.dp2px(5f),
                0,
                RoundedCornersTransformation.CornerType.TOP
            )
        )
        Logger.e(item.data.description)
        tv_title.text = item.data.title
        tv_dec.text = item.data.description
        tv_subTitle.visibility = if (item.data.subTitle.isEmpty()) View.GONE else View.VISIBLE
        tv_subTitle.text = item.data.subTitle
        tv_share.visibility = if (item.data.receiveTime == null) View.GONE else View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window!!
        val params = window.attributes
        params.dimAmount = 0.6f
        params.gravity = Gravity.CENTER
        window.attributes = params
        dialog!!.window!!.setLayout(
            (ScreenUtils.getAppScreenWidth() * 0.95).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun showDialog(manager: FragmentManager) {
        show(manager, "")
    }
}