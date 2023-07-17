package com.example.mvvmdemo.utils.enums

import com.example.mvvmdemo.utils.constants.AppConst

enum class ImageType(override val id: Int, private val value: String) :
    IdentifierType<Int> {

    ANY(AppConst.NUMBER.ZERO, "ANY"),
    NOTIFICATION(AppConst.NUMBER.ONE, "NOTIFICATION"),
    USER(AppConst.NUMBER.TWO, "USER"),
    PROFILE(AppConst.NUMBER.THREE, "profile"),
    INSURED_PHOTO(AppConst.NUMBER.FOUR, "insured_photo"),
    CHAT_MEDIA(AppConst.NUMBER.FIVE, "chat_media"),
    ADJUSTER(AppConst.NUMBER.SIX, "ADJUSTER"),
    TIMBER_WARRIOR(AppConst.NUMBER.SEVEN, "ADJUSTER"),
    MEASURING_FEATURE(AppConst.NUMBER.EIGHT, "measuring_feature");

    override fun toString(): String = this.value

    companion object {
        fun valueOf(id: Int): ImageType? = EnumHelper.INSTANCE.valueOf(id, values())
    }

}
