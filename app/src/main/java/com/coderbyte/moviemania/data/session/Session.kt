package com.coderbyte.moviemania.data.session

import com.coderbyte.moviemania.data.model.WishListItem


interface Session {

    companion object {
        const val WISH_LIST_ITEM = "WISH_LIST_TEM"
    }

    var wishListItem: MutableList<WishListItem>
}