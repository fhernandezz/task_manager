package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Task

@Composable
fun DetailScreen(
    task: Task,
    onStatusChanged: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    var isCompleted by remember { mutableStateOf(task.isCompleted) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalle de Tarea") }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text(text = task.name, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: ${task.description}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Prioridad: ${task.priority}", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Marcar como completada: ")
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = { 
                        isCompleted = it
                        onStatusChanged(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Volver al Inicio")
            }
        }
    }
}