package com.example.nycschoolsapp.data.repository.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SchoolModel::class], version = 1)
abstract class SchoolsDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao

    companion object {
        private const val SCHOOL_DATABASE_NAME = "schools_database"
        const val SCHOOLS_TABLE_NAME = "schools"

        private var INSTANCE: SchoolsDatabase? = null

        fun getInstance(context: Context): SchoolsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SchoolsDatabase::class.java,
                    SCHOOL_DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}