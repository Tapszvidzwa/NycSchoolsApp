package com.example.nycschoolsapp.data.repository.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchoolDao {
    @Query("SELECT * FROM schools")
    fun getAllSchools(): List<SchoolModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCountries(countries: List<SchoolModel>)

    @Query("SELECT COUNT(*) FROM schools")
    fun getCount(): Int

    @Query("SELECT * FROM schools WHERE dbn = :schoolId")
    fun getSchoolById(schoolId: String): SchoolModel?
}
