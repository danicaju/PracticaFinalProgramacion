package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/*
AUTOR: Daniel Casado Juan
GRUPO: 2
 */
public class Main {

    // --- HERRAMIENTAS GLOBALES ---
    private final Scanner scanner = new Scanner(System.in);
    private Registro registroPartida;

    // --- ESTADO DEL JUEGO ---
    private char[] entradaPorTeclado;
    private char[] caracteresAleatorios = new char[10]; // Se redimensiona si cambia la config
    private List<Integer> cifrasAleatorias = new ArrayList<>();

    private int rondaActual = 1;
    private boolean haPasado = false;
    private int puntuajeCifras = 0;

    private int cantidadCifras = 6;
    private int dificultadCPU = 1; // 0 = No CPU, 1 = Aleatorio (Fácil), 2 = Inteligente (Difícil)

    private Jugador jugador1;
    private Jugador jugador2;
    private Set<String> diccionarioEnMemoria = new HashSet<>();

    // --- CONSTANTES DE CONFIGURACIÓN ---
    private static final int MIN_LETRAS = 10;
    private static final int MAX_LETRAS = 20;

    private static final int MIN_CIFRAS = 6;
    private static final int MAX_CIFRAS = 10;

    private static final int MIN_RONDAS = 2;
    private static final int MAX_RONDAS = 20;

    // --- CONSTANTES DEL JUEGO DE CIFRAS ---
    private static final int MIN_OBJETIVO = 100;
    private static final int MAX_OBJETIVO = 999;

    private static final int PUNTOS_EXACTOS = 10;
    private static final int PUNTOS_MARGEN_5 = 7;
    private static final int PUNTOS_MARGEN_10 = 5;

    private static final int MARGEN_ERROR_CPU = 50;
    private static final int TIEMPO_ESPERA_MS = 750;

    // --- FICHEROS ---
    private String ficheroLetras = "letras_es.txt";
    private String diccionario = "dic_es.txt";
    private final String ficheroCifras = "cifras.txt";
    private final String ficheroPartidas = "partidas.txt";

    // =========================================================================
    // WRAPPERS DE SCANNER (MÉTODOS SEGUROS DE ENTRADA)
    // =========================================================================
    private String pedirTexto() {
        return scanner.nextLine().trim();
    }

