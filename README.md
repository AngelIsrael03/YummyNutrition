# 🍎 YummyNutrition

Aplicación Android desarrollada con **Kotlin + Jetpack Compose** para visualizar un resumen de nutrición diaria y navegar entre pantallas.

## ✨ Funcionalidades
- Resumen de calorías del día
- Barras de macronutrientes (Proteína, Carbs, Grasas)
- Navegación con Bottom Navigation
- Pantalla de detalle de receta (por id)

## 🧰 Tecnologías
- Kotlin
- Jetpack Compose (Material 3)
- Navigation Compose
- MVVM (ViewModel)

## 🗂️ Estructura del proyecto
- `MainActivity` inicia la app y carga `AppRoot()`
- `AppNavigation` define rutas y `startDestination = home`
- Screens: `Home`, `Recipes`, `Nutrition`, `RecipeDetail`

## ▶️ Cómo ejecutar
1. Descargar el proyecto (Code → Download ZIP) o clonar el repositorio.
2. Abrir en Android Studio.
3. Esperar Gradle Sync.
4. Ejecutar en emulador o dispositivo físico.

## 🧪 Navegación
- **Home**: resumen nutricional
- **Recipes**: búsqueda/listado de recetas
- **Nutrition**: sección de nutrición
- **RecipeDetail/{id}**: detalle por id


