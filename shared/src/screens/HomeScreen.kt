
package screens

// Importaciones para detectar cuando el usuario selecciona un elemento.
import androidx.compose.foundation.clickable

// Importaciones para organizar los elementos de la interfaz.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

// Componentes que permiten mostrar listas dinámicas.
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

// Componentes visuales de Material 3.
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

// Importaciones para utilizar funciones y elementos de Compose.
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Importa el modelo que contiene la información de cada tarea.
import models.Task

// Indica que esta pantalla utiliza una API experimental de Material 3.
@OptIn(ExperimentalMaterial3Api::class)

// Función encargada de construir la pantalla principal de la aplicación.
@Composable
fun HomeScreen(
    // Recibe la lista de tareas que se mostrarán en pantalla.
    tasks: List<Task>,

    // Función que permite navegar al formulario de creación.
    onNavigateToCreate: () -> Unit,

    // Función que permite abrir los detalles de una tarea seleccionada.
    onNavigateToDetail: (Task) -> Unit
) {

    // Scaffold proporciona la estructura principal de la pantalla.
    Scaffold(

        // Barra superior que muestra el título de la aplicación.
        topBar = {
            TopAppBar(
                title = {
                    Text("Gestión de Tareas - KMP")
                }
            )
        },

        // Botón flotante utilizado para crear nuevas tareas.
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->

        // LazyColumn permite mostrar listas de manera eficiente,
        // cargando los elementos conforme se necesitan visualizar.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // Recorre la lista recibida y genera una tarjeta por cada tarea.
            items(
                items = tasks,

                // Utiliza el identificador de cada tarea como clave única.
                key = { task -> task.id }
            ) { task ->

                // Card permite agrupar visualmente la información de una tarea.
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)

                        // Permite seleccionar una tarea para consultar sus detalles.
                        .clickable {
                            onNavigateToDetail(task)
                        },

                    // Define la elevación o sombra de la tarjeta.
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    // Organiza verticalmente la información de cada tarea.
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        // Muestra el nombre de la tarea.
                        Text(
                            text = task.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        // Agrega un espacio entre los elementos.
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        // Muestra la prioridad asignada a la tarea.
                        Text(
                            text = "Prioridad: ${task.priority}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        // Comprueba el estado de la tarea y muestra
                        // si está completada o todavía está pendiente.
                        Text(
                            text = if (task.isCompleted) {
                                "Completada"
                            } else {
                                "Pendiente"
                            },

                            // Cambia el color dependiendo del estado de la tarea.
                            color = if (task.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                    }
                }
            }
        }
    }
}
