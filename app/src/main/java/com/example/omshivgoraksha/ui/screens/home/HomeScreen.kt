package com.example.omshivgoraksha.ui.screens.home

//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import com.example.omshivgoraksha.ui.components.UserRole
//import com.example.omshivgoraksha.ui.screens.MainScreen

//@Composable
//fun HomeScreen() {
//
//    MainScreen(
//
//        // Temporary value for testing
//        userRole = UserRole.STUDENT,
//
//        // Current selected sidebar item
//        currentRoute = "home",
//
//        // Sidebar navigation
//        onNavigate = { route ->
//
//            // Navigation will be connected later
//            println("Navigate to: $route")
//        },
//
//        // Logout
//        onLogout = {
//
//            // Logout will be connected later
//            println("Logout clicked")
//        }
//
//    ) { paddingValues ->
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues),
//
//            contentAlignment = Alignment.Center
//        ) {
//
//            Text(
//                text = "Home Screen"
//            )
//        }
//    }
//}

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.omshivgoraksha.ui.components.UserRole
import com.example.omshivgoraksha.ui.screens.MainScreen
import com.example.omshivgoraksha.ui.theme.GoldDark
import com.example.omshivgoraksha.ui.theme.GoldPrimary
import com.example.omshivgoraksha.ui.theme.TextPrimary
import com.example.omshivgoraksha.ui.theme.TextSecondary


// =============================================================
// Hardcoded Announcement Model
// =============================================================

data class Announcement(
    val title: String,
    val description: String,
    val date: String
)


// =============================================================
// Hardcoded Course Model
// =============================================================

data class FeaturedCourse(
    val title: String,
    val description: String,
    val duration: String
)


// =============================================================
// Home Screen
// =============================================================

@Composable
fun HomeScreen( currentRoute: String,
                onNavigate: (String) -> Unit,
                onLogout: () -> Unit) {

    MainScreen(

        userRole = UserRole.STUDENT,

        currentRoute = currentRoute,

        onNavigate = onNavigate,

        onLogout = onLogout

    ) { paddingValues ->

        HomeContent(
            paddingValues = paddingValues
        )
    }
}


// =============================================================
// Home Content
// =============================================================

@Composable
private fun HomeContent(
    paddingValues: PaddingValues
) {

    // ---------------------------------------------------------
    // Hardcoded Announcements
    // ---------------------------------------------------------

    val announcements = remember {

        listOf(

            Announcement(
                title = "New Batch Starting Soon",
                description = "Admissions are now open for the upcoming learning batch.",
                date = "07 September 2026"
            ),

            Announcement(
                title = "Assignment Submission",
                description = "Students are requested to complete and submit their pending assignments.",
                date = "05 September 2026"
            ),

            Announcement(
                title = "New Learning Material",
                description = "New course materials and learning resources are now available.",
                date = "02 September 2026"
            )
        )
    }


    // ---------------------------------------------------------
    // Hardcoded Featured Courses
    // ---------------------------------------------------------

    val courses = remember {

        listOf(

            FeaturedCourse(
                title = "Java Programming",
                description = "Learn Java from basics to advanced concepts.",
                duration = "3 Months"
            ),

            FeaturedCourse(
                title = "Spring Boot",
                description = "Build modern and scalable backend applications.",
                duration = "2 Months"
            ),

            FeaturedCourse(
                title = "Android Development",
                description = "Build modern Android applications using Kotlin.",
                duration = "3 Months"
            ),

            FeaturedCourse(
                title = "Microservices",
                description = "Learn how to design and build distributed systems.",
                duration = "2 Months"
            )
        )
    }


    // ---------------------------------------------------------
    // Announcement Carousel State
    // ---------------------------------------------------------

    var currentAnnouncementIndex by remember {
        mutableIntStateOf(0)
    }


    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(
                horizontal = 16.dp
            ),

        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 24.dp
        ),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // =====================================================
        // LATEST ANNOUNCEMENT
        // =====================================================

        item {

            SectionTitle(
                title = "Latest Announcement",
                icon = Icons.Default.Notifications
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            LatestAnnouncementCard(
                announcement = announcements.first()
            )
        }


        // =====================================================
        // PREVIOUS ANNOUNCEMENTS
        // =====================================================

        item {

            SectionTitle(
                title = "Announcements",
                icon = Icons.Default.Campaign
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            AnnouncementCarousel(

                announcements = announcements.drop(1),

                currentIndex = currentAnnouncementIndex,

                onIndexChange = { index ->
                    currentAnnouncementIndex = index
                }
            )
        }


        // =====================================================
        // FEATURED COURSES
        // =====================================================

        item {

            SectionTitle(
                title = "Featured Courses",
                icon = Icons.Default.MenuBook
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }


        // -----------------------------------------------------
        // Course List
        // -----------------------------------------------------

        items(
            items = courses
        ) { course ->

            FeaturedCourseCard(
                course = course,
                onClick = {

                    println(
                        "Course clicked: ${course.title}"
                    )
                }
            )
        }
    }
}


// =============================================================
// Section Title
// =============================================================

@Composable
private fun SectionTitle(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldDark
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )
    }
}


