package com.example.omshivgoraksha.ui.screens.lesson

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.omshivgoraksha.data.local.FileUtils
import com.example.omshivgoraksha.data.model.lesson.request.LessonRequest
import com.example.omshivgoraksha.data.model.lesson.response.LessonResponse
import com.example.omshivgoraksha.data.model.lesson.viewmodel.LessonUiState
import com.example.omshivgoraksha.data.model.lesson.viewmodel.LessonViewModel
import com.example.omshivgoraksha.data.remote.RetrofitClient
import com.example.omshivgoraksha.data.repository.LessonRepository
import com.example.omshivgoraksha.ui.components.UserRole
import com.example.omshivgoraksha.ui.screens.MainScreen
import com.example.omshivgoraksha.ui.theme.GoldDark
import com.example.omshivgoraksha.ui.theme.GoldPrimary
import com.example.omshivgoraksha.ui.theme.TextSecondary
import java.io.File
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.omshivgoraksha.data.model.course.viewmodel.LessonViewModelFactory

@Composable
fun LessonScreen(
    courseId: Long,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {

    val context =
        LocalContext.current

    val apiService =
        RetrofitClient.apiService

    val repository =
        remember {
            LessonRepository(
                apiService
            )
        }

    val viewModel: LessonViewModel =
        viewModel(
            factory =
                LessonViewModelFactory(
                    repository
                )
        )

    val uiState by
    viewModel.uiState.collectAsState()


    var showLessonDialog by
    remember {
        mutableStateOf(false)
    }

    var selectedLesson by
    remember {
        mutableStateOf<LessonResponse?>(null)
    }

    var showDeleteDialog by
    remember {
        mutableStateOf(false)
    }


    // =========================================================
    // LOAD LESSONS
    // =========================================================

    LaunchedEffect(courseId) {

        viewModel.getLessons(
            courseId
        )
    }


    MainScreen(

        userRole = UserRole.ADMIN,

        currentRoute = "courses",

        onNavigate = onNavigate,

        onLogout = onLogout

    ) { paddingValues ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 16.dp
                )
        ) {


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // =================================================
            // HEADER
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                IconButton(
                    onClick = onBack
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowBack,

                        contentDescription =
                            "Back",

                        tint = GoldDark
                    )
                }


                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text = "Lessons",

                        style =
                            MaterialTheme
                                .typography
                                .headlineMedium,

                        color = GoldDark
                    )

                    Text(
                        text =
                            "Manage course lessons",

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,

                        color = TextSecondary
                    )
                }


                Button(

                    onClick = {

                        selectedLesson =
                            null

                        showLessonDialog =
                            true
                    },

                    shape =
                        RoundedCornerShape(
                            12.dp
                        ),

                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    GoldPrimary,

                                contentColor =
                                    MaterialTheme
                                        .colorScheme
                                        .onPrimary
                            )

                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            "Add Lesson"
                    )

                    Text(
                        text = " Add"
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // =================================================
            // LESSON LIST
            // =================================================

            when (val state = uiState) {

                is LessonUiState.Idle -> {

                    // Nothing
                }


                is LessonUiState.Loading -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        CircularProgressIndicator(
                            color =
                                GoldPrimary
                        )
                    }
                }


                is LessonUiState.Error -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(
                            text =
                                state.message,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }
                }


                is LessonUiState.Success -> {

                    if (
                        state.lessons.isEmpty()
                    ) {

                        Box(

                            modifier =
                                Modifier.fillMaxSize(),

                            contentAlignment =
                                Alignment.Center

                        ) {

                            Text(
                                text =
                                    "No lessons available",

                                color =
                                    TextSecondary
                            )
                        }

                    } else {

                        LazyColumn(

                            modifier =
                                Modifier.fillMaxSize(),

                            verticalArrangement =
                                Arrangement.spacedBy(
                                    12.dp
                                )

                        ) {

                            items(

                                items =
                                    state.lessons,

                                key = { lesson ->

                                    requireNotNull(
                                        lesson.lessonId
                                    ) {
                                        "Lesson ID is null"
                                    }
                                }

                            ) { lesson ->

                                LessonCard(

                                    lesson = lesson,

                                    onEdit = {

                                        selectedLesson =
                                            lesson

                                        showLessonDialog =
                                            true
                                    },

                                    onDelete = {

                                        selectedLesson =
                                            lesson

                                        showDeleteDialog =
                                            true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    // =========================================================
    // ADD / EDIT LESSON
    // =========================================================

    if (showLessonDialog) {

        LessonFormDialog(

            lesson =
                selectedLesson,

            courseId =
                courseId,

            onDismiss = {

                showLessonDialog =
                    false
            },

            onSave = { request,
                       documentFiles,
                       videoFile ->


                if (
                    selectedLesson == null
                ) {

                    viewModel.createLesson(

                        request =
                            request,

                        documentFiles =
                            documentFiles,

                        videoFile =
                            videoFile,

                        onSuccess = {

                            showLessonDialog =
                                false
                        }
                    )

                } else {

                    viewModel.updateLesson(

                        lessonId =
                            selectedLesson!!
                                .lessonId!!,

                        request =
                            request,

                        documentFiles =
                            documentFiles,

                        videoFile =
                            videoFile,

                        onSuccess = {

                            showLessonDialog =
                                false
                        }
                    )
                }
            }
        )
    }


    // =========================================================
    // DELETE DIALOG
    // =========================================================

    if (
        showDeleteDialog &&
        selectedLesson != null
    ) {

        AlertDialog(

            onDismissRequest = {

                showDeleteDialog =
                    false
            },

            title = {

                Text(
                    text =
                        "Delete Lesson"
                )
            },

            text = {

                Text(
                    text =
                        "Are you sure you want to delete \"${selectedLesson!!.lessonName}\"?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        selectedLesson!!
                            .lessonId
                            ?.let { lessonId ->

                                viewModel.deleteLesson(

                                    lessonId =
                                        lessonId,

                                    courseId =
                                        courseId,

                                    onSuccess = {

                                        showDeleteDialog =
                                            false

                                        selectedLesson =
                                            null
                                    }
                                )
                            }
                    }

                ) {

                    Text(
                        text = "Delete",

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showDeleteDialog =
                            false
                    }

                ) {

                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
}

@Composable
private fun LessonCard(
    lesson: LessonResponse,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            lesson.lessonName
                                ?: "Untitled Lesson",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        color =
                            GoldDark
                    )


                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )


                    Text(

                        text =
                            lesson.description
                                ?: "",

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,

                        color =
                            TextSecondary,

                        maxLines = 2
                    )
                }


                IconButton(
                    onClick = onEdit
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            "Edit Lesson",

                        tint =
                            GoldDark
                    )
                }


                IconButton(
                    onClick = onDelete
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            "Delete Lesson",

                        tint =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            Row {

                Text(

                    text =
                        "Duration: ${lesson.duration ?: "-"}",

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        TextSecondary
                )


                Spacer(
                    modifier =
                        Modifier.weight(1f)
                )


                if (
                    !lesson.videoName
                        .isNullOrBlank()
                ) {

                    Text(

                        text = "Video",

                        style =
                            MaterialTheme
                                .typography
                                .labelLarge,

                        color =
                            GoldDark
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonFormDialog(

    lesson: LessonResponse?,

    courseId: Long,

    onDismiss: () -> Unit,

    onSave: (
        LessonRequest,
        List<File>,
        File?
    ) -> Unit

) {

    val context =
        LocalContext.current


    // =========================================================
    // FORM VALUES
    // =========================================================

    var lessonName by
    remember {
        mutableStateOf(
            lesson?.lessonName ?: ""
        )
    }


    var thumbnail by
    remember {
        mutableStateOf(
            lesson?.thumbnail ?: ""
        )
    }


    var duration by
    remember {
        mutableStateOf(
            lesson?.duration ?: ""
        )
    }


    var description by
    remember {
        mutableStateOf(
            lesson?.description ?: ""
        )
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    var lessonNameError by
    remember {
        mutableStateOf<String?>(null)
    }


    var thumbnailError by
    remember {
        mutableStateOf<String?>(null)
    }


    var durationError by
    remember {
        mutableStateOf<String?>(null)
    }


    var descriptionError by
    remember {
        mutableStateOf<String?>(null)
    }


    // =========================================================
    // DOCUMENTS
    // =========================================================

    var selectedDocumentUris by
    remember {
        mutableStateOf<List<Uri>>(
            emptyList()
        )
    }


    // =========================================================
    // VIDEO
    // =========================================================

    var selectedVideoUri by
    remember {
        mutableStateOf<Uri?>(null)
    }


    // =========================================================
    // DOCUMENT PICKER
    // =========================================================

    val documentPicker =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .OpenMultipleDocuments()

        ) { uris ->

            selectedDocumentUris =
                uris
        }


    // =========================================================
    // VIDEO PICKER
    // =========================================================

    val videoPicker =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .GetContent()

        ) { uri ->

            selectedVideoUri =
                uri
        }


    // =========================================================
    // VALIDATION
    // =========================================================

    fun validateForm(): Boolean {

        var isValid = true


        lessonNameError =
            when {

                lessonName.isBlank() ->
                    "Lesson name is required."

                lessonName.trim()
                    .length < 2 ->
                    "Lesson name must contain at least 2 characters."

                else ->
                    null
            }


        if (
            lessonNameError != null
        ) {
            isValid = false
        }


        thumbnailError =
            when {

                thumbnail.isBlank() ->
                    "Thumbnail is required."

                else ->
                    null
            }


        if (
            thumbnailError != null
        ) {
            isValid = false
        }


        durationError =
            when {

                duration.isBlank() ->
                    "Duration is required."

                else ->
                    null
            }


        if (
            durationError != null
        ) {
            isValid = false
        }


        descriptionError =
            when {

                description.isBlank() ->
                    "Description is required."

                description.trim()
                    .length < 10 ->
                    "Description must contain at least 10 characters."

                else ->
                    null
            }


        if (
            descriptionError != null
        ) {
            isValid = false
        }


        return isValid
    }


    // =========================================================
    // DIALOG
    // =========================================================

    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(

                text =
                    if (lesson == null)
                        "Add Lesson"
                    else
                        "Edit Lesson",

                color =
                    GoldDark
            )
        },


        text = {

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(
                            rememberScrollState()
                        ),

                verticalArrangement =
                    Arrangement.spacedBy(
                        10.dp
                    )
            ) {


                // =================================================
                // LESSON NAME
                // =================================================

                OutlinedTextField(

                    value =
                        lessonName,

                    onValueChange = {

                        lessonName =
                            it

                        lessonNameError =
                            null
                    },

                    label = {
                        Text("Lesson Name")
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth(),

                    isError =
                        lessonNameError != null,

                    supportingText = {

                        lessonNameError?.let {

                            Text(it)
                        }
                    }
                )


                // =================================================
                // THUMBNAIL
                // =================================================

                OutlinedTextField(

                    value =
                        thumbnail,

                    onValueChange = {

                        thumbnail =
                            it

                        thumbnailError =
                            null
                    },

                    label = {
                        Text("Thumbnail")
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth(),

                    isError =
                        thumbnailError != null,

                    supportingText = {

                        thumbnailError?.let {

                            Text(it)
                        }
                    }
                )


                // =================================================
                // DURATION
                // =================================================

                OutlinedTextField(

                    value =
                        duration,

                    onValueChange = {

                        duration =
                            it

                        durationError =
                            null
                    },

                    label = {
                        Text("Duration")
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth(),

                    isError =
                        durationError != null,

                    supportingText = {

                        durationError?.let {

                            Text(it)
                        }
                    }
                )


                // =================================================
                // DESCRIPTION
                // =================================================

                OutlinedTextField(

                    value =
                        description,

                    onValueChange = {

                        description =
                            it

                        descriptionError =
                            null
                    },

                    label = {
                        Text("Description")
                    },

                    minLines = 3,

                    modifier =
                        Modifier.fillMaxWidth(),

                    isError =
                        descriptionError != null,

                    supportingText = {

                        descriptionError?.let {

                            Text(it)
                        }
                    }
                )


                // =================================================
                // DOCUMENTS
                // =================================================

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Button(

                    onClick = {

                        documentPicker.launch(

                            arrayOf(

                                "application/pdf",

                                "application/msword",

                                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",

                                "application/vnd.ms-excel",

                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",

                                "text/plain"
                            )
                        )
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                GoldPrimary
                        )
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.AttachFile,

                        contentDescription =
                            "Documents"
                    )


                    Spacer(
                        modifier =
                            Modifier.width(4.dp)
                    )


                    Text(
                        text =
                            "SELECT DOCUMENTS"
                    )
                }


                if (
                    selectedDocumentUris
                        .isNotEmpty()
                ) {

                    Text(

                        text =
                            "${selectedDocumentUris.size} document(s) selected",

                        color =
                            GoldDark
                    )
                }


                // =================================================
                // VIDEO
                // =================================================

                Button(

                    onClick = {

                        videoPicker.launch(
                            "video/*"
                        )
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                GoldPrimary
                        )
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.VideoLibrary,

                        contentDescription =
                            "Video"
                    )


                    Spacer(
                        modifier =
                            Modifier.width(4.dp)
                    )


                    Text(
                        text =
                            "SELECT VIDEO"
                    )
                }


                if (
                    selectedVideoUri != null
                ) {

                    Text(

                        text =
                            "Video selected",

                        color =
                            GoldDark
                    )

                } else if (
                    lesson != null &&
                    !lesson.videoName
                        .isNullOrBlank()
                ) {

                    Text(

                        text =
                            "Existing video: ${lesson.videoName}",

                        color =
                            TextSecondary,

                        maxLines = 2
                    )
                }
            }
        },


        // =========================================================
        // SAVE
        // =========================================================

        confirmButton = {

            TextButton(

                onClick = {

                    if (
                        !validateForm()
                    ) {
                        return@TextButton
                    }


                    // Convert documents

                    val documentFiles =
                        selectedDocumentUris
                            .mapNotNull { uri ->

                                FileUtils.uriToFile(
                                    context,
                                    uri
                                )
                            }


                    // Convert video

                    val videoFile =
                        selectedVideoUri?.let {

                            FileUtils.uriToFile(
                                context,
                                it
                            )
                        }


                    val request =
                        LessonRequest(

                            lessonName =
                                lessonName.trim(),

                            thumbnail =
                                thumbnail.trim(),

                            duration =
                                duration.trim(),

                            description =
                                description.trim(),

                            courseId =
                                courseId
                        )


                    onSave(

                        request,

                        documentFiles,

                        videoFile
                    )
                }

            ) {

                Text(

                    text =
                        if (lesson == null)
                            "Create"
                        else
                            "Update",

                    color =
                        GoldDark
                )
            }
        },


        // =========================================================
        // CANCEL
        // =========================================================

        dismissButton = {

            TextButton(
                onClick =
                    onDismiss
            ) {

                Text(
                    text = "Cancel"
                )
            }
        }
    )
}