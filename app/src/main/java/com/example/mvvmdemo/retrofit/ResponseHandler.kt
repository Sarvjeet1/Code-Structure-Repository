package com.example.mvvmdemo.retrofit

class ResponseHandler<out Any>(val status: Status, val data: kotlin.Any?, val message: String, val requestCode : String) {
    companion object {

        fun <Any> onSuccess(data: Any?,msg: String, requestCode : String): ResponseHandler<Any> {
            return ResponseHandler(Status.SUCCESS, data, msg, requestCode)
        }

        fun <Any> onError(msg: String, requestCode : String): ResponseHandler<Any> {
            return ResponseHandler(Status.ERROR,null, msg, requestCode)
        }

//        fun <Any> loading(data: Any?): ResponseHandler<Any> {
//            return ResponseHandler(Status.LOADING,0, data, null)
//        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}