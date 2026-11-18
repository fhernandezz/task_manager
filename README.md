# Gestor de tareas multiplataforma

Este proyecto consiste en una aplicación de gestión de tareas desarrollada para el curso Desarrollo de Software IV de la Universidad de Costa Rica.

La idea principal es utilizar Kotlin Multiplatform para construir una aplicación que pueda ejecutarse en diferentes plataformas compartiendo la mayor parte del código. De esta manera, no es necesario desarrollar toda la lógica y la interfaz por separado para Android, iOS y Desktop.

## Funcionalidades

La aplicación permite gestionar tareas mediante tres pantallas principales:

- **Inicio:** muestra dinámicamente el listado de tareas registradas, su prioridad y su estado.
- **Crear tarea:** permite registrar nuevas tareas indicando su nombre, descripción y prioridad. También incluye validaciones básicas para evitar campos vacíos.
- **Detalle de tarea:** muestra la información de cada tarea y permite marcarla como completada o pendiente.
- **Eliminar tareas:** permite eliminar tareas registradas desde el listado principal.
- **Gestión dinámica de datos:** actualiza automáticamente la lista cuando se agregan, modifican o eliminan tareas.

Al iniciar la aplicación se muestran algunas tareas de ejemplo. Las tareas nuevas y sus cambios se mantienen en memoria durante la ejecución de la aplicación.

## Tecnologías utilizadas

- **Kotlin:** lenguaje utilizado para desarrollar la lógica de la aplicación.
- **Kotlin Multiplatform (KMP):** permite compartir código entre diferentes plataformas.
- **Compose Multiplatform:** se utiliza para construir interfaces gráficas compartidas.
- **Material 3:** proporciona los componentes visuales utilizados en la interfaz.
- **Kotlin Toolchain:** permite configurar, compilar, ejecutar y probar los módulos del proyecto.

## Gestión de datos

Los datos de las tareas se administran mediante mutableStateListOf, una colección observable de Compose que permite actualizar dinámicamente la interfaz cuando cambia su contenido.

Cada tarea posee un identificador único, nombre, descripción, prioridad y estado de finalización.

Cuando se agrega una nueva tarea, esta se incorpora al listado principal. De igual manera, cuando se modifica su estado o se elimina, la interfaz refleja automáticamente los cambios realizados.

Actualmente, los datos se almacenan en memoria durante la ejecución de la aplicación. Por lo tanto, los cambios no se conservan cuando se cierra completamente el programa.

## Posible integración con API REST

Como ampliación del proyecto, se contempla la utilización de Ktor Client para establecer comunicación con servicios REST.

Esta integración permitiría consultar, registrar, actualizar y eliminar tareas mediante solicitudes HTTP, así como sincronizar la información con un servidor externo.

Entre las principales operaciones que podrían implementarse se encuentran:

- **GET:** consultar las tareas registradas en el servidor.
- **POST:** registrar nuevas tareas.
- **PUT:** actualizar la información de una tarea.
- **DELETE:** eliminar tareas almacenadas en el servidor.

También se contempla el uso de kotlinx.serialization para convertir los objetos Kotlin al formato JSON y viceversa.

Esta integración permitiría centralizar la información y facilitar el acceso a las tareas desde diferentes dispositivos.

## Organización del proyecto

El proyecto está dividido en módulos que permiten separar las aplicaciones de cada plataforma y mantener el código común en un solo lugar.

- `androidApp/`: contiene la aplicación para Android.
- `desktopApp/`: contiene la aplicación de escritorio basada en JVM.
- `iosApp/`: contiene la aplicación para iOS.
- `shared/`: contiene el código compartido entre las diferentes plataformas.

Dentro de `shared/` se encuentra la siguiente estructura:

