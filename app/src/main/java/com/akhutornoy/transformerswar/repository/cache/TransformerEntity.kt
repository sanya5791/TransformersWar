package com.akhutornoy.transformerswar.repository.cache

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Transformers")
data class TransformerEntity(
        @PrimaryKey
        var id: String,
        var name: String = "",
        var team: String = "",
        var strength: Int = 0,
        var intelligence: Int = 0,
        var speed: Int = 0,
        var endurance: Int = 0,
        var rank: Int = 0,
        var courage: Int = 0,
        var firepower: Int = 0,
        var skill: Int = 0,
        var team_icon: String = ""
) : Parcelable