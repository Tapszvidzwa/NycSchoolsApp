package com.example.nycschoolsapp.data.repository

import com.example.nycschoolsapp.data.api.NycSchoolsApiService
import com.example.nycschoolsapp.data.api.Resource
import com.example.nycschoolsapp.data.models.schoolsdata.SchoolResponseDataModel
import com.example.nycschoolsapp.data.models.schoolsdata.SchoolsInfoListResponseDataModel
import com.example.nycschoolsapp.data.models.schoolslist.SchoolsListResponseDataModel
import com.example.nycschoolsapp.data.repository.localdatabase.SchoolDao
import com.example.nycschoolsapp.data.repository.localdatabase.SchoolModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class SchoolsRepository @Inject constructor(
    private val schoolDao: SchoolDao,
    private val schoolsListApiService: NycSchoolsApiService
) {

    /**
     * This function fetches the list of schools as well as the SAT data for these schools before
     * combining the data for each school into a schoolModel object
     * @return Resource<List<SchoolModel>>, a list of school models wrapped in the Resource object
     */
    suspend fun getSchoolsList(): Resource<List<SchoolModel>> {
        try {
            // Check if already have a locally cached list
            return if (schoolDao.getCount() != 0) {

                // Lets return list from cache if available
                Resource.Success(data = schoolDao.getAllSchools())
            } else {

                var schoolsListResponse: Response<SchoolsListResponseDataModel>? = null
                var schoolsDataResponse: Response<SchoolsInfoListResponseDataModel>? = null

                withContext(Dispatchers.IO) {
                    val schoolsList =
                        async { schoolsListResponse = schoolsListApiService.getSchoolsList() }
                    val schoolsData =
                        async { schoolsDataResponse = schoolsListApiService.getSchoolsData() }
                    schoolsList.await()
                    schoolsData.await()

                    if (!schoolsListResponse?.body().isNullOrEmpty() && !schoolsDataResponse?.body()
                            .isNullOrEmpty()
                    ) {

                        val map = mutableMapOf<String, SchoolResponseDataModel>()

                        // Add sat data for each school in the map
                        schoolsDataResponse?.body()?.forEach {
                            map[it.dbn] = it
                        }

                        val schoolsList = mutableListOf<SchoolModel>()

                        schoolsListResponse?.body()?.forEach { it ->
                            // For each school in the schoolsList, add its sat data in the model
                            val data = map[it.dbn]

                            data?.let { item ->
                                schoolsList.add(
                                    SchoolModel(
                                        dbn = item.dbn,
                                        name = item.school_name,
                                        satReadingScore = data.sat_critical_reading_avg_score,
                                        satWritingScore = data.sat_writing_avg_score,
                                        satMathScore = data.sat_math_avg_score,
                                        description = it.overview_paragraph
                                    )
                                )
                            }
                        }

                        // Save them locally for quick retrieval
                        schoolDao.insertAllCountries(
                            schoolsList
                        )

                        Resource.Success(data = schoolDao.getAllSchools())
                    } else {
                        Resource.Error(
                            message = schoolsListResponse?.errorBody().toString(),
                            data = emptyList()
                        )
                    }
                }
            }

        } catch (httpException: HttpException) {
            return Resource.Error(message = "Network error occured", data = emptyList())
        } catch (exception: Exception) {
            return Resource.Error(
                message = "An error occured: " + exception.message.toString(),
                data = emptyList()
            )
        }
    }

    fun getSchoolDetails(id: String): Resource<SchoolModel> {
        return schoolDao.getSchoolById(id)?.let { model ->
            Resource.Success(data = model)
        } ?: Resource.Error(message = "School name not found", data = SchoolModel())
    }
}

