package com.kotlin.lib.interfaces

interface IObserverError {
    fun onError(any: Any, t: Throwable)
}