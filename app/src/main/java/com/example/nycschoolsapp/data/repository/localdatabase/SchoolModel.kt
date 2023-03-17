package com.example.nycschoolsapp.data.repository.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nycschoolsapp.data.repository.localdatabase.SchoolsDatabase.Companion.SCHOOLS_TABLE_NAME

@Entity(tableName = SCHOOLS_TABLE_NAME)
data class SchoolModel(
    @PrimaryKey val dbn: String = "",
    @ColumnInfo(name = "school_name") val name: String = "",
    @ColumnInfo(name = "sat_reading_score") val satReadingScore: String = "",
    @ColumnInfo(name = "sat_writing_score") val satWritingScore: String = "",
    @ColumnInfo(name = "sat_math_score") val satMathScore: String = "",
    @ColumnInfo(name = "description") val description: String = ""
)