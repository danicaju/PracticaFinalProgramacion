package practicafinalprogramacion;

import java.io.IOException;
import java.util.Random;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Main {

    //Inicializaciones de objeto LT y objeto Registro
    private final LT lector = new LT();
    private Registro registroPartida;
    //Entrada por teclado que leera como array de caracteres algunos de los datos del usuario
    //como su nombre, tambien la palabra que introduzca en la ronda de letras, etc.
    private char entradaPorTeclado[];
    //Numero de caracteres aleatorios de la ronda de letras
    private char caracteresAleatorios[] = new char[10];
    //Numero de cifras aleatorias de la ronda de cifras
    private int cifrasAleatorias[] = new int[6];
    //Entero que representa la ronda en la que se encuentran, supondremos que siempre
    //el numero de rondas es >= 1
    private int rondaActual = 1;
    // Entero que acumula la puntuación de las rondas del jugador 1
    private int acumuladorPuntosJugador1 = 0;
    // Entero que acumula la puntuación de las rondas del jugador 2
    private int acumuladorPuntosJugador2 = 0;
    // Booleano que determinara si el usuario ha pasado su turno
    private boolean haPasado = false;
    // Int para puntuaje temporal de rondas de cifras
    private int puntuajeCifras = 0;
    // Cantidad de cifras que selecciona el usuario
    private int cantidadCifras = 6;
    //Strings con los nombres de los ficheros proporcionados
    private String ficheroLetras = "letras_es.txt";
    private String diccionario = "dic_es.txt";
    private final String ficheroCifras = "cifras.txt";
    private final String ficheroPartidas = "partidas.txt";

    public void inicializarPartida() {
        registroPartida = new Registro();
        rondaActual = 1;
        acumuladorPuntosJugador1 = 0;
        acumuladorPuntosJugador2 = 0;
        puntuajeCifras = 0;
        haPasado = false;
        // Limpiar el lector si es necesario
        lector.clear();
    }

    public void menuPrincipal() throws Exception {
        boolean salirDelPrograma = false;

        // El bucle se repite mientras NO queramos salir
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
                         
                         Opcion(1|2|s): """);

            Character opcion = lector.llegirCaracter();

            if (opcion == null) {
                lector.clear(); // Limpiamos buffer
                System.err.println("\nERROR. Introduce una opcion valida!");
            } else {
                switch (opcion) {
                    case '1' -> {
                        lector.clear();
                        opcionJugar();
                    }
                    case '2' -> {
                        lector.clear();
                        opcionRegistro();
                    }
                    case '3' -> {
                        lector.clear();
                        opcionOpciones();
                    }
                    case 's' -> {
                        System.out.println("Saliendo... Gracias por Jugar a Cifras y Letras!");
                        salirDelPrograma = true;
                    }
                    default -> {
                        lector.clear();
                        System.err.println("\nERROR. Introduce una opcion valida!");
                    }
                }
            }
        }
    }

    //METODOS PRINCIPALES
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
                                 
                                 Opcion (1|2|3|s): """);

            Character opcion = lector.llegirCaracter();
            if (opcion == null) {
                lector.clear();
                System.err.println("\nERROR. Introduce una opcion valida!");
            } else {
                //Switch si le ha dado al 1 (Jugar)
                switch (opcion) {
                    case '1' -> {
                        inicializarPartida();

                        //Jugar contra la CPU
                        casoJugarContraCPU();
                        while (rondaActual <= registroPartida.getNumeroRondas()) {

                            // TURNO LETRAS JUGADOR 1
                            casoTurnoLetrasJugador1ContraJugador2();
                            mostrarLetrasDisponibles();
                            puedeFormarseJugador();
                            if (!haPasado) {
                                existeEnDiccionarioJugador();
                                //En el caso de que exista, premiar al jugador con puntos
                                puntuacionLetrasJugador1();
                            } else {
                                jugador1PasaTurno();
                            }
                            mostrarPuntuacionesJugador1Jugador2();

                            //Reinicio haPasado en el caso de que sea true, para que
                            //en la siguiente ronda, el usuario no pase directamente
                            haPasado = false;

                            // TURNO LETRAS CPU
                            casoTurnoJugador2ContraJugador1();
                            mostrarLetrasDisponibles();
                            puedeFormarseCPU();
                            puntuacionLetrasJugador2();
                            mostrarPuntuacionesJugador1Jugador2();
                            rondaActual++;

                            // TURNO CIFRAS JUGADOR
                            casoTurnoCifrasJugador1ContraJugador2();
                            generacionCifrasAleatorias();
                            operacionesCifrasJugador();
                            puntuacionCifrasJugador1();
                            mostrarPuntuacionesJugador1Jugador2();

                            // TURNO CIFRAS CPU
                            casoTurnoJugador2ContraJugador1();
                            generacionCifrasAleatorias();
                            operacionesCifrasCPU();
                            puntuacionCifrasJugador2();
                            mostrarPuntuacionesJugador1Jugador2();
                            rondaActual++;
                        }
                        escribirResultadosPartida();
                        finalPartida();
                        volverAlMenu = true;
                    }
                    case '2' -> {
                        inicializarPartida();

                        //Jugar contra otro jugador
                        casoJugador1ContraJugador2();
                        while (rondaActual <= registroPartida.getNumeroRondas()) {

                            // TURNO LETRAS JUGADOR 1
                            casoTurnoLetrasJugador1ContraJugador2();
                            mostrarLetrasDisponibles();
                            puedeFormarseJugador();
                            if (!haPasado) {
                                existeEnDiccionarioJugador();
                                //En el caso de que exista, premiar al jugador con puntos
                                puntuacionLetrasJugador1();
                            } else {
                                jugador1PasaTurno();
                            }
                            mostrarPuntuacionesJugador1Jugador2();

                            //Reinicio haPasado en el caso de que sea true, para que
                            //en la siguiente ronda, el usuario no pase directamente
                            haPasado = false;

                            // TURNO LETRAS JUGADOR 2
                            casoTurnoJugador2ContraJugador1();
                            mostrarLetrasDisponibles();
                            puedeFormarseJugador();
                            if (!haPasado) {
                                existeEnDiccionarioJugador();
                                //En el caso de que exista, premiar al jugador con puntos
                                puntuacionLetrasJugador2();
                            } else {
                                jugador2PasaTurno();
                            }
                            mostrarPuntuacionesJugador1Jugador2();
                            rondaActual++;

                            //Reinicio haPasado en el caso de que sea true, para que
                            //en la siguiente ronda, el usuario no pase directamente
                            haPasado = false;

                            // TURNO CIFRAS JUGADOR 1
                            casoTurnoCifrasJugador1ContraJugador2();
                            generacionCifrasAleatorias();
                            operacionesCifrasJugador();
                            puntuacionCifrasJugador1();
                            mostrarPuntuacionesJugador1Jugador2();

                            // TURNO CIFRAS JUGADOR 2
                            casoTurnoJugador2ContraJugador1();
                            generacionCifrasAleatorias();
                            operacionesCifrasJugador();
                            puntuacionCifrasJugador2();
                            mostrarPuntuacionesJugador1Jugador2();
                            rondaActual++;
                        }
                        escribirResultadosPartida();
                        finalPartida();
                        volverAlMenu = true;
                    }

                    case 's' -> {
                        lector.clear();
                        volverAlMenu = true;
                    }

                    default -> {
                        lector.clear();
                        System.err.println("\nERROR. Introduce una opcion valida!");
                    }

                }
            }
        }
    }

    public void opcionRegistro() throws Exception {
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
                
                                   Opcion (1|2|3|s): """);

            Character opcion = lector.llegirCaracter();
            if (opcion == null) {
                lector.clear();
                System.err.println("\nERROR. Introduce una opcion valida!");
            } else {
                switch (opcion) {
                    case '1' -> {
                        mostrarResultadosPartidas();
                        lector.clear();
                    }
                    case '2' -> {
                        mostrarEstadisticasJugador();
                        lector.clear();
                    }
                    case 's' -> {
                        volverAlMenu = true;
                    }
                    default -> {
                        lector.clear();
                        System.err.println("\nERROR. Introduce una opcion valida!");
                    }
                }
            }
        }
    }

    public void opcionOpciones() throws Exception {
        boolean volverAlMenu = false;
        while (!volverAlMenu) {
            System.out.print("""    
                         
                                   ************************************
                                   OPCIONES
                                   ************************************
                                       1. Configurar cantidad de letras
                                       2. Configurar cantidad de cifras
                                       3. Configurar idioma
                                       s. Volver al menu principal
                                   ************************************
                
                                   Opcion (1|2|3|s): """);

            Character opcion = lector.llegirCaracter();
            if (opcion == null) {
                lector.clear();
                System.err.println("\nERROR. Introduce una opcion valida!");
            } else {
                switch (opcion) {
                    case '1' -> {
                        configurarCantidadLetras();

                    }
                    case '2' -> {
                        configurarCantidadCifras();
                    }
                    case '3' -> {
                        configurarIdioma();
                    }
                    case 's' -> {
                        volverAlMenu = true;
                    }
                    default -> {
                        lector.clear();
                        System.err.println("\nERROR. Introduce una opcion valida!");
                    }
                }
            }
        }
    }

    public void configurarCantidadLetras() {
        boolean cantidadLetrasValida = false;
        System.out.println("\n[INFO] Longitud establecida en 10 (por defecto).");

        while (!cantidadLetrasValida) {
            System.out.print("Introduce la cantidad de letras [10-20]: ");
            lector.clear();
            Integer opcion = lector.llegirEnter();
            if (opcion == null) {
                System.err.println("ERROR. Escribe una cantidad valida!");
            } else if (opcion > 20) {
                System.err.println("ERROR. Maximo 20 letras!");
            } else if (opcion < 10) {
                System.err.println("ERROR. Minimo 10 letras!");
            } else {
                lector.clear();
                caracteresAleatorios = new char[opcion];
                cantidadLetrasValida = true;
            }
        }
    }

    public void configurarCantidadCifras() {
        boolean cantidadCifrasValida = false;
        System.out.println("\n[INFO] Longitud establecida en 6 (por defecto).");

        while (!cantidadCifrasValida) {
            System.out.print("Introduce la cantidad de cifras [6-10]: ");
            lector.clear();
            Integer opcion = lector.llegirEnter();
            if (opcion == null) {
                System.err.println("ERROR. Escribe una cantidad valida!");
            } else if (opcion > 10) {
                System.err.println("ERROR. Maximo 10 cifras!");
            } else if (opcion < 6) {
                System.err.println("ERROR. Minimo 6 cifras!");
            } else {
                lector.clear();
                cantidadCifras = opcion;
                cifrasAleatorias = new int[cantidadCifras];
                cantidadCifrasValida = true;
            }
        }
    }

    public void configurarIdioma() {
        boolean idiomaValido = false;
        System.out.print("\n[INFO] Idioma establecido en castellano (por defecto).\n");

        while (!idiomaValido) {

            System.out.print("""    
                         
                                   ************************************
                                   IDIOMAS DISPONIBLES
                                   ************************************
                                       1. Castellano
                                       2. Catalan
                                       3. Ingles
                                   ************************************
                
                                   Opcion (1|2|3|s): """);
            lector.clear();
            Integer opcion = lector.llegirEnter();
            if (opcion == null) {
                System.err.println("ERROR. No has escrito nada!");
            } else {
                switch (opcion) {
                    case 1 -> {
                        diccionario = "dic_es.txt";
                        ficheroLetras = "letras_es.txt";
                        lector.clear();
                        idiomaValido = true;
                    }
                    case 2 -> {
                        diccionario = "dic_ca.txt";
                        ficheroLetras = "letras_ca.txt";
                        lector.clear();
                        idiomaValido = true;
                    }
                    case 3 -> {
                        diccionario = "dic_en.txt";
                        ficheroLetras = "letras_en.txt";
                        lector.clear();
                        idiomaValido = true;
                    }
                    default -> {
                        System.err.println("ERROR. Introduce una opcion valida!");
                        lector.clear();
                    }
                }
            }
        }
    }

    private void pedirNombreValido() {
        boolean nombreValido = false;

        while (!nombreValido) {
            System.out.print("Introduce el nombre del jugador: ");
            entradaPorTeclado = lector.llegirLinia();

            if (entradaPorTeclado.length == 0) {
                System.err.println("ERROR. No has escrito nada!");
                lector.clear();
            } else if (entradaPorTeclado[0] == ' ') {
                System.err.println("ERROR. El nombre no puede empezar por espacio!");
                lector.clear();
            } else {
                nombreValido = true;
            }
        }
        /*
        Llegados a este punto, sabemos que el array no está vacío y no empieza por espacio.
        Utilizaremos este metodo para limpiar espacios internos innecesarios que haya podido
        introducir el usuario
         */
        char arrayAux[] = new char[entradaPorTeclado.length];
        boolean espacioYaPuesto = false;
        int numCaracteres = 0;

        for (int i = 0, j = 0; i < entradaPorTeclado.length; i++) {
            if (entradaPorTeclado[i] != ' ') {
                arrayAux[j++] = entradaPorTeclado[i];
                espacioYaPuesto = false;
                numCaracteres++;
            } else {
                // Solo metemos espacio si no acabamos de poner uno
                if (!espacioYaPuesto) {
                    arrayAux[j++] = ' ';
                    espacioYaPuesto = true;
                    numCaracteres++;

                }
            }
        }

        //Ajustamos la longitud final del array
        char arrayFinalAux[] = new char[numCaracteres];
        for (int k = 0; k < numCaracteres; k++) {
            arrayFinalAux[k] = arrayAux[k];
        }

        //Hacemos que entradaPorTeclado ahora apunte a arrayFinalAux
        //para que entradaPorTeclado tenga el mismo contenido
        entradaPorTeclado = arrayFinalAux;
    }

    public void pedirNumeroRondasValido() {
        System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
        /*
        Aqui, usamos un Integer porque nos dimos cuenta que al poner cualquier cosa que no 
        fuera un numero con un int opcion, el programa petaba devolviendo nulo, por ejemplo,
        si pusieramos un caracter del abecedario, entonces lo que hicimos es usar
        la variante objeto del entero que es el Integer para poder capturar ese
        error de nulo y pedir otro numero al usuario.
         */
        boolean numeroRondasValido = false;
        int numeroRondas = 0;

        while (!numeroRondasValido) {
            lector.clear();
            Integer opcion = lector.llegirEnter();
            if (opcion == null) {
                System.err.println("ERROR. No has escrito nada!");
                System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            } else if (opcion >= 20) {
                System.err.println("ERROR. Maximo 20 rondas!");
                System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            } else if (opcion % 2 != 0) {
                System.err.println("ERROR. Escribe un numero par de rondas!");
                System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            } else if (opcion % 2 == 0) {
                numeroRondas = opcion;
                numeroRondasValido = true;
            }
        }
        //Si ha llegado aqui, la opcion no es nula y tampoco es impar
        registroPartida.setNumeroRondas(numeroRondas);
    }

    public void casoJugarContraCPU() {
        System.out.println("""
                                           
                                           ************************************
                                           JUGAR CONTRA EL ORDENADOR
                                           ************************************""");

        pedirNombreValido();

        String aux = new String(entradaPorTeclado);
        registroPartida.setNombreJugador1(aux);

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

        pedirNombreValido();

        String aux = new String(entradaPorTeclado);
        registroPartida.setNombreJugador1(aux);

        pedirNombreValido();

        String aux2 = new String(entradaPorTeclado);
        registroPartida.setNombreJugador2(aux2);

        System.out.println("Nombre del jugador 1: " + aux);
        System.out.println("Nombre del jugador 2: " + aux2);
        registroPartida.setNombreJugador2(aux2);

        pedirNumeroRondasValido();
        registroPartida.setTipoPartida("vs humano");
    }

    public void casoTurnoLetrasJugador1ContraJugador2() {
        System.out.println("\nRonda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");

        System.out.println("Turno de: " + registroPartida.getNombreJugador1());
    }

    public void casoTurnoCifrasJugador1ContraJugador2() {
        System.out.println("\nRonda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");
        System.out.println("Turno de: " + registroPartida.getNombreJugador1());
    }

    public void casoTurnoJugador2ContraJugador1() {
        System.out.println("\nTurno de: " + registroPartida.getNombreJugador2());
    }

    public void jugador1PasaTurno() {
        System.out.println("Has pasado!");
    }

    public void jugador2PasaTurno() {
        System.out.println("Has pasado!");
    }

    public void mostrarLetrasDisponibles() {
        try {
            FicherosLectura lecturaFichero = new FicherosLectura(ficheroLetras);
            String lecturaLetrasDisponibles;
            lecturaLetrasDisponibles = lecturaFichero.leerFichero();
            Random random = new Random();

            char arrayFicheroLetras[] = lecturaLetrasDisponibles.toCharArray();
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                int indiceAleatorio = random.nextInt(arrayFicheroLetras.length);
                caracteresAleatorios[i] = arrayFicheroLetras[indiceAleatorio];
            }
            System.out.println("Letras disponibles: ");
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                System.out.print(caracteresAleatorios[i] + " ");
            }
            lecturaFichero.cerrarFichero();

        } catch (IOException e) {
            System.err.println("\nERROR. Fichero no encontrado");
        }
    }

    public void generacionCifrasAleatorias() throws IOException {
        FicherosLectura ficheroDeCifras = new FicherosLectura(ficheroCifras);
        String lectura;
        char arrayLectura[];
        int num = 0;
        // Inicializo un array con la longitud necesaria para los 24
        // numeros del fichero (1-10 x2, 25, 50, 75, 100).
        int arrayAux[] = new int[24];
        Random random = new Random();

        // Uso un String para leer el fichero, al haber solo una linea en el fichero
        // no hace falta que ejecute un bucle while de lectura, simplemente una
        // sola instruccion y convierto el String "lectura" a array de caracteres
        // "arrayLectura"
        lectura = ficheroDeCifras.leerFichero();
        arrayLectura = lectura.toCharArray();

        /*
        Comprobamos ahora el array de caracteres, y cada vez que leemos un numero
        le multiplicaremos 10 y ademas le restaremos el valor en ASCII del 0, 
        para tener su equivalente en int. 
        Los numeros se leen de uno en uno pero al estar yo almacenando en num
        el valor del numero anterior, si multiplico por 10 y le resto el
        equivalente en ascii al numero actual tengo el numero completo. 
        
        Al haber leido ya un numero completo y encontrarnos con un
        espacio, almacenamos "num" que tiene el numero en un
        arrayAux[j] que tiene el espacio suficiente para los
        24 numeros del fichero.
         */
        int j = 0; // Declaramos j fuera para usarla tras el bucle
        for (int i = 0; i < arrayLectura.length; i++) {
            if (arrayLectura[i] != ' ') { //Si es un numero
                num = num * 10 + (arrayLectura[i] - '0');
            } else {
                arrayAux[j++] = num;
                num = 0;
            }
        }

        // El último número del fichero se agrega después del bucle,
        // ya que no termina con un espacio.
        if (num != 0) {
            arrayAux[j] = num;
        }

        int cantidadDisponibles = arrayAux.length; // 24

        // Vuelvo a hacer que cifrasAleatorias[] tenga esta longitud porque al estar reutilizando
        // el metodo tanto para el jugador 1 como para el jugador 2, si no hago esto,
        // cuando ya es el turno del jugador 2, cifrasAleatorias[] tiene la longitud
        // con la que el jugador 1 dejo el array cuando acabo su turno
        cifrasAleatorias = new int[cantidadCifras];

        for (int k = 0; k < cifrasAleatorias.length; k++) {
            // Primero elegimos un índice aleatorio entre 0 y los que quedan disponibles
            int indiceAleatorio = random.nextInt(cantidadDisponibles);

            // Luego asignamos el número de ese índice a nuestro array de cifras de la ronda
            cifrasAleatorias[k] = arrayAux[indiceAleatorio];

            // Reemplazamos el número que acabamos de usar 
            // con el ultimo número que aun no ha sido seleccionado.
            arrayAux = eliminarNumeroDelArray(arrayAux, cantidadDisponibles - 1);

            // Por ultimo, reducimos el rango de seleccion para que el ultimo número
            // (el recién movido) no vuelva a ser elegido.
            cantidadDisponibles--;
        }
        ficheroDeCifras.cerrarFichero();
    }

    public void operacionesCifrasJugador() {
        Random objRandom = new Random();
        int objetivo = objRandom.nextInt(100, 999);
        int numOperacion = 1;
        int numOperando = 1;
        Integer resultadoFinal;
        Character tipoOperacion;
        boolean resultadoValidoElegido = false;
        int historialNumeros[] = new int[cifrasAleatorias.length];
        for (int i = 0; i < cifrasAleatorias.length; i++) {
            historialNumeros[i] = cifrasAleatorias[i];
        }

        while (!resultadoValidoElegido) {
            System.out.print("Cifras disponibles: ");
            for (int i = 0; i < cifrasAleatorias.length; i++) {
                System.out.print(cifrasAleatorias[i] + " ");
            }
            System.out.println("\nObjetivo: " + objetivo);
            System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");

            // Si es nulo (intro), entonces vuelve a pedirle que escriba
            // una operacion valida. Ademas mientras no sea una de las
            // operaciones validas, dara error y se pedira al usuario
            // que introduzca una operacion entre las proporcionadas
            tipoOperacion = lector.llegirCaracter();
            boolean operacionValida = false;
            while (!operacionValida) {
                if (tipoOperacion == null) {
                    System.err.println("ERROR. No has escrito nada!");
                    System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
                    lector.clear();
                    tipoOperacion = lector.llegirCaracter();
                } else if (tipoOperacion != '+' && tipoOperacion != '-'
                        && tipoOperacion != '*' && tipoOperacion != '/' && tipoOperacion != '=') {
                    System.err.println("ERROR. Introduce una operacion valida!");
                    System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
                    lector.clear();
                    tipoOperacion = lector.llegirCaracter();
                } else {
                    operacionValida = true;
                }
            }

            // Si cifrasAleatorias.length es 1 entonces solo queda un numero 
            // en el array, por tanto, obligo al usuario a dar ya el
            // resultado final porque no puede sumar ni restar
            // ni hacer otra operacion mas con un solo numero
            if (cifrasAleatorias.length == 1) {
                while (tipoOperacion != '=') {
                    System.err.println("ERROR. Debes introducir un resultado, solo tienes una cifra!");
                    System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
                    lector.clear();
                    tipoOperacion = lector.llegirCaracter();

                    while (tipoOperacion == null) {
                        lector.clear();
                        System.err.println("ERROR. Debes introducir un resultado, solo tienes una cifra!");
                        System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
                        tipoOperacion = lector.llegirCaracter();
                    }
                }

            }

            // Mas abajo hay un switch con todas las operaciones pero yo para el caso del '='
            // no quiero que me salga operando 1 y operando 2, asi que antes de eso, he mirado
            // si la operacion es la del igual, si lo fuera, entonces un booleano
            // resultadoElegido seria true y este metodo operacionesCifrasJugador()
            // no se volveria a repetir y se asignaria la puntuacion al jugador
            if (tipoOperacion == '=') {
                System.out.print("Introduce el resultado final (");
                for (int i = 0; i < historialNumeros.length; i++) {
                    if (i == historialNumeros.length - 1) {
                        System.out.print(historialNumeros[i] + "): ");
                    } else {
                        System.out.print(historialNumeros[i] + " ");

                    }
                }
                lector.clear();
                resultadoFinal = lector.llegirEnter();

                while (!resultadoValidoElegido) {
                    // Entrara aqui si resultado es nulo (intro)
                    while (resultadoFinal == null) {
                        lector.clear();
                        System.err.println("ERROR. No has escrito nada!");
                        System.out.print("Introduce el resultado final (");
                        for (int i = 0; i < historialNumeros.length; i++) {
                            if (i == historialNumeros.length - 1) {
                                System.out.print(historialNumeros[i] + "): ");
                            } else {
                                System.out.print(historialNumeros[i] + " ");

                            }
                        }
                        resultadoFinal = lector.llegirEnter();
                    }
                    for (int i = 0; i < historialNumeros.length && !resultadoValidoElegido; i++) {
                        if (historialNumeros[i] == resultadoFinal) {
                            resultadoValidoElegido = true;
                        }
                    }
                    if (!resultadoValidoElegido) {
                        System.err.println("ERROR. El resultado no esta entre las cifras disponibles!");
                        System.out.print("Introduce el resultado final (");
                        for (int i = 0; i < historialNumeros.length; i++) {
                            if (i == historialNumeros.length - 1) {
                                System.out.print(historialNumeros[i] + "): ");
                            } else {
                                System.out.print(historialNumeros[i] + " ");

                            }
                        }
                        lector.clear();
                        resultadoFinal = lector.llegirEnter();
                    }
                }

                if (resultadoValidoElegido) {
                    // Si el resultado esta en el array, entonces si que podemos
                    // calcular la diferencia entre objetivo y resultado
                    int diferencia = objetivo - resultadoFinal;
                    if (diferencia < 0) {
                        // Si la diferencia es negativa, entonces, la pasamos a positivo
                        // para asignar un valor positivo de puntos al usuario
                        diferencia = diferencia * (-1);
                    }

                    if (diferencia == 0) {
                        puntuajeCifras = 10;
                        System.out.println("Resultado exacto: +" + puntuajeCifras + " puntos");
                    } else if (diferencia >= 1 && diferencia <= 5) {
                        puntuajeCifras = 7;
                        System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
                    } else if (diferencia >= 6 && diferencia <= 10) {
                        puntuajeCifras = 5;
                        System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
                    } else {
                        puntuajeCifras = 0;
                        System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
                    }
                    resultadoValidoElegido = true;
                }
            }

            if (!resultadoValidoElegido) {
                // Si ha llegado hasta aqui, la operacion que 
                // ha elegido el usuario es valida y 
                // incrementamos de forma adelantada ya que la 
                // proxima operacion que esperamos es la segunda

                // Este System,out.println() y todo el bucle for
                // es para que se vea de la manera que queremos
                // por pantalla
                System.out.print("Operando " + numOperando + " (");
                // Primer bucle for para el 1r operando
                for (int j = 0; j < cifrasAleatorias.length; j++) {
                    if (j == cifrasAleatorias.length - 1) {
                        System.out.print(cifrasAleatorias[j] + "): ");
                    } else {
                        System.out.print(cifrasAleatorias[j] + " ");
                    }
                }
                // Se guarda el primer operando del usuario
                lector.clear();
                Integer operando1 = lector.llegirEnter();

                // Booleano que usaremos para determinar si el operando
                // que ha elegido el usuario existe entre las
                // cifras aleatorias
                boolean existeElOperando1 = false;
                int indiceAEliminar1 = -1;

                // Supondremos que el operando que ha puesto el usuario
                // es erroneo por facilidad a la hora de programar
                // y miraremos lo antes posible si es correcto
                // o no mirando si existe entre cifrasAleatorias      
                while (!existeElOperando1) {

                    while (operando1 == null) {
                        lector.clear();
                        System.err.println("ERROR. Introduce un operando valido!");
                        System.out.print("Operando " + numOperando + " (");
                        for (int j = 0; j < cifrasAleatorias.length; j++) {
                            if (j == cifrasAleatorias.length - 1) {
                                System.out.print(cifrasAleatorias[j] + "): ");
                            } else {
                                System.out.print(cifrasAleatorias[j] + " ");
                            }
                        }
                        operando1 = lector.llegirEnter();
                    }
                    // Si en todas las cifras aleatorias, encuentra
                    // alguna que sea igual que el operando del
                    // usuario entonces, existe el operando
                    for (int i = 0; i < cifrasAleatorias.length && !existeElOperando1; i++) {
                        if (operando1 == cifrasAleatorias[i]) {
                            indiceAEliminar1 = i;
                            existeElOperando1 = true;
                        }
                    }
                    // Si no existiera, se llevaria a cabo un bucle 
                    // constante hasta que el usuario escribiera
                    // un operando valido
                    if (!existeElOperando1) {
                        System.err.println("ERROR. Introduce un operando valido!");
                        System.out.print("Operando " + numOperando + " (");
                        for (int i = 0; i < cifrasAleatorias.length; i++) {
                            if (i == cifrasAleatorias.length - 1) {
                                System.out.print(cifrasAleatorias[i] + "): ");
                            } else {
                                System.out.print(cifrasAleatorias[i] + " ");
                            }
                        }
                        lector.clear();
                        operando1 = lector.llegirEnter();
                    }
                }
                // EN EL CASO DE QUE LA OPERACION QUE LLEVEMOS A CABO LUEGO NO 
                // SEA VALIDA, SE RECUPERARA CIFRAS ALEATORIAS CON ESTA
                // REFERENCIA
                int arrayRecuperarCifrasAleatorias[] = cifrasAleatorias;

                if (indiceAEliminar1 != -1) {
                    cifrasAleatorias = eliminarNumeroDelArray(cifrasAleatorias, indiceAEliminar1);
                }

                numOperando++;

                // Segundo bucle for para el 2n operando
                System.out.print("Operando " + numOperando + " (");
                for (int i = 0; i < cifrasAleatorias.length; i++) {
                    if (i == cifrasAleatorias.length - 1) {
                        System.out.print(cifrasAleatorias[i] + "): ");
                    } else {
                        System.out.print(cifrasAleatorias[i] + " ");
                    }
                }

                lector.clear();
                Integer operando2 = lector.llegirEnter();
                // Booleano que usaremos para determinar si el operando
                // que ha elegido el usuario existe entre las
                // cifras aleatorias
                boolean existeElOperando2 = false;
                int indiceAEliminar2 = -1;
                // Supondremos que el operando que ha puesto el usuario
                // es erroneo por facilidad a la hora de programar
                // y miraremos lo antes posible si es correcto
                // o no mirando si existe entre cifrasAleatorias
                while (!existeElOperando2) {

                    while (operando2 == null) {
                        lector.clear();
                        System.err.println("ERROR. Introduce un operando valido!");
                        System.out.print("Operando " + numOperando + " (");
                        for (int j = 0; j < cifrasAleatorias.length; j++) {
                            if (j == cifrasAleatorias.length - 1) {
                                System.out.print(cifrasAleatorias[j] + "): ");
                            } else {
                                System.out.print(cifrasAleatorias[j] + " ");
                            }
                        }
                        operando2 = lector.llegirEnter();
                    }
                    // Si en todas las cifras aleatorias, encuentra
                    // alguna que sea igual que el operando del
                    // usuario entonces, existe el operando
                    for (int i = 0; i < cifrasAleatorias.length && !existeElOperando2; i++) {
                        if (operando2 == cifrasAleatorias[i]) {
                            indiceAEliminar2 = i;
                            existeElOperando2 = true;
                        }
                    }
                    // Si no existiera, se llevaria a cabo un bucle 
                    // constante hasta que el usuario escribiera
                    // un operando valido
                    if (!existeElOperando2) {
                        System.err.println("ERROR. Introduce un operando valido!");
                        System.out.print("Operando " + numOperando + " (");
                        for (int i = 0; i < cifrasAleatorias.length; i++) {
                            if (i == cifrasAleatorias.length - 1) {
                                System.out.print(cifrasAleatorias[i] + "): ");
                            } else {
                                System.out.print(cifrasAleatorias[i] + " ");
                            }
                        }
                        lector.clear();
                        operando2 = lector.llegirEnter();
                    }
                }
                numOperando--;

                if (indiceAEliminar2 != -1) {
                    cifrasAleatorias = eliminarNumeroDelArray(cifrasAleatorias, indiceAEliminar2);
                }

                switch (tipoOperacion) {
                    case '+' -> {
                        numOperacion++;
                        int resultadoSuma = operando1 + operando2;

                        System.out.println(operando1 + " + " + operando2 + " = " + resultadoSuma + "\n");

                        cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultadoSuma);
                        historialNumeros = agregarNumeroAlArray(historialNumeros, resultadoSuma);
                    }

                    case '*' -> {
                        numOperacion++;
                        int resultadoMultiplicacion = operando1 * operando2;

                        System.out.println(operando1 + " * " + operando2 + " = " + resultadoMultiplicacion + "\n");

                        cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultadoMultiplicacion);
                        historialNumeros = agregarNumeroAlArray(historialNumeros, resultadoMultiplicacion);
                    }

                    case '-' -> {
                        if ((operando1 - operando2) < 0) {
                            System.err.println("ERROR. La resta no puede llevarse a cabo!");
                            cifrasAleatorias = arrayRecuperarCifrasAleatorias;
                        } else if (operando1 == operando2) {
                            /*
                            Si son iguales, no hago nada para que no se acumule un 0
                            en el array, entonces cifrasAleatorias[] ha llegado aqui
                            y sera la misma que cuando se suprimieron el operando1
                            y operando2 del array, para que una resta entre dos
                            numeros iguales no se pueda guardar un 0.
                             */
                        } else {
                            numOperacion++;
                            int resultadoResta = operando1 - operando2;
                            System.out.println("- se ha comprobado que puede llevarse a cabo la resta "
                                    + operando1 + " - " + operando2 + ".");
                            System.out.println(operando1 + " - " + operando2 + " = " + resultadoResta + "\n");

                            cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultadoResta);
                            historialNumeros = agregarNumeroAlArray(historialNumeros, resultadoResta);
                        }
                    }

                    case '/' -> {
                        if (operando1 % operando2 != 0) {
                            System.err.println("ERROR. La division no es entera!");
                            cifrasAleatorias = arrayRecuperarCifrasAleatorias;
                        } else {
                            numOperacion++;
                            int resultadoDivision = operando1 / operando2;
                            System.out.println("- se ha comprobado que puede llevarse a cabo la division entera "
                                    + operando1 + " / " + operando2 + ".");

                            cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultadoDivision);
                            historialNumeros = agregarNumeroAlArray(historialNumeros, resultadoDivision);
                        }
                    }
                    default -> {
                        System.err.println("ERROR. Introduce un operando valido!");
                    }
                }
            }
        }
    }

    public void operacionesCifrasCPU() {
        Random random = new Random();
        char arrayOperaciones[] = {'+', '-', '*', '/'};
        char operacion = 0;
        int operando1 = 0;
        int operando2 = 0;
        int indiceOperando1;
        int indiceOperando2;
        int numOperacion = 1;
        int numeroMasCercanoResultado = 0;
        int resultado = 0;
        int objetivo = random.nextInt(100, 999);
        boolean quedanNumeros = true;
        boolean anteriorOperacionValida = false;
        int historialNumeros[] = new int[cifrasAleatorias.length];
        for (int i = 0; i < cifrasAleatorias.length; i++) {
            historialNumeros[i] = cifrasAleatorias[i];
        }

        while (cifrasAleatorias.length > 1) {

            /*
            Usamos el booleano anteriorOperacionNoValida porque solo me interesa que ponga este mensaje
            cuando la anterior operacion ha sido valida (no ha dado numero negativo
            ni tampoco ha intentado hacer una  division no entera). Asi da la
            sensacion de que la CPU no se equivoca aunque realmente
            al ser aleatoria si puede equivocarse y por desgracia
            muy frecuentemente
             */
            if (anteriorOperacionValida) {
                System.out.print("Cifras disponibles: ");
                for (int i = 0; i < cifrasAleatorias.length; i++) {
                    System.out.print(cifrasAleatorias[i] + " ");
                }
                System.out.println("\nObjetivo: " + objetivo);
            }
            // Reinicio el booleano a false, luego se puede poner a true
            // cuando miro si la resta o division es valida en el switch
            anteriorOperacionValida = true;

            int indiceOperacionRandom = random.nextInt(0, arrayOperaciones.length);
            //Bucle for para la operacion aleatoria
            for (int i = 0; i < arrayOperaciones.length && quedanNumeros; i++) {
                if (i == indiceOperacionRandom) {
                    operacion = arrayOperaciones[i];
                }
            }

            //Creo un indice que sera aleatoriamente una de las posiciones
            //del array de CifrasAleatorias
            indiceOperando1 = random.nextInt(0, cifrasAleatorias.length);

            /*
                Bucle para elegir el primer operando, si el indice coincide 
                con el del indice aleatorio, indiceOperando1, entonces, 
                guardamos en el operando1 lo que haya en esa posicion
                del indice
             */
            for (int i = 0; i < cifrasAleatorias.length; i++) {
                if (i == indiceOperando1) {
                    operando1 = cifrasAleatorias[i];
                }
            }

            // En el caso de que no se pueda realizar una operacion recuperare el valor
            // de cifrasAleatorias con esta referencia
            int arrayRecuperarCifrasAleatorias[] = cifrasAleatorias;

            // Elimino el primer operando que ha cogido del array para que no pueda
            // usarlo otra vez
            cifrasAleatorias = eliminarNumeroDelArray(cifrasAleatorias, indiceOperando1);

            if (cifrasAleatorias.length != 0) {
                indiceOperando2 = random.nextInt(0, cifrasAleatorias.length);
                // Bucle para ver si encuentra el segundo operando
                for (int i = 0; i < cifrasAleatorias.length; i++) {
                    if (i == indiceOperando2) {
                        operando2 = cifrasAleatorias[i];
                    }
                }
            } else {
                // Solo puede ser este
                operando2 = cifrasAleatorias[0];
            }

            // Elimino el segundo operando del array
            cifrasAleatorias = eliminarNumeroDelArray(cifrasAleatorias, indiceOperando1);

            switch (operacion) {
                case '+' -> {
                    numOperacion++;
                    resultado = operando1 + operando2;
                    System.out.println("Operacion: " + numOperacion + ": " + operando1 + " + " + operando2 + " = " + resultado + "\n");

                    cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultado);
                    historialNumeros = agregarNumeroAlArray(historialNumeros, resultado);
                }

                case '-' -> {
                    if ((operando1 - operando2) < 0) {
                        cifrasAleatorias = arrayRecuperarCifrasAleatorias;
                        anteriorOperacionValida = false;
                    } else if (operando1 == operando2) {
                        /*
                         Restauramos para que la CPU pueda intentar SUMARLOS o MULTIPLICARLOS
                         en el siguiente intento ya que al ser aleatoria, es muy probable 
                         que reste dos operandos iguales y ya los pierda, (es mucho mas dificil
                         que gane si hace eso), entonces es mejor en este caso que recupere
                         esos operandos para que tenga la oportunidad de sumarlos o multiplicarlos
                         */
                        cifrasAleatorias = arrayRecuperarCifrasAleatorias;
                        anteriorOperacionValida = false;

                    } else {
                        numOperacion++;
                        resultado = operando1 - operando2;
                        System.out.println("- se ha comprobado que puede llevarse a cabo la resta "
                                + operando1 + " - " + operando2 + ".");
                        System.out.println("Operacion: " + numOperacion + ": " + operando1 + " - " + operando2 + " = " + resultado + "\n");

                        cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultado);
                        historialNumeros = agregarNumeroAlArray(historialNumeros, resultado);
                    }
                }

                case '*' -> {
                    numOperacion++;
                    resultado = operando1 * operando2;
                    System.out.println("Operacion: " + numOperacion + ": " + operando1 + " * " + operando2 + " = " + resultado + "\n");

                    cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultado);
                    historialNumeros = agregarNumeroAlArray(historialNumeros, resultado);
                }

                case '/' -> {
                    if (operando1 % operando2 != 0) {
                        cifrasAleatorias = arrayRecuperarCifrasAleatorias;
                        anteriorOperacionValida = false;
                    } else {
                        numOperacion++;
                        resultado = operando1 / operando2;
                        System.out.println("- se ha comprobado que puede llevarse a cabo la division entera "
                                + operando1 + " / " + operando2 + ".");
                        System.out.println("Operacion: " + numOperacion + ": " + operando1 + " / " + operando2 + " = " + resultado + "\n");

                        cifrasAleatorias = agregarNumeroAlArray(cifrasAleatorias, resultado);
                        historialNumeros = agregarNumeroAlArray(historialNumeros, resultado);
                    }
                }

            }
        }
        int mejorDiferencia = 9999;

        for (int i = 0; i < historialNumeros.length; i++) {
            int diferenciaActual = (objetivo - historialNumeros[i]);
            if (diferenciaActual < 0) {
                diferenciaActual = diferenciaActual * (-1);
            }
            if (diferenciaActual < mejorDiferencia) {
                mejorDiferencia = diferenciaActual;
                numeroMasCercanoResultado = historialNumeros[i];
            }
        }

        System.out.print("Resultado final (");
        for (int i = 0; i < historialNumeros.length; i++) {
            if (i == historialNumeros.length - 1) {
                System.out.print(historialNumeros[i] + "): " + numeroMasCercanoResultado + "\n");
            } else {
                System.out.print(historialNumeros[i] + " ");
            }
        }
        if (mejorDiferencia == 0) {
            puntuajeCifras = 10;
            System.out.println("Resultado exacto: +" + puntuajeCifras + " puntos");
        } else if (mejorDiferencia >= 1 && mejorDiferencia <= 5) {
            puntuajeCifras = 7;
            System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
        } else if (mejorDiferencia >= 6 && mejorDiferencia <= 10) {
            puntuajeCifras = 5;
            System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
        } else {
            puntuajeCifras = 0;
            System.out.println("Resultado no exacto: +" + puntuajeCifras + " puntos");
        }
    }

    public void puedeFormarseJugador() throws Exception {
        boolean puedeFormarse = false;
        /*
        Mientras aun no se pueda formar y el usuario no haya 
        pasado su turno tiene que preguntarle continuamente
        la palabra a introducir y checkear si esa palabra
        se puede formar con las letras disponibles
         */
        while (!puedeFormarse && !haPasado) {
            System.out.print("\nIntroduce tu palabra (o escribe '.' para pasar): ");
            entradaPorTeclado = lector.llegirLinia();

            /*
            Si el usuario escribe un "intro" sin nada mas, el array seria de
            longitud 0, por tanto, luego al querer comprobar el array 
            el programa petaria, asi que hemos creado este while
            para arreglar ese error. Si la longitud del array es 0,
            entonces, te vuelve a pedir que escribas la palabra.
             */
            while (entradaPorTeclado.length == 0) {
                System.err.println("ERROR. No has escrito nada!");
                System.out.print("Introduce tu palabra (o escribe '.' para pasar): ");
                entradaPorTeclado = lector.llegirLinia();
            }
            /*
            Si la primera letra que lee es igual al 
            caracter '.', entonces el usuario ha
            decidido pasar
             */
            if (entradaPorTeclado[0] == '.') {
                haPasado = true;
            }

            /*
            Si no ha decidido pasar, entonces nos disponemos a validar la palabra,
            esto esta hecho de esta manera para que si es un punto '.' no se tome 
            el tiempo de validar la palabra.
             */
            if (!haPasado) {
                System.out.println("Validando palabra...");

                // 1. Comprobar si puede formarse con las letras disponibles
                //Copiamos el array de caracteresAleatorios en otro array 
                //que nos ayudara mas adelante, "copiaLetras"
                char copiaLetras[] = new char[caracteresAleatorios.length];
                for (int i = 0; i < caracteresAleatorios.length; i++) {
                    copiaLetras[i] = caracteresAleatorios[i]; // copiar manualmente         
                }

                /*         
                2. Recorremos las letras de la palabra que el jugador ha introducido (entradaPorTeclado)
                Por cada letra, buscamos si existe en el array de letras disponibles (copiaLetras)
                Si la encontramos, la “marcamos” sustituyéndola por '*' para que no pueda volver a reutilizarse.
                Si alguna letra no se encuentra, significa que la palabra NO puede formarse con las letras dadas.
                 */
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
                    System.err.println("La palabra NO puede formarse con las letras disponibles! Intentalo de nuevo.");
                }
            }
        }
    }

    /*
    Lo que haremos para simular una CPU, es que el programa vaya leyendo
    todo el diccionario, y a partir de eso vaya comprobando que palabras
    del diccionario pueden ser una potencial palabra con las letras 
    disponibles del metodo mostrarLetrasDisponibles()
     */
    public void puedeFormarseCPU() throws IOException {
        Random random = new Random();
        char palabraCPU[] = null;
        int contador = 0;

        FicherosLectura ficheroDic = new FicherosLectura(diccionario);
        String palabraDic;

        /*
        Mientras lo que lea no sea igual a 0 (final de linea), es decir,
        ha devuelto null y el array.length de palabraDic es igual a 0
        entonces que vaya comprobando si la palabraDic puede formarse
        con auxLetras
         */
        while ((palabraDic = ficheroDic.leerFichero()) != null) {

            /*
            char auxLetras[] nos ayuda porque en cada iteracion
            almacena una linea entera del diccionarioEspanyol 
            ("dic_es.txt").
             */
            char auxLetras[] = new char[caracteresAleatorios.length];
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                auxLetras[i] = caracteresAleatorios[i];
            }

            /*
            Comprobar si la palabraDic puede formarse con auxLetras.
            Cabe destacar que auxLetras tiene el array de
            caracteresAleatorios que se ha formado en el
            metodo mostrarLetrasDisponibles()
             */
            boolean puedeFormarse = true;
            char palabraDicArray[] = palabraDic.toCharArray();
            for (int i = 0; i < palabraDicArray.length && puedeFormarse; i++) {
                char letra = palabraDicArray[i];
                boolean encontrada = false;

                // Buscar la letra en auxLetras
                for (int j = 0; j < auxLetras.length && !encontrada; j++) {
                    if (auxLetras[j] == letra) {
                        auxLetras[j] = '*'; // marcar letra usada
                        encontrada = true;
                    }
                }

                if (!encontrada) {
                    puedeFormarse = false; // la letra no estaba disponible
                }
            }

            /*
            Si esa palabra, si pudiera formarse con las letras disponibles,
            en nuestro caso con el array auxLetras, entonces, el contador
            se incrementa para decir que ya hemos encontrado una palabra
            valida para ser la candidata a eleccion de la CPU.
            
            Para elegir la palabra valida, la probabilidad siempre es de
            1/contador, ya que por ejemplo, la primera palabra valida
            siempre se elige ya que 1/contador = 1/1 = 1 (100%). 
            
            Para la segunda palabra, se hace exactamente lo mismo, 1/contador,
            en este caso 1/contador = 1/2 (50%), y si entre el 0 y el 1,
            saliera un 0, entonces se reemplaza la palabra de la CPU 
            por esa palabra de diccionario. Y esa seria la nueva
            palabra candidata a eleccion de la CPU.
             */
            if (puedeFormarse) {
                contador++;
                int r = random.nextInt(contador);
                if (r == 0) {
                    palabraCPU = palabraDicArray; // elegimos esta palabra
                }
            }
        }

        ficheroDic.cerrarFichero();

        // Mostrar la palabra elegida por la CPU
        if (palabraCPU != null) {
            System.out.println("\nCPU elige: " + new String(palabraCPU));
            entradaPorTeclado = palabraCPU;
            registroPartida.setPuntuacionJugador2(entradaPorTeclado.length);
            lector.clear();
        } else {
            System.out.println("CPU no pudo formar ninguna palabra.");
        }
    }

    public void existeEnDiccionarioJugador() throws Exception {

        boolean palabraValida = false;

        while (!palabraValida) {
            boolean existeEnDic = false;
            FicherosLectura ficheroDic = new FicherosLectura(diccionario);
            String lineaDic;

            while ((lineaDic = ficheroDic.leerFichero()) != null && !existeEnDic) {
                char lineaDicArray[] = lineaDic.toCharArray();
                boolean iguales = false;

                if (lineaDicArray.length == entradaPorTeclado.length) {
                    iguales = true;
                    for (int i = 0; i < lineaDicArray.length && iguales; i++) {
                        if (lineaDicArray[i] != entradaPorTeclado[i]) {
                            iguales = false;
                        }
                    }
                }

                if (iguales) {
                    existeEnDic = true;
                }
            }

            ficheroDic.cerrarFichero();

            if (existeEnDic) {
                System.out.println(" - puede crearse con las letras disponibles");
                System.out.println(" - existe en el diccionario");
                palabraValida = true;
            } else {
                System.err.println("La palabra NO existe en el diccionario.");
                System.out.println("Intentalo de nuevo!");
                puedeFormarseJugador();
            }
        }
    }

    /*
    En estos metodos de puntuacion, usamos una variable acumulador
    para que vaya acumulando los puntos de todas las rondas que
    quiera jugar el jugador
     */
    public void puntuacionLetrasJugador1() {
        acumuladorPuntosJugador1 += entradaPorTeclado.length;
        registroPartida.setPuntuacionJugador1(acumuladorPuntosJugador1);
        System.out.println("Felicidades " + registroPartida.getNombreJugador1() + "! Has ganado " + entradaPorTeclado.length + " puntos!");
    }

    public void puntuacionLetrasJugador2() {
        acumuladorPuntosJugador2 += entradaPorTeclado.length;
        registroPartida.setPuntuacionJugador2(acumuladorPuntosJugador2);
        System.out.println("Felicidades " + registroPartida.getNombreJugador2() + "! Has ganado " + entradaPorTeclado.length + " puntos!");
    }

    public void puntuacionCifrasJugador1() {
        acumuladorPuntosJugador1 += puntuajeCifras;
        registroPartida.setPuntuacionJugador1(acumuladorPuntosJugador1);
    }

    public void puntuacionCifrasJugador2() {
        acumuladorPuntosJugador2 += puntuajeCifras;
        registroPartida.setPuntuacionJugador2(acumuladorPuntosJugador2);
    }

    public void mostrarPuntuacionesJugador1Jugador2() {
        System.out.println("\nPuntuaciones:\n - " + registroPartida.getNombreJugador1()
                + ": " + registroPartida.getPuntuacionJugador1() + " puntos.\n"
                + " - " + registroPartida.getNombreJugador2() + ": "
                + registroPartida.getPuntuacionJugador2() + " puntos.");
    }

    public void escribirResultadosPartida() throws IOException {
        FicherosEscritura ficherosEscritura = new FicherosEscritura(ficheroPartidas);
        ficherosEscritura.escribirFichero(registroPartida.toString());
        ficherosEscritura.escribirSaltoLinea();
        ficherosEscritura.cerrarFichero();
        registroPartida.determinarGanador();
    }

    public void mostrarResultadosPartidas() throws IOException {
        FicherosLectura ficheroLectura = new FicherosLectura(ficheroPartidas);
        String leerFicheroRegistro;
        String campo = "";
        String arrayCampos[] = new String[7];
        int contadorPosiciones = 0;
        int numeroPartidas = 1;
        boolean esNulo = false;

        System.out.println("\n------------------ REGISTRO DE LAS PARTIDAS ------------------\n");

        while (!esNulo) {
            leerFicheroRegistro = ficheroLectura.leerFichero();
            if (leerFicheroRegistro == null) {
                ficheroLectura.cerrarFichero();
                System.out.println("--------------------------------------------------------------");
                esNulo = true;
            } else {
                char aux[] = leerFicheroRegistro.toCharArray();
                for (int i = 0; i < aux.length; i++) {
                    if (aux[i] != '#') {
                        campo += aux[i];
                    }
                    if (aux[i] == '#' || i == aux.length - 1) {
                        arrayCampos[contadorPosiciones] = campo;
                        contadorPosiciones++;
                        campo = "";
                    }
                }
                int puntuacionJugador1 = 0;
                int puntuacionJugador2 = 0;
                // Array auxiliar con la puntuacion del jugador 1
                char arrayAux[] = arrayCampos[5].toCharArray();

                for (int i = 0; i < arrayAux.length; i++) {
                    // Utilizamos una tecnica parecida que en generacionCifrasAleatorias()
                    // para tener la puntuacion del jugador 1 
                    puntuacionJugador1 = puntuacionJugador1 * 10 + (arrayAux[i] - '0');
                }

                arrayAux = arrayCampos[6].toCharArray();
                for (int i = 0; i < arrayAux.length; i++) {
                    puntuacionJugador2 = puntuacionJugador2 * 10 + (arrayAux[i] - '0');
                }

                if (puntuacionJugador1 > puntuacionJugador2) {
                    System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                            + "Modo " + '"' + arrayCampos[1] + '"' + ", " + arrayCampos[4] + " rondas,\n"
                            + "ganador: " + '"' + arrayCampos[2] + '"'
                            + ".\n - Jugador 1 " + '"' + arrayCampos[2] + '"' + ": " + arrayCampos[5] + " puntos.\n"
                            + " - Jugador 2 " + '"' + arrayCampos[3] + '"' + ": " + arrayCampos[6] + " puntos.\n");
                } else {
                    System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                            + "Modo " + '"' + arrayCampos[1] + '"' + ", " + arrayCampos[4] + " rondas,\n"
                            + "ganador: " + '"' + arrayCampos[3] + '"'
                            + ".\n - Jugador 1 " + '"' + arrayCampos[2] + '"' + ": " + arrayCampos[5] + " puntos.\n"
                            + " - Jugador 2 " + '"' + arrayCampos[3] + '"' + ": " + arrayCampos[6] + " puntos.\n");
                }
                numeroPartidas++;
                contadorPosiciones = 0;
            }
        }
    }

    public void mostrarEstadisticasJugador() throws IOException {
        FicherosLectura ficheroLectura = new FicherosLectura(ficheroPartidas);
        String leerFicheroRegistro;
        String campo = "";
        String arrayCampos[] = new String[7];
        int contadorPosiciones = 0;
        int numeroPartidas = 0;
        int numeroPartidasGanadas = 0;
        int puntuacionTotal = 0;
        double porcentajePartidasGanadas = 0;
        double promedioPuntuacion = 0;
        boolean esNulo = false;
        boolean existeElJugador = false;
        boolean nombreValido = false;

        while (!nombreValido) {
            lector.clear();
            System.out.print("Introduce el nombre del jugador: ");
            entradaPorTeclado = lector.llegirLinia();
            // En caso de que el usuario solo escriba un intro
            if (entradaPorTeclado.length == 0) {
                System.err.println("ERROR. No has escrito nada!");
            } else if (entradaPorTeclado[0] == ' ') {
                System.err.println("ERROR. El nombre no puede empezar por espacio!");
            } else {
                nombreValido = true;
            }
        }

        while (!esNulo) {
            leerFicheroRegistro = ficheroLectura.leerFichero();
            if (leerFicheroRegistro == null) {
                ficheroLectura.cerrarFichero();
                if (existeElJugador) {
                    // Si este booleano es true, entonces hemos encontrado al jugador 
                    // en algun momento, por tanto, si existe dentro del fichero
                    porcentajePartidasGanadas = (double) numeroPartidasGanadas / numeroPartidas * 100.0;
                    promedioPuntuacion = (double) puntuacionTotal / numeroPartidas;
                    System.out.println("\n------------------ ESTADISTICAS DEL JUGADOR ------------------\n"
                            + "Total de partidas jugadas: " + numeroPartidas + ".\n"
                            + "Total de partidas ganadas: " + numeroPartidasGanadas + ".\n"
                            + "Porcentaje de partidas ganadas: " + porcentajePartidasGanadas + "%.\n"
                            + "Promedio de puntuacion obtenida por partida: " + promedioPuntuacion + ".\n"
                            + "--------------------------------------------------------------");
                } else {
                    System.err.println("\nERROR. El jugador no existe!");
                    lector.clear();
                }

                esNulo = true;
            } else {
                char aux[] = leerFicheroRegistro.toCharArray();
                for (int i = 0; i < aux.length; i++) {
                    if (aux[i] != '#') {
                        campo += aux[i];
                    }
                    if (aux[i] == '#' || i == aux.length - 1) {
                        arrayCampos[contadorPosiciones] = campo;
                        contadorPosiciones++;
                        campo = "";
                    }
                }
                // Supondremos que en la linea que hemos leido el jugador si esta
                // por facilidad a la hora de programar
                boolean esJugador1 = true;
                char auxNombre1[] = arrayCampos[2].toCharArray();

                // Si no tienen la misma longitud, directamente obviamos que 
                // no es el jugador 1
                if (auxNombre1.length != entradaPorTeclado.length) {
                    esJugador1 = false;
                }

                // Si todos los caracteres coinciden, esJugador1 seguira siendo true
                for (int i = 0; i < auxNombre1.length && esJugador1; i++) {
                    if (entradaPorTeclado[i] != auxNombre1[i]) {
                        esJugador1 = false;
                    }
                }

                boolean esJugador2 = true;
                char auxNombre2[] = arrayCampos[3].toCharArray();

                // Si no tienen la misma longitud, directamente obviamos que 
                // no es el jugador 2
                if (auxNombre2.length != entradaPorTeclado.length) {
                    esJugador2 = false;
                }

                // Si todos los caracteres coinciden, esJugador2 seguira siendo true
                for (int i = 0; i < auxNombre2.length && esJugador2; i++) {
                    if (entradaPorTeclado[i] != auxNombre2[i]) {
                        esJugador2 = false;
                    }
                }

                if (esJugador1 || esJugador2) {
                    System.out.println("\n------------------ PARTIDAS JUGADAS POR ESTE JUGADOR ------------------\n");
                    existeElJugador = true;
                    int puntuacionJugador1 = 0;
                    int puntuacionJugador2 = 0;
                    // Array auxiliar con la puntuacion del jugador 1
                    char arrayAuxPuntuacion[] = arrayCampos[5].toCharArray();

                    for (int i = 0; i < arrayAuxPuntuacion.length; i++) {
                        // Utilizamos una tecnica parecida que en generacionCifrasAleatorias()
                        // para tener la puntuacion del jugador 1 
                        puntuacionJugador1 = puntuacionJugador1 * 10 + (arrayAuxPuntuacion[i] - '0');
                    }

                    // Array auxiliar con la puntuacion del jugador 1
                    arrayAuxPuntuacion = arrayCampos[6].toCharArray();
                    for (int i = 0; i < arrayAuxPuntuacion.length; i++) {
                        puntuacionJugador2 = puntuacionJugador2 * 10 + (arrayAuxPuntuacion[i] - '0');
                    }

                    if ((puntuacionJugador1 > puntuacionJugador2) && esJugador1) {
                        numeroPartidas++;
                        numeroPartidasGanadas++;
                        puntuacionTotal += puntuacionJugador1;
                    } else if (puntuacionJugador1 < puntuacionJugador2 && esJugador1) {
                        numeroPartidas++;
                        puntuacionTotal += puntuacionJugador1;
                    } else if ((puntuacionJugador2 > puntuacionJugador1) && esJugador2) {
                        numeroPartidas++;
                        numeroPartidasGanadas++;
                        puntuacionTotal += puntuacionJugador2;
                    } else if ((puntuacionJugador2 < puntuacionJugador1) && esJugador2) {
                        numeroPartidas++;
                        puntuacionTotal += puntuacionJugador2;
                    } else if ((puntuacionJugador1 == puntuacionJugador2) && esJugador1) {
                        numeroPartidas++;
                        puntuacionTotal += puntuacionJugador1;
                    } else {
                        // Llegara aqui en caso de empate y de ser el jugador 2
                        numeroPartidas++;
                        puntuacionTotal += puntuacionJugador2;
                    }
                    if (puntuacionJugador1 > puntuacionJugador2) {
                        System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                                + "Modo " + '"' + arrayCampos[1] + '"' + ", " + arrayCampos[4] + " rondas,\n"
                                + "ganador: " + '"' + arrayCampos[2] + '"'
                                + ".\n - Jugador 1 " + '"' + arrayCampos[2] + '"' + ": " + arrayCampos[5] + " puntos.\n"
                                + " - Jugador 2 " + '"' + arrayCampos[3] + '"' + ": " + arrayCampos[6] + " puntos.\n");
                    } else {
                        System.out.println("Partida " + numeroPartidas + " (" + arrayCampos[0] + "). "
                                + "Modo " + '"' + arrayCampos[1] + '"' + ", " + arrayCampos[4] + " rondas,\n"
                                + "ganador: " + '"' + arrayCampos[3] + '"'
                                + ".\n - Jugador 1 " + '"' + arrayCampos[2] + '"' + ": " + arrayCampos[5] + " puntos.\n"
                                + " - Jugador 2 " + '"' + arrayCampos[3] + '"' + ": " + arrayCampos[6] + " puntos.\n");
                    }
                }
            }
            contadorPosiciones = 0;
        }
    }

    public void finalPartida() {
        // Solo llega aqui al final de la partida
        System.out.println("\nSe acabo la partida! Muy bien jugado ambos!");
    }

    private int[] agregarNumeroAlArray(int[] arrayOriginal, int nuevoNumero) {
        int[] nuevoArray = new int[arrayOriginal.length + 1];

        // Copiamos el array original
        for (int i = 0; i < arrayOriginal.length; i++) {
            nuevoArray[i] = arrayOriginal[i];
        }
        // Añadimos el numero nuevo al final
        nuevoArray[arrayOriginal.length] = nuevoNumero;
        return nuevoArray;
    }

    private int[] eliminarNumeroDelArray(int[] arrayOriginal, int indiceAEliminar) {
        if (indiceAEliminar < 0 || indiceAEliminar >= arrayOriginal.length) {
            return arrayOriginal; // O error
        }
        int[] nuevoArray = new int[arrayOriginal.length - 1];
        for (int i = 0, j = 0; i < arrayOriginal.length; i++) {
            if (i != indiceAEliminar) {
                nuevoArray[j++] = arrayOriginal[i];
            }
        }
        return nuevoArray;
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.menuPrincipal();
    }
}
