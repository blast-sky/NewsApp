package com.example.newsapp.domain.model

import java.io.Serializable

data class Source(
    val id: String? = null,
    val name: String
) : Serializable