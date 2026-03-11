# Cifras y Letras - Console Edition 🧮🔤

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

Implementación robusta y por consola del clásico concurso de televisión **"Cifras y Letras"**. Este proyecto fue desarrollado íntegramente en Java como Práctica Final para la asignatura de **Programación 1**.

El programa no solo replica las reglas oficiales del juego, sino que está diseñado con una arquitectura modular, gestión eficiente de memoria y algoritmos de búsqueda probabilística para la Inteligencia Artificial (IA).

---

## 🚀 Características Principales

* **Modos de Juego Flexibles**: Permite el enfrentamiento contra una CPU con dificultad ajustable (Modo Entrenamiento) o contra otro jugador humano en modo local (Multijugador).
* **Persistencia de Datos Completa**: Mediante un sistema de ficheros de texto (`partidas.txt`), el juego guarda un historial cronológico de los enfrentamientos y permite consultar estadísticas detalladas por jugador (porcentaje de victorias, puntos totales, promedios).
* **Soporte Multilingüe Dinámico**: Carga dinámica de diccionarios y frecuencias de letras para jugar en **Castellano, Catalán e Inglés**.
* **Gestión de Errores Robusta**: Prevención activa de excepciones y fallos de *buffer* durante la entrada de datos por teclado mediante la implementación de una clase lectora personalizada (`LT.java`).

---

## 🎮 Mecánicas de las Pruebas

El juego alterna entre dos tipos de rondas clásicas, configurables entre 2 y 20 por partida:

### 1. Ronda de Letras
* **Objetivo**: Formar la palabra válida de mayor longitud utilizando un conjunto de caracteres generados pseudoaleatoriamente.
* **Validación Rigurosa**: El algoritmo verifica primero que la palabra ingresada se pueda construir *únicamente* con las letras disponibles (fichas) y, a continuación, valida su existencia leyendo diccionarios masivos en texto plano. 

### 2. Ronda de Cifras
* **Objetivo**: A partir de 6 números iniciales (del set 1-10, 25, 50, 75, 100) y un número objetivo (100-999), el jugador debe usar aritmética básica (`+`, `-`, `*`, `/`) para lograr el valor exacto o la mejor aproximación.
* **Reglas Estrictas**: El sistema valida paso a paso ("step-by-step") que no se generen resultados negativos ni divisiones no enteras en ningún punto del cálculo.

---

## 🧠 Inteligencia Artificial y Algoritmos (CPU)

Uno de los mayores logros técnicos del proyecto es el diseño del rival virtual, configurable desde el menú de opciones:

### Nivel 1: Fácil (Fuerza Bruta Aleatoria)
* **Letras**: Selecciona palabras válidas del diccionario completamente al azar.
* **Cifras**: Realiza operaciones aritméticas escogiendo operandos y operadores de forma aleatoria hasta agotar los números o bloquearse.

### Nivel 2: Difícil (Búsqueda Heurística y Reservoir Sampling)
* **Algoritmo de *Reservoir Sampling***: Para la elección de palabras de la CPU, en lugar de cargar en memoria RAM (estructuras dinámicas) las miles de palabras que coinciden con las letras disponibles, se implementó el algoritmo de *Reservoir Sampling*. Esto permite escanear el fichero del diccionario de forma secuencial y asignar probabilidades dinámicas (`1/contador`) para quedarse siempre con la palabra más óptima y larga de manera ultra-eficiente.
* **Heurística de Cifras**: La CPU evalúa combinaciones de pares de números. Primero busca una operación que alcance la exactitud total matemática (margen de error 0) y, de no encontrarla en su primer pase, realiza un segundo pase buscando una aproximación dentro de un margen de tolerancia específico. 

---

## ⚙️ Estructura del Código y Manejo de Datos

El proyecto respeta los límites de los conocimientos de "Programación 1", demostrando un dominio absoluto de los fundamentos lógicos:

| Componente | Detalles de Implementación |
| :--- | :--- |
| **Estructuras Estáticas** | Uso exclusivo de arrays unidimensionales (`[]`). La inserción y eliminación de elementos (ej. consumir operandos en Cifras) se realiza mediante funciones manuales de redimensionamiento e indexación. |
| **Manejo de Ficheros** | Abstracción en las clases `FicherosLectura` y `FicherosEscritura` utilizando `BufferedReader` y `BufferedWriter` para un I/O de alta eficiencia. |
| **Clase Registro** | Actúa como el Modelo de Datos para empaquetar fechas, nombres, puntuaciones y configuración de la CPU antes de volcar la información al historial. |

---

## 🛠️ Navegación y Uso

1. **Menú Principal**: Accede a Jugar, Registro, Opciones o Salir.
2. **Opciones (Configuración Global)**.
   * Cantidad de letras: 10 a 20.
   * Cantidad de cifras: 6 a 10.
   * Nivel CPU: Fácil o Difícil.
   * Idiomas de Diccionarios.
