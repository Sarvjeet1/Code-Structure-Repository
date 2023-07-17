package com.example.mvvmdemo.utils.enums

import com.example.mvvmdemo.utils.constants.AppConst

enum class DialogEventType(override val id: Int, private val value: String) :
    IdentifierType<Int> {

    POSITIVE(AppConst.NUMBER.ONE, "POSITIVE"),
    NEGATIVE(AppConst.NUMBER.TWO, "NEGATIVE"),
    DISMISS(AppConst.NUMBER.THREE, "DISMISS"),

    DELETE_PHOTO(AppConst.NUMBER.FOUR, "DELETE_PHOTO"),
    TAKE_PHOTO(AppConst.NUMBER.FIVE, "TAKE_PHOTO"),
    TAKE_VIDEO(AppConst.NUMBER.SIX, "TAKE_VIDEO"),

    CHOOSE_PHOTO(AppConst.NUMBER.SEVEN, "CHOOSE_PHOTO"),
    CHOOSE_PHOTOS(AppConst.NUMBER.EIGHT, "CHOOSE_PHOTOS"),
    CHOOSE_VIDEOS(AppConst.NUMBER.NINE, "CHOOSE_VIDEOS"),

    DATE_SELECTED(AppConst.NUMBER.TEN, "DATE_SELECTED"),

    DELETE(AppConst.NUMBER.ELEVEN, "DELETE");

    override fun toString(): String = this.value

    companion object {
        fun valueOf(id: Int): DialogEventType? =
            EnumHelper.INSTANCE.valueOf(id, values())
    }
}
