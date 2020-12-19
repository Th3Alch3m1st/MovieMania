package com.coderbyte.moviemania.data.session



interface Session {

    companion object {
        const val SEARCH_CONTENTS = "SEARCH_CONTENTS"
        const val SEARCH_HISTORY = "search_history"
        const val HOME_ITEM_SEQUENCE = "HOME_ITEM_SEQUENCE"
        const val SEND_GIFT_TO_CONTACT = "SEND_GIFT_TO_CONTACT"
        const val LOGIN_STATUS = "LOGIN_STATUS"
        const val BALANCE_SUMMERY = "BALANCE_SUMMERY"
        const val TOKEN = "TOKEN"
        const val FCM_TOKEN = "FCM_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val CUSTOMER = "CUSTOMER"
        const val TOKEN_VALIDITY = "TOKEN_VALIDITY"
        const val AUTO_UPDATE = "AUTO_UPDATE"
        const val IS_BANGLA = "IS_BANGLA"
        const val IS_POP_UP_SHOWN = "IS_POP_UP_SHOWN"
        const val IS_TUTORIAL_SHOWN = "IS_TUTORIAL_SHOWN"
        const val APP_VERSION_CODE = "APP_VERSION_CODE"
        const val LINKED_ACCOUNT = "LINKED_ACCOUNT"
    }

    var token: String
    var fcmToken: String
    var refreshToken: String
}