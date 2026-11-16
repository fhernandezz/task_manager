import androidx.compose.runtime.*
import .models.Task
import .screens.CreateTaskScreen
import .screens.DetailScreen
import .kotlin.screens.HomeScreen

sealed class CurrentScreen {
    object Home : CurrentScreen()
    object Create : CurrentScreen()
    data class Detail(val task: Task) : CurrentScreen()
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<CurrentScreen>(CurrentScreen.Home) }
    
    // Lista de tareas inicial de ejemplo
    val tasks = remember { mutableStateListOf(
        Task(1, "Investigar KMP", "Revisar arquitectura y ventajas", "Alta", false),
        Task(2, "Diseñar UI", "Crear pantallas en Compose Multiplatform", "Media", false)
    )}

    when (val screen = currentScreen) {
        is CurrentScreen.Home -> {
            HomeScreen(
                tasks = tasks,
                onNavigateToCreate = { currentScreen = CurrentScreen.Create },
                onNavigateToDetail = { task -> currentScreen = CurrentScreen.Detail(task) }
            )
        }
        is CurrentScreen.Create -> {
            CreateTaskScreen(
                onTaskCreated = { newTask ->
                    tasks.add(newTask)
                    currentScreen = CurrentScreen.Home
                },
                onBack = { currentScreen = CurrentScreen.Home }
            )
        }
        is CurrentScreen.Detail -> {
            DetailScreen(
                task = screen.task,
                onStatusChanged = { newStatus ->
                    screen.task.isCompleted = newStatus
                },
                onBack = { currentScreen = CurrentScreen.Home }
            )
        }
    }
}