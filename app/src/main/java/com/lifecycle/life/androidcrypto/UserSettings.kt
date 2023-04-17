package com.lifecycle.life.androidcrypto

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userName: String? = null,
    val userPassword: String? = null
)
