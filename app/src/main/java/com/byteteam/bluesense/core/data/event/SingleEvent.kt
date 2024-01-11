package com.byteteam.bluesense.core.data.event

sealed class SingleEvent {
    data class MessageEvent(val message: String): SingleEvent()
}