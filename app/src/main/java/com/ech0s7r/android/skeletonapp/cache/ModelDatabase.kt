package com.ech0s7r.android.skeletonapp.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ech0s7r.android.skeletonapp.model.Model

/**
 *
 * @author ech0s7r
 */
@Database(entities = [(Model::class)], version = 1, exportSchema = false)
abstract class ModelDatabase : RoomDatabase() {

    abstract fun getModelDao(): ModelDao

}