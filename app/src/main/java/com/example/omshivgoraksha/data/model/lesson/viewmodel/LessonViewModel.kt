package com.example.omshivgoraksha.data.model.lesson.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omshivgoraksha.data.model.lesson.request.LessonRequest
import com.example.omshivgoraksha.data.model.lesson.response.LessonResponse
import com.example.omshivgoraksha.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

sealed class LessonUiState {

    data object Idle : LessonUiState()

    data object Loading : LessonUiState()

    data class Success(
        val lessons: List<LessonResponse>
    ) : LessonUiState()

    data class Error(
        val message: String
    ) : LessonUiState()
}


class LessonViewModel(
    private val repository: LessonRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LessonUiState>(
            LessonUiState.Idle
        )

    val uiState: StateFlow<LessonUiState> =
        _uiState.asStateFlow()


    // =========================================================
    // GET LESSONS
    // =========================================================

    fun getLessons(
        courseId: Long
    ) {

        viewModelScope.launch {

            _uiState.value =
                LessonUiState.Loading


            repository
                .getLessonsByCourse(courseId)
                .onSuccess { lessons ->

                    _uiState.value =
                        LessonUiState.Success(
                            lessons
                        )
                }
                .onFailure { exception ->

                    _uiState.value =
                        LessonUiState.Error(
                            exception.message
                                ?: "Failed to load lessons."
                        )
                }
        }
    }


    // =========================================================
    // CREATE LESSON
    // =========================================================

    fun createLesson(

        request: LessonRequest,

        documentFiles: List<File>,

        videoFile: File?,

        onSuccess: () -> Unit

    ) {

        viewModelScope.launch {

            _uiState.value =
                LessonUiState.Loading


            repository
                .createLesson(
                    request,
                    documentFiles,
                    videoFile
                )
                .onSuccess {

                    getLessons(
                        request.courseId
                    )

                    onSuccess()
                }
                .onFailure { exception ->

                    _uiState.value =
                        LessonUiState.Error(
                            exception.message
                                ?: "Failed to create lesson."
                        )
                }
        }
    }


    // =========================================================
    // UPDATE LESSON
    // =========================================================

    fun updateLesson(

        lessonId: Long,

        request: LessonRequest,

        documentFiles: List<File>,

        videoFile: File?,

        onSuccess: () -> Unit

    ) {

        viewModelScope.launch {

            _uiState.value =
                LessonUiState.Loading


            repository
                .updateLesson(
                    lessonId,
                    request,
                    documentFiles,
                    videoFile
                )
                .onSuccess {

                    getLessons(
                        request.courseId
                    )

                    onSuccess()
                }
                .onFailure { exception ->

                    _uiState.value =
                        LessonUiState.Error(
                            exception.message
                                ?: "Failed to update lesson."
                        )
                }
        }
    }


    // =========================================================
    // DELETE LESSON
    // =========================================================

    fun deleteLesson(

        lessonId: Long,

        courseId: Long,

        onSuccess: () -> Unit

    ) {

        viewModelScope.launch {

            repository
                .deleteLesson(lessonId)
                .onSuccess {

                    getLessons(courseId)

                    onSuccess()
                }
                .onFailure { exception ->

                    _uiState.value =
                        LessonUiState.Error(
                            exception.message
                                ?: "Failed to delete lesson."
                        )
                }
        }
    }
}