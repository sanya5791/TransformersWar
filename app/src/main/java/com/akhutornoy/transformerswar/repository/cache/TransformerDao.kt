package com.akhutornoy.transformerswar.repository.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TransformerDao {
    @Query("SELECT * FROM Transformers")
    fun getAll() : Flowable<List<TransformerEntity>>

    @Query("DELETE FROM Transformers")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transformers: List<TransformerEntity>)

    @Query("SELECT * FROM Transformers WHERE id == :id")
    fun getById(id: String): Single<TransformerEntity>
}