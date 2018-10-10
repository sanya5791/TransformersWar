package com.akhutornoy.transformerswar.repository.cache

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface ValidationDao {
    @Query("SELECT * FROM ValidationEntity")
    fun getAll() : Flowable<ValidationEntity>

    @Query("DELETE FROM ValidationEntity")
    fun deleteAll()

    @Query("SELECT * FROM ValidationEntity WHERE id == :id LIMIT 1")
    fun get(id: String): ValidationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(validation: ValidationEntity)

    enum class Id {
        TRANSFORMERS,
    }
}

