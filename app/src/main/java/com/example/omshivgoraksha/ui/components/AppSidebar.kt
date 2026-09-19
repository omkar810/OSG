package com.example.omshivgoraksha.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.omshivgoraksha.ui.theme.GoldPrimary
import com.example.omshivgoraksha.ui.theme.GoldDark
import com.example.omshivgoraksha.ui.theme.TextPrimary
import com.example.omshivgoraksha.ui.theme.TextSecondary

enum class UserRole {
    STUDENT,
    ADMIN
}

data class SidebarItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun AppSidebar(
    userRole: UserRole,
    currentRoute: String,
    onItemClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val menuItems = when (userRole) {

        UserRole.STUDENT -> listOf(
            SidebarItem(
                title = "Dashboard",
                icon = Icons.Default.Dashboard,
                route = "home"
            ),
            SidebarItem(
                title = "My Courses",
                icon = Icons.Default.MenuBook,
                route = "learning"
            ),
//            SidebarItem(
//                title = "Assignments",
//                icon = Icons.Default.Assignment,
//                route = "assignments"
//            ),
//            SidebarItem(
//                title = "Notifications",
//                icon = Icons.Default.Notifications,
//                route = "notifications"
//            ),
//            SidebarItem(
//                title = "Profile",
//                icon = Icons.Default.Person,
//                route = "profile"
//            ),
//            SidebarItem(
//                title = "Settings",
//                icon = Icons.Default.Settings,
//                route = "settings"
//            )
        )

        UserRole.ADMIN -> listOf(
            SidebarItem(
                title = "Dashboard",
                icon = Icons.Default.Dashboard,
                route = "home"
            ),
            SidebarItem(
                title = "Students",
                icon = Icons.Default.Group,
                route = "students"
            ),
            SidebarItem(
                title = "Courses",
                icon = Icons.Default.MenuBook,
                route = "courses"
            ),
            SidebarItem(
                title = "Assignments",
                icon = Icons.Default.Assignment,
                route = "assignments"
            ),
            SidebarItem(
                title = "Reports",
                icon = Icons.Default.BarChart,
                route = "reports"
            ),
            SidebarItem(
                title = "Notifications",
                icon = Icons.Default.Notifications,
                route = "notifications"
            ),
            SidebarItem(
                title = "Profile",
                icon = Icons.Default.Person,
                route = "profile"
            ),
            SidebarItem(
                title = "Settings",
                icon = Icons.Default.Settings,
                route = "settings"
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            )
    ) {

        // ---------------------------------------------------------
        // Logo / Application Name
        // ---------------------------------------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Om Shiv Goraksha",
                style = MaterialTheme.typography.titleLarge,
                color = GoldDark
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------------------------------------------------
        // User Role
        // ---------------------------------------------------------

        Text(
            text = when (userRole) {
                UserRole.ADMIN -> "ADMIN"
                UserRole.STUDENT -> "STUDENT"
            },
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 4.dp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ---------------------------------------------------------
        // Menu Items
        // ---------------------------------------------------------

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            menuItems.forEach { item ->

                SidebarMenuItem(
                    item = item,
                    selected = currentRoute == item.route,
                    onClick = {
                        onItemClick(item.route)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // ---------------------------------------------------------
        // Logout
        // ---------------------------------------------------------

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .clickable {
                    onLogoutClick()
                }
                .padding(
                    horizontal = 12.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun SidebarMenuItem(
    item: SidebarItem,
    selected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor =
        if (selected) {
            GoldPrimary.copy(alpha = 0.15f)
        } else {
            MaterialTheme.colorScheme.surface
        }

    val contentColor =
        if (selected) {
            GoldDark
        } else {
            TextPrimary
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(backgroundColor)
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 13.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = contentColor
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor
        )
    }
}
