package com.coderbyte.moviemania.base

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
data class SnackBarMessage(var message: String?, var messageType: Int = SNACK_BAR_DEFAULT) {
    companion object {
        const val SNACK_BAR_DEFAULT = 0
        const val SNACK_BAR_NORMAL = 1
        const val SNACK_BAR_SUCCESS = 2
        const val SNACK_BAR_ERROR = 3
    }
}