- `src/`: contiene el código común que utilizan las plataformas configuradas.
  - `App.kt`: controla el flujo principal de la aplicación y la navegación entre pantallas.
  - `models/Task.kt`: define el modelo de datos de las tareas.
  - `screens/`: contiene las pantallas de inicio, creación y detalle de tareas.
  - `Platform.kt`: declara la funcionalidad común para obtener información de la plataforma.
  - `Greeting.kt`: utiliza la información correspondiente a cada plataforma.
- `src@android/`: contiene las implementaciones específicas de Android.
- `src@ios/`: contiene las implementaciones específicas de iOS.
- `src@jvm/`: contiene las implementaciones específicas de Desktop.
- `test/`: contiene las pruebas del código común.
- `test@android/`, `test@ios/` y `test@jvm/`: contienen pruebas específicas de cada plataforma.
- `composeResources/`: contiene recursos compartidos de Compose.

Esta separación permite reutilizar la lógica y la interfaz cuando es posible, manteniendo implementaciones específicas cuando una funcionalidad depende de las características de cada plataforma.

## Configuración del proyecto

El proyecto utiliza Kotlin Toolchain, que administra las herramientas necesarias para trabajar con los diferentes módulos.

Los scripts `kotlin` para macOS/Linux y `kotlin.bat` para Windows funcionan como lanzadores automáticos. Durante la primera ejecución descargan la versión de Kotlin Toolchain definida para el proyecto, por lo que no es necesario instalar Kotlin manualmente.

Los principales archivos de configuración son:

- `project.yaml`: define la organización general del proyecto y sus módulos.
- `shared/module.yaml`: configura el módulo compartido, sus dependencias y plataformas.
- `androidApp/module.yaml`: configura la aplicación Android.
- `desktopApp/module.yaml`: configura la aplicación Desktop.
- `iosApp/module.yaml`: configura la aplicación iOS.
- `libs.versions.toml`: centraliza las versiones de las dependencias.

## Ejecución del proyecto

Los comandos deben ejecutarse desde la carpeta principal del proyecto.

### Windows

Para compilar el proyecto:

```powershell
.\kotlin.bat build
```

Para ejecutar la aplicación Android:

```powershell
.\kotlin.bat run -m androidApp
```

Para ejecutar la aplicación Desktop:

```powershell
.\kotlin.bat run -m desktopApp
```

Para ejecutar las pruebas:

```powershell
.\kotlin.bat test
```

### macOS y Linux

Para compilar el proyecto:

```bash
./kotlin build
```

Para ejecutar la aplicación Android:

```bash
./kotlin run -m androidApp
```

Para ejecutar la aplicación Desktop:

```bash
./kotlin run -m desktopApp
```

Para ejecutar la aplicación iOS:

```bash
./kotlin run -m iosApp
```

La ejecución de iOS requiere macOS y Xcode configurado.

Para ejecutar las pruebas:

```bash
./kotlin test
```

También es posible ejecutar las pruebas de un módulo específico mediante:

```bash
./kotlin test -m <module>
```

## Pruebas

El proyecto incluye pruebas para comprobar el funcionamiento del código común y de las implementaciones específicas de cada plataforma.

Durante el desarrollo se reportaron seis pruebas aprobadas mediante Kotlin Toolchain.

Se recomienda comprobar también las operaciones principales de gestión de tareas, incluyendo la creación, consulta, actualización de estado y eliminación de registros.

## Consideraciones

Esta versión utiliza almacenamiento en memoria para gestionar las tareas. Por lo tanto, los cambios realizados no se conservan cuando se cierra completamente la aplicación.

Actualmente, la integración con API REST se contempla como una posible ampliación del proyecto y no como una funcionalidad implementada.

El proyecto busca demostrar las posibilidades de Kotlin Multiplatform mediante una aplicación pequeña y funcional, en la que se comparte código entre diferentes plataformas y se mantienen separadas las implementaciones específicas cuando resulta necesario.

## Recursos y documentación

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Kotlin Toolchain](https://kotlin-toolchain.org/latest/)
- [Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)
- [Ktor](https://ktor.io/)