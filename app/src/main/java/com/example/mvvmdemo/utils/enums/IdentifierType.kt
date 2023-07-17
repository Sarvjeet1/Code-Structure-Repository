package com.example.mvvmdemo.utils.enums

/**
 * Contract that will allow Types with id to have generic implementation.
 */
interface IdentifierType<T> {

    val id: T

}