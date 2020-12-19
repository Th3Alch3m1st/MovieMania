package com.coderbyte.moviemania.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
const val API_DATE_FORMAT = "yyyy-mm-dd"
const val APP_DATE_FORMAT = "dd MMM yyyy"

fun getAppDateFormat(date: String?):String{
    if(date.isNullOrEmpty())
        return ""

    val inputDateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
    val dateTime = inputDateFormat.parse(date)

    val outPutDateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
    return outPutDateFormat.format(dateTime)
}