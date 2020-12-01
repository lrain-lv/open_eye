package com.app.eye

import com.app.eye.ui.entity.BrandApiRequest
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.GsonUtils


fun main(args: Array<String>) {
    val link ="eyepetizer://cardlist/common?fit_window=1&api_request=%7B%22url%22%3A%22https%3A%5C%2F%5C%2Fapi.eyepetizer.net%5C%2Fv1%5C%2Fcard%5C%2Fpage%5C%2Fget_page%22%2C%22params%22%3A%7B%22page_label%22%3A%22brand_wall_detail%22%2C%22resource_id%22%3A78%7D%7D&title="
    val urlDecode = EncodeUtils.urlDecode(link)
    val index = urlDecode.indexOf("{")
    val indexLast = urlDecode.lastIndexOf("}")
    val substring = urlDecode.substring(index, indexLast + 1)
    val fromJson = GsonUtils.fromJson(substring, BrandApiRequest::class.java)

    print(fromJson.params.resource_id)
}