// =============================================================
// Latest Announcement Card
// =============================================================

@Composable
private fun LatestAnnouncementCard(
    announcement: Announcement
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            GoldPrimary.copy(
                                alpha = 0.15f
                            )
                        )
                        .padding(10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = GoldDark
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {

                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = GoldDark
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = announcement.date,
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            Text(
                text = announcement.description,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
        }
    }
}


// =============================================================
// Announcement Carousel
// =============================================================

@Composable
private fun AnnouncementCarousel(
    announcements: List<Announcement>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit
) {

    if (announcements.isEmpty()) {
        return
    }


    val announcement =
        announcements[currentIndex.coerceIn(
            0,
            announcements.lastIndex
        )]


    Column {

        // -----------------------------------------------------
        // Horizontal Announcement Card
        // -----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Previous
            Text(
                text = "‹",
                style = MaterialTheme.typography.headlineMedium,
                color = GoldDark,
                modifier = Modifier
                    .clickable {

                        val newIndex =
                            if (currentIndex == 0) {
                                announcements.lastIndex
                            } else {
                                currentIndex - 1
                            }

                        onIndexChange(newIndex)
                    }
                    .padding(8.dp)
            )


            // Card
            Card(

                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GoldPrimary.copy(
                        alpha = 0.08f
                    )
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = GoldDark
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = announcement.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = announcement.date,
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }


            // Next
            Text(
                text = "›",
                style = MaterialTheme.typography.headlineMedium,
                color = GoldDark,
                modifier = Modifier
                    .clickable {

                        val newIndex =
                            if (currentIndex == announcements.lastIndex) {
                                0
                            } else {
                                currentIndex + 1
                            }

                        onIndexChange(newIndex)
                    }
                    .padding(8.dp)
            )
        }


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // -----------------------------------------------------
        // Three Dots
        // -----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            announcements.forEachIndexed { index, _ ->

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(
                            if (index == currentIndex) {
                                18.dp
                            } else {
                                7.dp
                            }
                        )
                        .height(7.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentIndex) {
                                GoldPrimary
                            } else {
                                GoldPrimary.copy(
                                    alpha = 0.30f
                                )
                            }
                        )
                )
            }
        }
    }
}


// =============================================================
// Featured Course Card
// =============================================================

@Composable
private fun FeaturedCourseCard(
    course: FeaturedCourse,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // -------------------------------------------------
            // Course Icon
            // -------------------------------------------------

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        GoldPrimary.copy(
                            alpha = 0.15f
                        )
                    )
                    .padding(12.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = GoldDark
                )
            }


            Spacer(
                modifier = Modifier.width(14.dp)
            )


            // -------------------------------------------------
            // Course Information
            // -------------------------------------------------

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = course.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = course.duration,
                    style = MaterialTheme.typography.labelMedium,
                    color = GoldDark
                )
            }


            // -------------------------------------------------
            // Arrow
            // -------------------------------------------------

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Open Course",
                tint = GoldDark
            )
        }
    }
}


