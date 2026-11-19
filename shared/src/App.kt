
package org.example.taskmanager

// Importaciones necesarias para manejar estados y componentes de Compose.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// Importa el modelo que representa cada tarea.
import models.Task

// Importa las pantallas utilizadas por la aplicación.
import screens.CreateTaskScreen
import screens.DetailScreen
import screens.HomeScreen

// Define los diferentes estados de navegación de la aplicación.
private sealed class CurrentScreen {
    object Home : CurrentScreen()
    object Create : CurrentScreen()
    data class Detail(val taskId: Int) : CurrentScreen()
}

@Composable
fun App() {

    // Guarda cuál pantalla se encuentra activa actualmente.
    var currentScreen by remember {
        mutableStateOf<CurrentScreen>(CurrentScreen.Home)
    }

    // Crea una lista observable para almacenar las tareas.
    // Compose detecta los cambios y actualiza la interfaz automáticamente.
    val tasks = remember {
        mutableStateListOf(
            Task(1, "Investigar KMP", "Revisar arquitectura y ventajas", "Alta"),
            Task(2, "Diseñar UI", "Crear pantallas en Compose Multiplatform", "Media")
        )
    }

    // Determina qué pantalla debe mostrarse según el estado actual.
    when (val screen = currentScreen) {

        // Muestra la pantalla principal con el listado de tareas.
        CurrentScreen.Home -> {
            HomeScreen(
                tasks = tasks,

                // Cambia a la pantalla de creación de tareas.
                onNavigateToCreate = {
                    currentScreen = CurrentScreen.Create
                },

                // Abre los detalles de la tarea seleccionada.
                onNavigateToDetail = { task ->
                    currentScreen = CurrentScreen.Detail(task.id)
                }
            )
        }

        // Muestra el formulario para registrar una nueva tarea.
        CurrentScreen.Create -> {
            CreateTaskScreen(
                onTaskCreated = { newTask ->

                    // Calcula el siguiente identificador disponible.
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1

                    // Agrega la nueva tarea a la lista con su identificador.
                    tasks.add(newTask.copy(id = nextId))

                    // Regresa a la pantalla principal.
                    currentScreen = CurrentScreen.Home
                },

                // Regresa al inicio sin registrar una tarea.
                onBack = {
                    currentScreen = CurrentScreen.Home
                }
            )
        }

        // Muestra los detalles de la tarea seleccionada.
        is CurrentScreen.Detail -> {

            // Busca la tarea utilizando su identificador.
            val selectedTask = tasks.firstOrNull {
                it.id == screen.taskId
            }

            if (selectedTask == null) {

                // Si la tarea no existe, muestra nuevamente la pantalla principal.
                HomeScreen(
                    tasks = tasks,
                    onNavigateToCreate = {
                        currentScreen = CurrentScreen.Create
                    },
                    onNavigateToDetail = { task ->
                        currentScreen = CurrentScreen.Detail(task.id)
                    }
                )

            } else {

                DetailScreen(
                    task = selectedTask,

                    // Recibe el nuevo estado y actualiza la tarea correspondiente.
                    onStatusChanged = { newStatus ->

                        // Busca la posición de la tarea dentro de la lista.
                        val taskIndex = tasks.indexOfFirst {
                            it.id == screen.taskId
                        }

                        // Comprueba que la tarea exista antes de actualizarla.
                        if (taskIndex >= 0) {

                            // Crea una copia de la tarea con el nuevo estado.
                            tasks[taskIndex] =
                                tasks[taskIndex].copy(
                                    isCompleted = newStatus
                                )
                        }
                    },

                    // Regresa a la pantalla principal.
                    onBack = {
                        currentScreen = CurrentScreen.Home
                    }
                )
            }
        }
    }
}
