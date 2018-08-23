package com.ech0s7r.android.skeletonapp.cache

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ech0s7r.android.skeletonapp.model.Model

/**
 *
 * @author ech0s7r
 */
@Dao
interface ModelDao {

    @Query("SELECT * FROM model")
    fun getModel(): LiveData<List<Model>>

    @Query("SELECT * FROM model")
    fun getModelSync(): List<Model>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(modelList: List<Model>?)

    @Query("DELETE FROM model")
    fun deleteAll()

}