    private char pedirCaracter() {
        while (true) {
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.isEmpty()) {
                System.err.println("ERROR. ¡No has escrito nada! Introduce una opción:");
            } else {
                return entrada.charAt(0);
            }
        }
    }

    private int pedirEntero() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.err.println("ERROR. ¡No has escrito nada! Introduce un número:");
                continue;
            }
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.err.println("ERROR. ¡Entrada no válida! Por favor, introduce solo números:");
            }
        }
    }

    // =========================================================================
    // INICIALIZACIÓN Y MENÚS
    // =========================================================================
    public void inicializarPartida() {
        cargarDiccionarioEnMemoria();
        registroPartida = new Registro();
        registroPartida.setNivelDificultad(dificultadCPU);

        rondaActual = 1;
        puntuajeCifras = 0;
        haPasado = false;
    }

    public void pantallaPrincipal() {
        System.out.println("""
                                                                         
    .-------------------------------------------------------------------------------.
    |    ____ _  __                    __   __   _          _                       |
    |   / ___(_)/ _| _ __ __ _ ___     \\ \\ / /  | |    ___ | |_ _ __ __ _ ___       |
    |  | |   | | |_ | '__/ _` / __|     \\ V /   | |   / _ \\| __| '__/ _` / __|      |
    |  | |___| |  _|| | | (_| \\__ \\      | |    | |__|  __/| |_| | | (_| \\__ \\      |
    |   \\____|_|_|  |_|  \\__,_|___/      |_|    |_____\\___| \\__|_|  \\__,_|___/      |
    |                                                                               |
    +-------------------------------------------------------------------------------+
    |                          >>>   G R U P O   2   <<<                            |
    +-------------------------------------------------------------------------------+
    |                                                                               |
    |   DESARROLLADO POR:                                                           |
    |    > Maria Teresa Sbert Gomila                                                |
    |    > Daniel Casado Juan                                                       |
    |                                                                               |
    '-------------------------------------------------------------------------------'   
    """);

        System.out.print("¡Bienvenido jugador! Pulsa Enter para continuar: ");
        scanner.nextLine(); // Simplemente esperamos un intro
    }

    public void menuPrincipal() throws Exception {
        boolean salirDelPrograma = false;

        while (!salirDelPrograma) {
            System.out.print("""
                          ************************************
                          MENU PRINCIPAL
                          ************************************
                             1. Jugar
                             2. Registro
                             3. Opciones
                             s. Salir
                          ************************************
                          
                          Opcion(1|2|3|s): """);

            char opcion = pedirCaracter();

            switch (opcion) {
                case '1' ->
                    opcionJugar();
                case '2' ->
                    opcionRegistro();
                case '3' ->
                    opcionOpciones();
                case 's' -> {
                    System.out.println("Saliendo... ¡Gracias por Jugar a Cifras y Letras!");
                    salirDelPrograma = true;
                }
                default ->
                    System.err.println("\nERROR. ¡Introduce una opción válida!");
            }
        }
    }

    public void opcionJugar() throws Exception {
        boolean volverAlMenu = false;
        while (!volverAlMenu) {
            System.out.print("""
                                 
                                 ************************************
                                 JUGAR
                                 ************************************
                                   1. Jugar contra el ordenador
                                   2. Jugar contra otro jugador
                                   s. Volver al menu principal
                                 ************************************
                                 
                                 Opcion (1|2|s): """);

            char opcion = pedirCaracter();

            switch (opcion) {
                case '1' -> {
                    inicializarPartida();
                    casoJugarContraCPU();
                    buclePrincipalJuego();
                    volverAlMenu = true;
                }
                case '2' -> {
                    inicializarPartida();
                    casoJugador1ContraJugador2();
                    buclePrincipalJuego();
                    volverAlMenu = true;
                }
                case 's' ->
                    volverAlMenu = true;
                default ->
                    System.err.println("\nERROR. ¡Introduce una opción válida!");
            }
        }
    }

    public void opcionRegistro() {
        boolean volverAlMenu = false;
        while (!volverAlMenu) {
            System.out.print("""
                             
                          ************************************
                          REGISTRO
                          ************************************
                             1. Mostrar resultados de las partidas
                             2. Mostrar estadisticas de un jugador
                             s. Volver al menu principal
                          ************************************
                          
                          Opcion (1|2|s): """);

            char opcion = pedirCaracter();

            switch (opcion) {
                case '1' ->
                    mostrarResultadosPartidas();
                case '2' ->
                    mostrarEstadisticasJugador();
                case 's' ->
                    volverAlMenu = true;
                default ->
                    System.err.println("\nERROR. ¡Introduce una opción válida!");
            }
        }
    }

    public void opcionOpciones() {
        boolean volverAlMenu = false;
        while (!volverAlMenu) {
            System.out.print("""
                             
                          ************************************
                          OPCIONES
                          ************************************
                             1. Configurar cantidad de letras
                             2. Configurar cantidad de cifras
                             3. Configurar dificultad CPU                         
                             4. Configurar idioma
                             s. Volver al menu principal
                          ************************************
                          
                          Opcion (1|2|3|4|s): """);

            char opcion = pedirCaracter();

            switch (opcion) {
                case '1' ->
                    configurarCantidadLetras();
                case '2' ->
                    configurarCantidadCifras();
                case '3' ->
                    configurarNivelCPU();
                case '4' ->
                    configurarIdioma();
                case 's' ->
                    volverAlMenu = true;
                default ->
                    System.err.println("\nERROR. ¡Introduce una opción válida!");
            }
        }
    }

    // =========================================================================
    // CONFIGURACIÓN
    // =========================================================================
    public void configurarCantidadLetras() {
        boolean valido = false;
        System.out.println("\n[INFO] Longitud actual establecida en " + caracteresAleatorios.length + ".");

        while (!valido) {
            System.out.print("Introduce la cantidad de letras [" + MIN_LETRAS + "-" + MAX_LETRAS + "]: ");
            int opcion = pedirEntero();

            if (opcion > MAX_LETRAS) {
                System.err.println("ERROR. ¡Máximo " + MAX_LETRAS + " letras!");
            } else if (opcion < MIN_LETRAS) {
                System.err.println("ERROR. ¡Mínimo " + MIN_LETRAS + " letras!");
            } else {
                System.out.println("\n[INFO] ¡Has cambiado la cantidad de letras a " + opcion + "!");
                caracteresAleatorios = new char[opcion];
                valido = true;
            }
        }
    }

    public void configurarCantidadCifras() {
        boolean valido = false;
        System.out.println("\n[INFO] Cantidad de cifras actual establecida en " + cantidadCifras + ".");

        while (!valido) {
            System.out.print("Introduce la cantidad de cifras [" + MIN_CIFRAS + "-" + MAX_CIFRAS + "]: ");
            int opcion = pedirEntero();

            if (opcion > MAX_CIFRAS) {
                System.err.println("ERROR. ¡Máximo " + MAX_CIFRAS + " cifras!");
            } else if (opcion < MIN_CIFRAS) {
                System.err.println("ERROR. ¡Mínimo " + MIN_CIFRAS + " cifras!");
            } else {
                System.out.println("\n[INFO] ¡Has cambiado la cantidad de cifras a " + opcion + "!");
                cantidadCifras = opcion;
                valido = true;
            }
        }
    }

    public void configurarNivelCPU() {
        boolean valido = false;
        String nivelStr = (dificultadCPU == 1) ? "Fácil" : "Difícil";
        System.out.println("\n[INFO] Nivel de dificultad actual: " + nivelStr);

        while (!valido) {
            System.out.print("Introduce el nivel de dificultad [1 = Fácil, 2 = Difícil]: ");
            int opcion = pedirEntero();

            if (opcion != 1 && opcion != 2) {
                System.err.println("ERROR. ¡Solo 1 (Fácil) o 2 (Difícil)!");
            } else {
                System.out.println("\n[INFO] ¡Has cambiado la dificultad a " + (opcion == 1 ? "Fácil" : "Difícil") + "!");
                dificultadCPU = opcion;
                valido = true;
            }
        }
    }

    public void configurarIdioma() {
        boolean valido = false;

        while (!valido) {
            System.out.print("""
                             
                          ************************************
                          IDIOMAS DISPONIBLES
                          ************************************
                             1. Castellano
                             2. Catalan
                             3. Ingles
                          ************************************
                          
                          Opcion (1|2|3): """);
            int opcion = pedirEntero();

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n[INFO] ¡Has cambiado el idioma a castellano!");
                    diccionario = "dic_es.txt";
                    ficheroLetras = "letras_es.txt";
                    valido = true;
                }
                case 2 -> {
                    System.out.println("\n[INFO] ¡Has cambiado el idioma a catalan!");
                    diccionario = "dic_ca.txt";
                    ficheroLetras = "letras_ca.txt";
                    valido = true;
                }
                case 3 -> {
                    System.out.println("\n[INFO] ¡Has cambiado el idioma a ingles!");
                    diccionario = "dic_en.txt";
                    ficheroLetras = "letras_en.txt";
                    valido = true;
                }
                default ->
                    System.err.println("\nERROR. ¡Introduce una opción válida!");
            }
        }
    }

    // =========================================================================
    // PREPARACIÓN DE PARTIDA
    // =========================================================================
    public void cargarDiccionarioEnMemoria() {
        diccionarioEnMemoria.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(diccionario))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                diccionarioEnMemoria.add(linea.trim().toLowerCase());
            }
            System.out.println("[INFO] Diccionario cargado con " + diccionarioEnMemoria.size() + " palabras.");
        } catch (IOException e) {
            System.err.println("ERROR crítico: No se pudo cargar el diccionario " + diccionario + " - " + e.getMessage());
        }
    }

    private String pedirNombreValido(int numJugador) {
        String nombreFinal = "";
        boolean valido = false;

        while (!valido) {
            System.out.print("Introduce el nombre del jugador " + numJugador + ": ");
            String nombreLeido = pedirTexto();

            if (nombreLeido.isEmpty()) {
                System.err.println("ERROR. ¡No has escrito nada!");
            } else {
                nombreFinal = nombreLeido.replaceAll("\\s+", " "); // Unifica múltiples espacios en uno
                valido = true;
            }
        }
        return nombreFinal;
    }

    public void pedirNumeroRondasValido() {
        boolean valido = false;
        int numeroRondas = 0;

        while (!valido) {
            System.out.print("Introduce cuantas rondas quieres jugar [" + MIN_RONDAS + "-" + MAX_RONDAS + ", número par]: ");
            numeroRondas = pedirEntero();

            if (numeroRondas > MAX_RONDAS) {
                System.err.println("ERROR. ¡Máximo " + MAX_RONDAS + " rondas!");
            } else if (numeroRondas < MIN_RONDAS) {
                System.err.println("ERROR. ¡Mínimo " + MIN_RONDAS + " rondas!");
            } else if (numeroRondas % 2 != 0) {
                System.err.println("ERROR. ¡Introduce un número par de rondas!");
            } else {
                valido = true;
            }
        }
        registroPartida.setNumeroRondas(numeroRondas);
    }

    public void casoJugarContraCPU() {
        System.out.println("""
                                    
                                    ************************************
                                    JUGAR CONTRA EL ORDENADOR
                                    ************************************""");

        String nombreJ1 = pedirNombreValido(1);
        jugador1 = new Jugador(nombreJ1, false);
        jugador2 = new Jugador("CPU", true);

        registroPartida.setNombreJugador1(nombreJ1);
        System.out.println("Nombre del jugador 2: CPU.");
        registroPartida.setNombreJugador2("CPU");

        pedirNumeroRondasValido();
        registroPartida.setTipoPartida("vs CPU");
    }

    public void casoJugador1ContraJugador2() {
        System.out.println("""
    ************************************
    JUGAR CONTRA OTRO JUGADOR
    ************************************""");

        String nombreJ1 = pedirNombreValido(1);
        jugador1 = new Jugador(nombreJ1, false);
        registroPartida.setNombreJugador1(nombreJ1);

        String nombreJ2 = pedirNombreValido(2);
        jugador2 = new Jugador(nombreJ2, false);
        registroPartida.setNombreJugador2(nombreJ2);

        System.out.println("Nombre del jugador 1: " + nombreJ1);
        System.out.println("Nombre del jugador 2: " + nombreJ2);

        pedirNumeroRondasValido();
        registroPartida.setTipoPartida("vs humano");
        registroPartida.setNivelDificultad(0);
    }

    // =========================================================================
    // FLUJO DEL JUEGO (RONDAS)
    // =========================================================================
    private void buclePrincipalJuego() throws Exception {
        while (rondaActual <= registroPartida.getNumeroRondas()) {
            // Turnos de Letras
            jugarTurnoLetras(jugador1);
            mostrarPuntuacionesJugador1Jugador2();

            jugarTurnoLetras(jugador2);
            mostrarPuntuacionesJugador1Jugador2();

            // Turnos de Cifras
            jugarTurnoCifras(jugador1);
            mostrarPuntuacionesJugador1Jugador2();

            jugarTurnoCifras(jugador2);
            mostrarPuntuacionesJugador1Jugador2();

            rondaActual++;
        }
        escribirResultadosPartida();
        finalPartida();
    }

    public void jugarTurnoLetras(Jugador jugadorActual) throws Exception {
        System.out.println("\n--- RONDA " + rondaActual + " DE LETRAS ---");
        System.out.println("Turno de: " + jugadorActual.getNombre());

        mostrarLetrasDisponibles();

        if (jugadorActual.isCpu()) {
            puedeFormarseCPU();
            if (entradaPorTeclado != null && entradaPorTeclado.length > 0) {
                asignarPuntosLetras(jugadorActual);
            }
        } else {
            boolean palabraCorrecta = false;
            while (!palabraCorrecta && !haPasado) {
                puedeFormarseJugador();
                if (!haPasado) {
                    palabraCorrecta = existeEnDiccionarioJugador();
                }
            }

            if (!haPasado) {
                asignarPuntosLetras(jugadorActual);
            } else {
                System.out.println("¡Has pasado el turno!");
                haPasado = false; // Reiniciamos para el siguiente
            }
        }
    }

    public void jugarTurnoCifras(Jugador jugadorActual) throws Exception {
        System.out.println("\n--- RONDA " + rondaActual + " DE CIFRAS ---");
        System.out.println("Turno de: " + jugadorActual.getNombre());

        generacionCifrasAleatorias();

        if (jugadorActual.isCpu()) {
            operacionesCifrasCPU();
        } else {
            operacionesCifrasJugador();
        }

        asignarPuntosCifras(jugadorActual);
    }

    public void mostrarPuntuacionesJugador1Jugador2() {
        System.out.println("\nPuntuaciones:\n - " + registroPartida.getNombreJugador1()
                + ": " + registroPartida.getPuntuacionJugador1() + " puntos.\n"
                + " - " + registroPartida.getNombreJugador2() + ": "
                + registroPartida.getPuntuacionJugador2() + " puntos.");
    }

    public void finalPartida() {
        System.out.println("\n¡Se acabó la partida! ¡Muy bien jugado ambos!");
    }

    // =========================================================================
    // LÓGICA DE LETRAS
    // =========================================================================
    public void mostrarLetrasDisponibles() {
        try (FicherosLectura lecturaFichero = new FicherosLectura(ficheroLetras)) {
            String lecturaLetrasDisponibles = lecturaFichero.leerFichero();

            if (lecturaLetrasDisponibles != null) {
                Random random = new Random();
                char[] arrayFicheroLetras = lecturaLetrasDisponibles.toCharArray();

                System.out.print("Letras disponibles: ");
                for (int i = 0; i < caracteresAleatorios.length; i++) {
                    int indiceAleatorio = random.nextInt(arrayFicheroLetras.length);
                    caracteresAleatorios[i] = arrayFicheroLetras[indiceAleatorio];
                    System.out.print(caracteresAleatorios[i] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("\nERROR. Fichero " + ficheroLetras + " no encontrado o no se pudo leer.");
        }
    }

    public void puedeFormarseJugador() {
        boolean puedeFormarse = false;

        while (!puedeFormarse && !haPasado) {
            System.out.print("\nIntroduce tu palabra (o escribe '.' para pasar): ");
            String input = pedirTexto();

            while (input.isEmpty()) {
                System.err.println("ERROR. ¡No has escrito nada!\n");
                System.out.print("Introduce tu palabra (o escribe '.' para pasar): ");
                input = pedirTexto();
            }

            entradaPorTeclado = input.toCharArray();

            if (entradaPorTeclado[0] == '.') {
                haPasado = true;
            }

            if (!haPasado) {
                System.out.println("Validando palabra...");

                char[] copiaLetras = new char[caracteresAleatorios.length];
                System.arraycopy(caracteresAleatorios, 0, copiaLetras, 0, caracteresAleatorios.length);

                puedeFormarse = true;
                for (int i = 0; i < entradaPorTeclado.length; i++) {
                    char letra = entradaPorTeclado[i];
                    boolean encontrada = false;
                    for (int j = 0; j < caracteresAleatorios.length && !encontrada; j++) {
                        if (copiaLetras[j] == letra) {
                            copiaLetras[j] = '*';
                            encontrada = true;
                        }
                    }
                    if (!encontrada) {
                        puedeFormarse = false;
                    }
                }

                if (!puedeFormarse) {
                    System.err.println("¡La palabra NO puede formarse con las letras disponibles! Inténtalo de nuevo.");
                }
            }
        }
    }

    public void puedeFormarseCPU() throws InterruptedException {
        Random random = new Random();
        String palabraCPU = null;
        int contador = 0;

        for (String palabraDic : diccionarioEnMemoria) {
            char[] auxLetras = new char[caracteresAleatorios.length];
            System.arraycopy(caracteresAleatorios, 0, auxLetras, 0, caracteresAleatorios.length);

            boolean posiblePalabra = true;
            char[] palabraDicArray = palabraDic.toCharArray();

            for (int i = 0; i < palabraDicArray.length && posiblePalabra; i++) {
                char letra = palabraDicArray[i];
                boolean encontrada = false;

                for (int j = 0; j < auxLetras.length && !encontrada; j++) {
                    if (auxLetras[j] == letra) {
                        auxLetras[j] = '*';
                        encontrada = true;
                    }
                }
                if (!encontrada) {
                    posiblePalabra = false;
                }
            }

            if (posiblePalabra) {
                if (registroPartida.getNivelDificultad() == 1) {
                    contador++;
                    if (random.nextInt(contador) == 0) {
                        palabraCPU = palabraDic;
                    }
                } else {
                    if (palabraCPU == null || palabraDic.length() > palabraCPU.length()) {
                        palabraCPU = palabraDic;
                    }
                }
            }
        }

        if (palabraCPU != null) {
            char[] palabraArray = palabraCPU.toCharArray();
            esperarLetrasCPU(palabraArray);
            entradaPorTeclado = palabraArray;
        } else {
            System.out.println("CPU no pudo formar ninguna palabra.");
            entradaPorTeclado = new char[0];
        }
    }

    public boolean existeEnDiccionarioJugador() {
        String palabraJugador = new String(entradaPorTeclado).trim().toLowerCase();

        if (diccionarioEnMemoria.contains(palabraJugador)) {
            System.out.println(" - puede crearse con las letras disponibles");
            System.out.println(" - existe en el diccionario");
            return true;
        } else if (!haPasado) {
            System.err.println("La palabra NO existe en el diccionario. ¡Inténtalo de nuevo!");
            return false;
        }
        return true;
    }

    public void asignarPuntosLetras(Jugador jugadorActivo) {
        int puntosGanados = entradaPorTeclado.length;
        jugadorActivo.sumarPuntos(puntosGanados);

        if (jugadorActivo == jugador1) {
            registroPartida.setPuntuacionJugador1(jugadorActivo.getPuntuacion());
        } else {
            registroPartida.setPuntuacionJugador2(jugadorActivo.getPuntuacion());
        }
        System.out.println("¡Felicidades " + jugadorActivo.getNombre() + "! Has ganado " + puntosGanados + " puntos.");
    }

    // =========================================================================
    // LÓGICA DE CIFRAS
    // =========================================================================
    public void generacionCifrasAleatorias() {
        try (FicherosLectura ficheroDeCifras = new FicherosLectura(ficheroCifras)) {
            String lectura = ficheroDeCifras.leerFichero();
            if (lectura != null) {
                String[] numerosString = lectura.trim().split("\\s+");

                List<Integer> disponibles = new ArrayList<>();
                for (String s : numerosString) {
                    disponibles.add(Integer.parseInt(s));
                }

                cifrasAleatorias.clear();
                Random random = new Random();

                for (int k = 0; k < cantidadCifras; k++) {
                    int indiceAleatorio = random.nextInt(disponibles.size());
                    cifrasAleatorias.add(disponibles.remove(indiceAleatorio));
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR. Fichero " + ficheroCifras + " no encontrado o no se pudo leer.");
        }
    }

    public void operacionesCifrasJugador() {
        Random objRandom = new Random();
        int objetivo = objRandom.nextInt(MIN_OBJETIVO, MAX_OBJETIVO);
        int numOperacion = 1;
        Integer resultadoFinal = null;
        char tipoOperacion;
        boolean resultadoValidoElegido = false;

        List<Integer> historialNumeros = new ArrayList<>(cifrasAleatorias);

        while (!resultadoValidoElegido) {
            System.out.println("Cifras disponibles: " + cifrasAleatorias.toString().replace("[", "").replace("]", ""));
            System.out.println("Objetivo: " + objetivo);

            System.out.print("Operación " + numOperacion + " (+|-|*|/|=): ");
            tipoOperacion = pedirCaracter();

            while (tipoOperacion != '+' && tipoOperacion != '-' && tipoOperacion != '*' && tipoOperacion != '/' && tipoOperacion != '=') {
                System.err.println("ERROR. ¡Introduce una operación válida!");
                System.out.print("Operación " + numOperacion + " (+|-|*|/|=): ");
                tipoOperacion = pedirCaracter();
            }

            if (cifrasAleatorias.size() == 1 && tipoOperacion != '=') {
                System.err.println("ERROR. ¡Debes introducir un resultado (=), solo tienes una cifra!");
                continue;
            }

            if (tipoOperacion == '=') {
                System.out.print("Introduce el resultado final (" + historialNumeros.toString().replace("[", "").replace("]", "") + "): ");
                resultadoFinal = pedirEntero();

                while (!historialNumeros.contains(resultadoFinal)) {
                    System.err.println("ERROR. El resultado no está entre las cifras del historial.");
                    System.out.print("Introduce el resultado final (" + historialNumeros.toString().replace("[", "").replace("]", "") + "): ");
                    resultadoFinal = pedirEntero();
                }

                int diferencia = Math.abs(objetivo - resultadoFinal);
                if (diferencia == 0) {
                    puntuajeCifras = PUNTOS_EXACTOS;
                } else if (diferencia <= 5) {
                    puntuajeCifras = PUNTOS_MARGEN_5;
                } else if (diferencia <= 10) {
                    puntuajeCifras = PUNTOS_MARGEN_10;
                } else {
                    puntuajeCifras = 0;
                }

                System.out.println("Diferencia de " + diferencia + ": +" + puntuajeCifras + " puntos");
                resultadoValidoElegido = true;
                break;
            }

            List<Integer> backupCifras = new ArrayList<>(cifrasAleatorias);

            System.out.print("Operando 1 (" + cifrasAleatorias.toString().replace("[", "").replace("]", "") + "): ");
            int operando1 = pedirEntero();
            while (!cifrasAleatorias.contains(operando1)) {
                System.err.println("ERROR. ¡Introduce un operando válido de la lista!");
                System.out.print("Operando 1: ");
                operando1 = pedirEntero();
            }
            cifrasAleatorias.remove(Integer.valueOf(operando1));

            System.out.print("Operando 2 (" + cifrasAleatorias.toString().replace("[", "").replace("]", "") + "): ");
            int operando2 = pedirEntero();
            while (!cifrasAleatorias.contains(operando2)) {
                System.err.println("ERROR. ¡Introduce un operando válido de la lista!");
                System.out.print("Operando 2: ");
                operando2 = pedirEntero();
            }
            cifrasAleatorias.remove(Integer.valueOf(operando2));

            boolean operacionCorrecta = false;
            int resultadoTemporal = 0;

            switch (tipoOperacion) {
                case '+' -> {
                    resultadoTemporal = operando1 + operando2;
                    operacionCorrecta = true;
                }
                case '*' -> {
                    resultadoTemporal = operando1 * operando2;
                    operacionCorrecta = true;
                }
                case '-' -> {
                    if (operando1 - operando2 < 0) {
                        System.err.println("ERROR. ¡La resta daría negativo!");
                    } else {
                        resultadoTemporal = operando1 - operando2;
                        operacionCorrecta = true;
                    }
                }
                case '/' -> {
                    if (operando2 == 0 || operando1 % operando2 != 0) {
                        System.err.println("ERROR. ¡La división no es exacta o es por 0!");
                    } else {
                        resultadoTemporal = operando1 / operando2;
                        operacionCorrecta = true;
                    }
                }
            }

            if (operacionCorrecta) {
                System.out.println(operando1 + " " + tipoOperacion + " " + operando2 + " = " + resultadoTemporal + "\n");
                cifrasAleatorias.add(resultadoTemporal);
                historialNumeros.add(resultadoTemporal);
                numOperacion++;
            } else {
                cifrasAleatorias = new ArrayList<>(backupCifras);
            }
        }
    }

    public void operacionesCifrasCPU() throws InterruptedException {
        Random random = new Random();
        char[] arrayOperaciones = {'+', '-', '*', '/'};
        int numOperacion = 1;
        int objetivo = random.nextInt(MIN_OBJETIVO, MAX_OBJETIVO);
        boolean objetivoEncontrado = false;

        List<Integer> historialNumeros = new ArrayList<>(cifrasAleatorias);

        while (cifrasAleatorias.size() > 1 && !objetivoEncontrado) {
            System.out.println("Cifras disponibles: " + cifrasAleatorias.toString().replace("[", "").replace("]", ""));
            System.out.println("Objetivo: " + objetivo);

            boolean movimientoDecidido = false;
            char operacion = '+';
            Integer op1 = null, op2 = null;

            if (registroPartida.getNivelDificultad() == 2) {
                for (int intento = 0; intento < 2 && !movimientoDecidido; intento++) {
                    int margenError = (intento == 1) ? MARGEN_ERROR_CPU : 0;

                    for (int i = 0; i < cifrasAleatorias.size() && !movimientoDecidido; i++) {
                        for (int j = 0; j < cifrasAleatorias.size() && !movimientoDecidido; j++) {
                            if (i != j) {
                                int tempOp1 = cifrasAleatorias.get(i);
                                int tempOp2 = cifrasAleatorias.get(j);

                                if (Math.abs((tempOp1 + tempOp2) - objetivo) <= margenError) {
                                    operacion = '+';
                                    op1 = tempOp1;
                                    op2 = tempOp2;
                                    movimientoDecidido = true;
                                } else if (tempOp1 - tempOp2 >= 0 && Math.abs((tempOp1 - tempOp2) - objetivo) <= margenError) {
                                    operacion = '-';
                                    op1 = tempOp1;
                                    op2 = tempOp2;
                                    movimientoDecidido = true;
                                } else if (Math.abs((tempOp1 * tempOp2) - objetivo) <= margenError) {
                                    operacion = '*';
                                    op1 = tempOp1;
                                    op2 = tempOp2;
                                    movimientoDecidido = true;
                                } else if (tempOp2 != 0 && tempOp1 % tempOp2 == 0 && Math.abs((tempOp1 / tempOp2) - objetivo) <= margenError) {
                                    operacion = '/';
                                    op1 = tempOp1;
                                    op2 = tempOp2;
                                    movimientoDecidido = true;
                                }
                            }
                        }
                    }
                }
            }

            if (!movimientoDecidido) {
                operacion = arrayOperaciones[random.nextInt(arrayOperaciones.length)];
                int idx1 = random.nextInt(cifrasAleatorias.size());
                int idx2 = random.nextInt(cifrasAleatorias.size());
                while (idx1 == idx2) {
                    idx2 = random.nextInt(cifrasAleatorias.size());
                }
                op1 = cifrasAleatorias.get(idx1);
                op2 = cifrasAleatorias.get(idx2);
            }

            cifrasAleatorias.remove(op1);
            cifrasAleatorias.remove(op2);

            boolean operacionCorrecta = false;
            int resultado = 0;

            switch (operacion) {
                case '+' -> {
                    resultado = op1 + op2;
                    operacionCorrecta = true;
                }
                case '*' -> {
                    resultado = op1 * op2;
                    operacionCorrecta = true;
                }
                case '-' -> {
                    if (op1 - op2 >= 0) {
                        resultado = op1 - op2;
                        operacionCorrecta = true;
                    }
                }
                case '/' -> {
                    if (op2 != 0 && op1 % op2 == 0) {
                        resultado = op1 / op2;
                        operacionCorrecta = true;
                    }
                }
            }

            if (operacionCorrecta) {
                esperarCifrasCPU();
                System.out.println("Operación " + numOperacion + ": " + op1 + " " + operacion + " " + op2 + " = " + resultado + "\n");
                cifrasAleatorias.add(resultado);
                historialNumeros.add(resultado);
                numOperacion++;

                if (resultado == objetivo) {
                    objetivoEncontrado = true;
                }
            } else {
                cifrasAleatorias.add(op1);
                cifrasAleatorias.add(op2);
            }
        }

        int mejorDiferencia = 9999;
        int numeroMasCercano = 0;

        for (int num : historialNumeros) {
            int dif = Math.abs(objetivo - num);
            if (dif < mejorDiferencia) {
                mejorDiferencia = dif;
                numeroMasCercano = num;
            }
        }

        System.out.println("Resultado final de CPU: " + numeroMasCercano);

        if (mejorDiferencia == 0) {
            puntuajeCifras = PUNTOS_EXACTOS;
        } else if (mejorDiferencia <= 5) {
            puntuajeCifras = PUNTOS_MARGEN_5;
        } else if (mejorDiferencia <= 10) {
            puntuajeCifras = PUNTOS_MARGEN_10;
        } else {
            puntuajeCifras = 0;
        }

        System.out.println("Diferencia de " + mejorDiferencia + ": +" + puntuajeCifras + " puntos");
    }

    public void asignarPuntosCifras(Jugador jugadorActivo) {
        jugadorActivo.sumarPuntos(puntuajeCifras);

        if (jugadorActivo == jugador1) {
            registroPartida.setPuntuacionJugador1(jugadorActivo.getPuntuacion());
        } else {
            registroPartida.setPuntuacionJugador2(jugadorActivo.getPuntuacion());
        }
    }

    // =========================================================================
    // UTILIDADES VISUALES
    // =========================================================================
    private void esperarCifrasCPU() throws InterruptedException {
        System.out.print("La CPU esta calculando");
        Thread.sleep(TIEMPO_ESPERA_MS);
        System.out.print(".");
        Thread.sleep(TIEMPO_ESPERA_MS);
        System.out.print(".");
        Thread.sleep(TIEMPO_ESPERA_MS);
        System.out.println(".");
    }

    private void esperarLetrasCPU(char[] palabraCPU) throws InterruptedException {
        System.out.print("\nLa CPU elige: ");
        for (int i = 0; i < palabraCPU.length; i++) {
            System.out.print(palabraCPU[i]);
            Thread.sleep(TIEMPO_ESPERA_MS);
        }
        System.out.println();
    }

    // =========================================================================
    // REGISTRO DE PARTIDAS E HISTÓRICO
    // =========================================================================
    public void escribirResultadosPartida() {
        try (FicherosEscritura ficherosEscritura = new FicherosEscritura(ficheroPartidas)) {
            ficherosEscritura.escribirFichero(registroPartida.toString());
            ficherosEscritura.escribirSaltoLinea();
        } catch (IOException e) {
            System.err.println("ERROR guardando la partida: " + e.getMessage());
        }
        registroPartida.determinarGanador();
    }

    public void mostrarResultadosPartidas() {
        System.out.println("\n------------------ REGISTRO DE LAS PARTIDAS ------------------\n");

        try (FicherosLectura ficheroLectura = new FicherosLectura(ficheroPartidas)) {
            String leerFicheroRegistro;
            int numeroPartidas = 1;

            while ((leerFicheroRegistro = ficheroLectura.leerFichero()) != null) {
                String[] arrayCampos = leerFicheroRegistro.split("#");

                if (arrayCampos.length >= 8) {
                    int puntuacionJugador1 = Integer.parseInt(arrayCampos[6]);
                    int puntuacionJugador2 = Integer.parseInt(arrayCampos[7]);

                    String ganador;
                    if (puntuacionJugador1 > puntuacionJugador2) {
                        ganador = arrayCampos[2];
                    } else if (puntuacionJugador1 < puntuacionJugador2) {
                        ganador = arrayCampos[3];
                    } else {
                        ganador = "Ninguno (Empate)";
                    }

                    System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                            + "Modo \"" + arrayCampos[1] + "\", " + arrayCampos[5] + " rondas,\n"
                            + "ganador: \"" + ganador + "\".\n"
                            + " - Jugador 1 \"" + arrayCampos[2] + "\": " + puntuacionJugador1 + " puntos.\n"
                            + " - Jugador 2 \"" + arrayCampos[3] + "\": " + puntuacionJugador2 + " puntos.\n");
                    numeroPartidas++;
                }
            }
            System.out.println("--------------------------------------------------------------");
        } catch (IOException e) {
            System.err.println("\nERROR. No se pudo acceder al fichero de partidas: " + e.getMessage());
        }
    }

    public void mostrarEstadisticasJugador() {
        String nombreBuscado = "";
        boolean valido = false;

        while (!valido) {
            System.out.print("Introduce el nombre del jugador: ");
            nombreBuscado = pedirTexto();

            if (nombreBuscado.isEmpty()) {
                System.err.println("ERROR. ¡No has escrito nada!");
            } else {
                valido = true;
            }
        }

        int numeroPartidas = 0;
        int numeroPartidasGanadas = 0;
        int puntuacionTotal = 0;
        boolean existeElJugador = false;

        try (FicherosLectura ficheroLectura = new FicherosLectura(ficheroPartidas)) {
            String leerFicheroRegistro;

            while ((leerFicheroRegistro = ficheroLectura.leerFichero()) != null) {
                String[] arrayCampos = leerFicheroRegistro.split("#");

                if (arrayCampos.length >= 8) {
                    boolean esJugador1 = arrayCampos[2].equalsIgnoreCase(nombreBuscado);
                    boolean esJugador2 = arrayCampos[3].equalsIgnoreCase(nombreBuscado);

                    if (esJugador1 || esJugador2) {
                        if (!existeElJugador) {
                            System.out.println("\n------------------ PARTIDAS JUGADAS POR ESTE JUGADOR ------------------\n");
                            existeElJugador = true;
                        }

                        int puntuacionJugador1 = Integer.parseInt(arrayCampos[6]);
                        int puntuacionJugador2 = Integer.parseInt(arrayCampos[7]);

                        if (esJugador1) {
                            numeroPartidas++;
                            puntuacionTotal += puntuacionJugador1;
                            if (puntuacionJugador1 > puntuacionJugador2) {
                                numeroPartidasGanadas++;
                            }
                        } else {
                            numeroPartidas++;
                            puntuacionTotal += puntuacionJugador2;
                            if (puntuacionJugador2 > puntuacionJugador1) {
                                numeroPartidasGanadas++;
                            }
                        }

                        String ganador;
                        if (puntuacionJugador1 > puntuacionJugador2) {
                            ganador = arrayCampos[2];
                        } else if (puntuacionJugador1 < puntuacionJugador2) {
                            ganador = arrayCampos[3];
                        } else {
                            ganador = "Ninguno (Empate)";
                        }

                        System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                                + "Modo \"" + arrayCampos[1] + "\", " + arrayCampos[5] + " rondas,\n"
                                + "ganador: \"" + ganador + "\".\n"
                                + " - Jugador 1 \"" + arrayCampos[2] + "\": " + puntuacionJugador1 + " puntos.\n"
                                + " - Jugador 2 \"" + arrayCampos[3] + "\": " + puntuacionJugador2 + " puntos.\n");
                    }
                }
            }

            if (existeElJugador) {
                double porcentajePartidasGanadas = (double) numeroPartidasGanadas / numeroPartidas * 100.0;
                double promedioPuntuacion = (double) puntuacionTotal / numeroPartidas;
                System.out.println("\n------------------ ESTADÍSTICAS DEL JUGADOR ------------------\n"
                        + "Total de partidas jugadas: " + numeroPartidas + ".\n"
                        + "Total de partidas ganadas: " + numeroPartidasGanadas + ".\n"
                        + "Porcentaje de partidas ganadas: " + String.format("%.2f", porcentajePartidasGanadas) + "%.\n"
                        + "Promedio de puntuación por partida: " + String.format("%.2f", promedioPuntuacion) + ".\n"
                        + "--------------------------------------------------------------");
            } else {
                System.err.println("\nERROR. ¡El jugador no existe en el registro!");
            }
        } catch (IOException e) {
            System.err.println("\nERROR. No se pudo acceder al fichero de partidas: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO MAIN
    // =========================================================================
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.pantallaPrincipal();
        m.menuPrincipal();
    }
}
