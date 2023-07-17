package com.example.mvvmdemo.utils.enums

import com.example.mvvmdemo.utils.constants.AppConst

enum class ItemClickType private constructor(override val id: Int, private val value: String) :
    IdentifierType<Int> {

    DETAIL(AppConst.NUMBER.ONE, "DETAIL"),
    IMAGE(AppConst.NUMBER.TWO, "IMAGE"),
    ADD_PHOTO(AppConst.NUMBER.THREE, "ADD_PHOTO"),
    DELETE(AppConst.NUMBER.FOUR, "DELETE"),
    CLEAR(AppConst.NUMBER.FIVE, "CLEAR"),
    SELECT_OPTION(AppConst.NUMBER.SIX, "SELECT_OPTION"),
    SELECT_USER(AppConst.NUMBER.SEVEN, "SELECT_USER"),
    VIDEO(AppConst.NUMBER.EIGHT, "VIDEO"),
    UPDATE_MEDIA(AppConst.NUMBER.NINE, "UPDATE_MEDIA"),
    NOTIFICATION_CLICK(AppConst.NUMBER.TEN, "NOTIFICATION_CLICK");

    override fun toString(): String = this.value

    companion object {
        fun valueOf(id: Int): ItemClickType? =
            EnumHelper.INSTANCE.valueOf(id, values())
    }
}
