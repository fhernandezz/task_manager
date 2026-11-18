
package org.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import models.Task
import screens.CreateTaskScreen
import screens.DetailScreen
import screens.HomeScreen

private sealed class CurrentScreen {
    object Home : CurrentScreen()
    object Create : CurrentScreen()
    data class Detail(val taskId: Int) : CurrentScreen()
}

@Composable
fun App() {
    var currentScreen by remember {
        mutableStateOf<CurrentScreen>(CurrentScreen.Home)
    }

    val tasks = remember {
        mutableStateListOf(
            Task(1, "Investigar KMP", "Revisar arquitectura y ventajas", "Alta"),
            Task(2, "Diseñar UI", "Crear pantallas en Compose Multiplatform", "Media")
        )
    }

    when (val screen = currentScreen) {

        CurrentScreen.Home -> {
            HomeScreen(
                tasks = tasks,
                onNavigateToCreate = {
                    currentScreen = CurrentScreen.Create
                },
                onNavigateToDetail = { task ->
                    currentScreen = CurrentScreen.Detail(task.id)
                },
                onDeleteTask = { task ->
                    tasks.removeAll { it.id == task.id }
                }
            )
        }

        CurrentScreen.Create -> {
            CreateTaskScreen(
                onTaskCreated = { newTask ->
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    tasks.add(newTask.copy(id = nextId))
                    currentScreen = CurrentScreen.Home
                },
                onBack = {
                    currentScreen = CurrentScreen.Home
                }
            )
        }

        is CurrentScreen.Detail -> {
            val selectedTask = tasks.firstOrNull {
                it.id == screen.taskId
            }

            if (selectedTask == null) {
                currentScreen = CurrentScreen.Home
            } else {
                DetailScreen(
                    task = selectedTask,
                    onStatusChanged = { newStatus ->
                        val taskIndex = tasks.indexOfFirst {
                            it.id == screen.taskId
                        }

                        if (taskIndex >= 0) {
                            tasks[taskIndex] =
                                tasks[taskIndex].copy(
                                    isCompleted = newStatus
                                )
                        }
                    },
                    onBack = {
                        currentScreen = CurrentScreen.Home
                    }
                )
            }
        }
    }
}
