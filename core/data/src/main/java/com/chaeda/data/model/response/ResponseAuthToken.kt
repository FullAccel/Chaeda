package com.chaeda.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseAuthToken(
    val accessToken: String,
    val refreshToken: String,
)