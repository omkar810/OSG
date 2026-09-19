package com.example.omshivgoraksha.data.model.course.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omshivgoraksha.data.model.course.request.CourseRequest
import com.example.omshivgoraksha.data.model.course.response.CourseResponse
import com.example.omshivgoraksha.data.remote.RetrofitClient
import com.example.omshivgoraksha.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

sealed class CourseUiState {

    data object Loading : CourseUiState()

    data class Success(
        val courses: List<CourseResponse>
    ) : CourseUiState()

    data class Error(
        val message: String
    ) : CourseUiState()
}


class CourseViewModel : ViewModel() {

    private val repository =
        CourseRepository(
            RetrofitClient.apiService
        )

    private val _uiState =
        MutableStateFlow<CourseUiState>(
            CourseUiState.Loading
        )

    val uiState: StateFlow<CourseUiState> =
        _uiState.asStateFlow()


    // =========================================================
    // GET COURSES
    // =========================================================

    fun getCourses() {

        viewModelScope.launch {

            _uiState.value =
                CourseUiState.Loading

            repository
                .getCourses()
                .onSuccess { courses ->

                    _uiState.value =
                        CourseUiState.Success(
                            courses
                        )
                }
                .onFailure { error ->

                    _uiState.value =
                        CourseUiState.Error(
                            error.message
                                ?: "Unable to load courses."
                        )
                }
        }
    }


    // =========================================================
    // CREATE COURSE
    // =========================================================

    fun createCourse(
        request: CourseRequest,
        documentFiles: List<File>,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            _uiState.value =
                CourseUiState.Loading

            repository
                .createCourse(
                    request = request,
                    documentFiles = documentFiles
                )
                .onSuccess {

                    getCourses()

                    onSuccess()
                }
                .onFailure { error ->

                    _uiState.value =
                        CourseUiState.Error(
                            error.message
                                ?: "Unable to create course."
                        )
                }
        }
    }


    // =========================================================
    // UPDATE COURSE
    // =========================================================

    fun updateCourse(
        id: Long,
        request: CourseRequest,
        documentFiles: List<File>,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            _uiState.value =
                CourseUiState.Loading

            repository
                .updateCourse(
                    id = id,
                    request = request,
                    documentFiles = documentFiles
                )
                .onSuccess {

                    getCourses()

                    onSuccess()
                }
                .onFailure { error ->

                    _uiState.value =
                        CourseUiState.Error(
                            error.message
                                ?: "Unable to update course."
                        )
                }
        }
    }


    // =========================================================
    // DELETE COURSE
    // =========================================================

    fun deleteCourse(
        id: Long,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            _uiState.value =
                CourseUiState.Loading

            repository
                .deleteCourse(id)
                .onSuccess {

                    getCourses()

                    onSuccess()
                }
                .onFailure { error ->

                    _uiState.value =
                        CourseUiState.Error(
                            error.message
                                ?: "Unable to delete course."
                        )
                }
        }
    }
}