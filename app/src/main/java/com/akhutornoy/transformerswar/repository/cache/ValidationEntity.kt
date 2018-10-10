package com.akhutornoy.transformerswar.repository.cache

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ValidationEntity(
        @PrimaryKey
        var id: String,
        var isValid: Boolean = false
)