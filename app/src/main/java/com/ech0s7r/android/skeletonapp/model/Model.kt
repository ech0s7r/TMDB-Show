package com.ech0s7r.android.skeletonapp.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author ech0s7r
 */
@Entity(tableName = "model")
data class Model(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0) {
    fun get(pos: Int) = "Hello"
}