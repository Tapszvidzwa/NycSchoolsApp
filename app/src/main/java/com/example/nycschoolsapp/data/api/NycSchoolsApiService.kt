package com.example.nycschoolsapp.data.api

import com.example.nycschoolsapp.data.models.schoolsdata.SchoolsInfoListResponseDataModel
import com.example.nycschoolsapp.data.models.schoolslist.SchoolsListResponseDataModel
import retrofit2.Response
import retrofit2.http.GET

interface NycSchoolsApiService {
    @GET("7crd-d9xh.json")
    suspend fun getSchoolsList(): Response<SchoolsListResponseDataModel>

    @GET("f9bf-2cp4.json")
    suspend fun getSchoolsData(
    ): Response<SchoolsInfoListResponseDataModel>
}
