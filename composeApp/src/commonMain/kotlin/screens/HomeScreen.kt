package screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Task

@Composable
fun HomeScreen(
    tasks: List<Task>,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Task) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Tareas - KMP") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onNavigateToDetail(task) },
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = task.name, style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Prioridad: ${task.priority}", style = MaterialTheme.typography.caption)
                        Text(
                            text = if (task.isCompleted) "Completada" else "Pendiente",
                            color = if (task.isCompleted) androidx.compose.ui.graphics.Color.Green else androidx.compose.ui.graphics.Color.Red
                        )
                    }
                }
            }
        }
    }
}