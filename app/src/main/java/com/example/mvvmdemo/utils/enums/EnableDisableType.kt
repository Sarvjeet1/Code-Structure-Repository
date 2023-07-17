package com.example.mvvmdemo.utils.enums

import com.example.mvvmdemo.utils.constants.AppConst

enum class EnableDisableType(override val id: Int, private val value: String) :
    IdentifierType<Int> {

    NOTHING(AppConst.NUMBER.ZERO, "NOTHING"),
    ALL(AppConst.NUMBER.ONE, "ALL"),
    LOADING_DIALOG(AppConst.NUMBER.TWO, "LOADING_DIALOG"),
    PROGRESS_BAR(AppConst.NUMBER.THREE, "PROGRESS_BAR"),
    LOAD_MORE(AppConst.NUMBER.FOUR, "LOAD_MORE"),
    SWIPE_REFRESH(AppConst.NUMBER.FIVE, "SWIPE_REFRESH");

    override fun toString(): String = this.value

    companion object {
        fun valueOf(id: Int): EnableDisableType? = EnumHelper.INSTANCE.valueOf(id, values())
    }

}
