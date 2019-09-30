package com.dolan.dolantancepclient

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Bencoleng on 29/09/2019.
 */
fun getConvertDate(dateString: String?): String? {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
    val monthDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return monthDate.format(date)
}