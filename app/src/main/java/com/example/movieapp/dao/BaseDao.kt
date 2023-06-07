package com.example.movieapp.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insertList(entities: List<T>)

    @Delete
    suspend fun update(entity: T)

    @Delete
    suspend fun updateList(entities: List<T>)

    @Update
    suspend fun delete(entity: T)

    @Update
    suspend fun deleteList(entities: List<T>)
}