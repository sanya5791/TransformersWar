package com.akhutornoy.transformerswar.repository.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [TransformerEntity::class, ValidationEntity::class], version = 1, exportSchema = false)
abstract class CacheDb : RoomDatabase() {
    abstract fun getTransformerDao(): TransformerDao
    abstract fun getValidationDao(): ValidationDao
}