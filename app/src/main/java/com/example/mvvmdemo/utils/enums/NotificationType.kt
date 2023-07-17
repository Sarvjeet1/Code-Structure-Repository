package com.example.mvvmdemo.utils.enums

import com.example.mvvmdemo.utils.constants.AppConst

enum class NotificationType private constructor(override val id: Int, private val value: String) :
    IdentifierType<Int> {

    USER_CHAT(AppConst.NUMBER.ZERO, "USER_CHAT"),
    ASSIGNMENT_REQUEST(AppConst.NUMBER.ONE, "ASSIGNMENT_REQUEST"),
    ASSIGNMENT_CANCELED(AppConst.NUMBER.TWO, "ASSIGNMENT_CANCELED"),
    JOB_COMPLETED(AppConst.NUMBER.THREE, "JOB_COMPLETED"),
    CLAIM_COMPLETED(AppConst.NUMBER.FOUR, "CLAIM_COMPLETED"),
    UNKNOWN(AppConst.NUMBER.FIVE, "UNKNOWN");


    override fun toString(): String = this.value

    companion object {
        fun valueOf(id: Int): NotificationType? = EnumHelper.INSTANCE.valueOf(id, values())
    }

}