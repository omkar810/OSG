package com.example.omshivgoraksha.ui.screens.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.omshivgoraksha.data.model.course.response.CourseResponse
import com.example.omshivgoraksha.data.model.course.request.CourseRequest
import com.example.omshivgoraksha.ui.components.UserRole
import com.example.omshivgoraksha.ui.screens.MainScreen
import com.example.omshivgoraksha.ui.theme.GoldDark
import com.example.omshivgoraksha.ui.theme.GoldPrimary
import com.example.omshivgoraksha.ui.theme.TextSecondary
import com.example.omshivgoraksha.data.model.course.viewmodel.CourseUiState
import com.example.omshivgoraksha.data.model.course.viewmodel.CourseViewModel

@Composable
fun CourseScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: CourseViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var showCourseDialog by remember {
        mutableStateOf(false)
    }

    var selectedCourse by remember {
        mutableStateOf<CourseResponse?>(null)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // ---------------------------------------------------------
    // Load Courses
    // ---------------------------------------------------------

    LaunchedEffect(Unit) {
        viewModel.getCourses()
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
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // -------------------------------------------------
            // Header
            // -------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Courses",
                        style = MaterialTheme.typography.headlineMedium,
                        color = GoldDark
                    )

                    Text(
                        text = "Manage your courses",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }


                Button(
                    onClick = {

                        selectedCourse = null
                        showCourseDialog = true

                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Course"
                    )

                    Text(
                        text = " Add"
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // -------------------------------------------------
            // Course List
            // -------------------------------------------------

            when (val state = uiState) {

                is CourseUiState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator(
                            color = GoldPrimary
                        )
                    }
                }


                is CourseUiState.Error -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }


                is CourseUiState.Success -> {

                    if (state.courses.isEmpty()) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "No courses available",
                                color = TextSecondary
                            )
                        }

                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            items(
                                items = state.courses,
                                key = { course ->
                                    course.id ?: 0L
                                }
                            ) { course ->

                                CourseCard(
                                    course = course,

                                    onEdit = {

                                        selectedCourse = course
                                        showCourseDialog = true
                                    },

                                    onDelete = {

                                        selectedCourse = course
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    // ---------------------------------------------------------
    // Add / Edit Dialog
    // ---------------------------------------------------------

    if (showCourseDialog) {

        CourseFormDialog(

            course = selectedCourse,

            onDismiss = {
                showCourseDialog = false
            },

            onSave = { request ->

                if (selectedCourse == null) {

                    viewModel.createCourse(
                        request = request,
                        onSuccess = {
                            showCourseDialog = false
                        }
                    )

                } else {

                    viewModel.updateCourse(
                        id = selectedCourse!!.id!!,
                        request = request,
                        onSuccess = {
                            showCourseDialog = false
                        }
                    )
                }
            }
        )
    }


    // ---------------------------------------------------------
    // Delete Dialog
    // ---------------------------------------------------------

    if (showDeleteDialog && selectedCourse != null) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text(
                    text = "Delete Course"
                )
            },

            text = {
                Text(
                    text = "Are you sure you want to delete \"${selectedCourse!!.courseName}\"?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        selectedCourse!!.id?.let { id ->

                            viewModel.deleteCourse(
                                id = id,
                                onSuccess = {
                                    showDeleteDialog = false
                                    selectedCourse = null
                                }
                            )
                        }
                    }
                ) {

                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
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


// =============================================================
// Course Card
// =============================================================

@Composable
private fun CourseCard(
    course: CourseResponse,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    course.courseName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            color = GoldDark
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    course.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }


                IconButton(
                    onClick = onEdit
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Course",
                        tint = GoldDark
                    )
                }


                IconButton(
                    onClick = onDelete
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Course",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Duration: ${course.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

//                Text(
//                    text = course.status,
//                    style = MaterialTheme.typography.labelLarge,
//                    color = GoldDark
//                )
            }
        }
    }
}


// =============================================================
// Add / Edit Course Dialog
// =============================================================

@Composable
private fun CourseFormDialog(
    course: CourseResponse?,
    onDismiss: () -> Unit,
    onSave: (CourseRequest) -> Unit
) {

    var name by remember {
        mutableStateOf(course?.courseName ?: "")
    }

    var description by remember {
        mutableStateOf(course?.description ?: "")
    }

    var duration by remember {
        mutableStateOf(course?.duration ?: "")
    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text(
                text = if (course == null) {
                    "Add Course"
                } else {
                    "Edit Course"
                },
                color = GoldDark
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Course Name")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )


                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = {
                        Text("Description")
                    },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )


                OutlinedTextField(
                    value = duration,
                    onValueChange = {
                        duration = it
                    },
                    label = {
                        Text("Duration")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    if (name.isNotBlank()) {

                        onSave(
                            CourseRequest(
                                courseName = name.trim(),
                                description = description.trim(),
                                duration = duration.trim()
                            )
                        )
                    }
                }
            ) {

                Text(
                    text = if (course == null) {
                        "Create"
                    } else {
                        "Update"
                    },
                    color = GoldDark
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Cancel"
                )
            }
        }
    )
}