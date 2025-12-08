package practicafinalprogramacion;

import java.io.IOException;
import java.util.Random;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Main {

    //Inicializaciones de objeto LT y objeto Registro
    LT lector = new LT();
    Registro registroPartida = new Registro();
    //Entrada por teclado que leera como array de caracteres algunos de los datos del usuario
    //como su nombre, tambien la palabra que introduzca en la ronda de letras, etc.
    char entradaPorTeclado[];
    //Numero de caracteres aleatorios de la ronda de letras
    char caracteresAleatorios[] = new char[10];
    //Numero de cifras aleatorias de la ronda de cifras
    int cifrasAleatorias[] = new int[6];
    //Entero que representa la ronda en la que se encuentran, supondremos que siempre
    //el numero de rondas es >= 1
    int rondaActual = 1;
    // Entero que acumula la puntuación de las rondas del jugador 1
    int acumuladorPuntosJugador1 = 0;
    // Entero que acumula la puntuación de las rondas del jugador 1
    int acumuladorPuntosJugador2 = 0;
    // Entero que acumula la puntuación de las rondas de la CPU
    int acumuladorPuntosCPU = 0;
    // Booleano que determinara si el usuario ha pasado su turno
    boolean haPasado = false;
    //Strings con los nombres de los ficheros proporcionados
    String ficheroLetras = "letras_es.txt";
    String diccionarioEspanyol = "dic_es.txt";
    String ficheroCifras = "cifras.txt";

    public void menuPrincipal() throws Exception {
        System.out.print("""
                         ************************************
                         MENU PRINCIPAL
                         ************************************
                             1. Jugar
                             2. Registro
                             s. Salir
                         ************************************
                         
                         Opcion(1|2|s): """);

        int opcion = lector.llegirCaracter();
        //Switch del menuPrincipal
        switch (opcion) {
            case '1' -> {
                opcionJugar();
            }
            case '2' -> {
                opcionRegistro();
            }
            default -> {
                if (opcion == 's') {
                    System.out.println("Saliendo... Gracias por Jugar a Cifras y Letras!");
                } else {
                    System.err.println("\nERROR. Introduce una opcion valida!\n");
                    menuPrincipal();
                }
            }
        }
    }

    //METODOS PRINCIPALES
    public void opcionJugar() throws Exception {
        System.out.print("""
                                 
                                 ************************************
                                 JUGAR
                                 ************************************
                                     1. Jugar contra el ordenador
                                     2. Jugar contra otro jugador
                                     s. Volver al menu principal
                                 ************************************
                                 
                                 Opcion (1|2|3|s): """);

        int opcion = lector.llegirCaracter();
        //Switch si le ha dado al 1 (Jugar)
        switch (opcion) {
            case '1' -> {
                while (rondaActual <= registroPartida.getNumeroRondas()) {
                    //Jugar contra la CPU
                    casoJugarContraCPU();
                    casoTurnoLetrasJugadorContraCPU();
                    mostrarLetrasDisponibles();

                    //Validacion de palabra
                    puedeFormarseJugador();
                    if (!haPasado) {
                        existeEnDiccionarioJugador();
                        //En el caso de que exista, premiar al jugador con puntos
                        puntuacionLetrasJugador1();
                    } else {
                        jugador1PasaTurno();
                    }
                    //Aqui empieza el turno de la CPU
                    casoTurnoCPUContraJugador();
                    mostrarLetrasDisponibles();
                    puedeFormarseCPU();
                    puntuacionLetrasCPU();
                    //Reinicio haPasado en el caso de que sea true, para que
                    //en la siguiente ronda, el usuario no pase directamente
                    haPasado = false;
                    rondaActual++;

                    casoTurnoCifrasJugadorContraCPU();
                    mostrarCifrasDisponibles();
                    operacionesCifras();

                    //TEMPORAL: ESTO TIENE QUE IR ABAJO DE MAS
                    //METODOS CUANDO LA CPU ACABE RONDA DE CIFRAS
                    rondaActual++;
                }
                //Quedan metodos por implementar
            }
            case '2' -> {
                //Jugar contra otra persona (por hacer)
                //casoTurnoJugador1ContraJugador2
                //casoTurnoJugador2ContraJugador1
            }

            case 's' -> {
                menuPrincipal();
            }

            default -> {
                System.err.println("\nERROR. Introduce una opcion valida!");
                opcionJugar();
            }

        }
    }

    public void opcionRegistro() throws Exception {
        System.out.print("""    
                         
                                   ************************************
                                   REGISTRO
                                   ************************************
                                       1. Mostrar resultados de las partidas
                                       2. Mostrar estadisticas de un jugador
                                       s. Volver al menu principal
                
                                   Opcion (1|2|3|s): """);

        int opcion = lector.llegirCaracter();
        switch (opcion) {
            case '1' -> {
                mostrarResultadosPartida();
            }
            case '2' -> {
                //por implementar:
                //mostrarEstadisticasJugador();
            }
            case 's' -> {
                System.out.println();
                menuPrincipal();
            }
            default -> {
                System.err.println("\nERROR. Introduce una opcion valida!");
                opcionRegistro();
            }
        }
    }

    public void casoJugarContraCPU() {
        System.out.println("""
                                           
                                           ************************************
                                           JUGAR CONTRA EL ORDENADOR
                                           ************************************""");

        System.out.print("\nIntroduce el nombre del jugador: ");
        entradaPorTeclado = lector.llegirLinia();
        //Comentar esto mas adelante
        String aux = new String(entradaPorTeclado);
        registroPartida.setNombreJugador1(aux);
        System.out.println("Nombre del jugador 2: CPU.");
        registroPartida.setNombreCPU("CPU");
        System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
        int opcion = lector.llegirEnter();
        registroPartida.setNumeroRondas(opcion);
        while (opcion % 2 != 0) {
            System.err.println("Escribe un numero par de rondas");
            System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            opcion = lector.llegirEnter();
        }
    }

    public void casoTurnoLetrasJugadorContraCPU() {
        System.out.println("Ronda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");

        System.out.println("Turno de: " + registroPartida.getNombreJugador1());
    }

    public void casoTurnoCifrasJugadorContraCPU() {
        System.out.println("Ronda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": cifras.");

        System.out.println("Turno de: " + registroPartida.getNombreJugador1());
    }

    public void casoTurnoCPUContraJugador() {
        System.out.println("\nTurno de: " + registroPartida.getNombreCPU());
    }

    public void jugador1PasaTurno() {
        System.out.println("Has pasado!");
    }

    public void mostrarResultadosPartida() {
        System.out.print("\n************************************\nDETALLES DE LAS PARTIDAS\n************************************\nPartida "
                + registroPartida.getTotalPartidas() + " (" + registroPartida.getFechaHoraFormateada() + "). Modo "
                + registroPartida.getTipoPartida() + ", " + registroPartida.getNumeroRondas() + " rondas,\n"
                + "ganador: ");
        registroPartida.determinarGanador();
    }

    public void mostrarLetrasDisponibles() {
        try {
            FicherosLectura lecturaFichero = new FicherosLectura(ficheroLetras);
            String lecturaLetrasDisponibles;
            lecturaLetrasDisponibles = lecturaFichero.leerFicheroLetras();
            Random random = new Random();

            char[] arrayFicheroLetras = lecturaLetrasDisponibles.toCharArray();
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

    public void mostrarCifrasDisponibles() throws IOException {
        FicherosLectura ficheroDeCifras = new FicherosLectura(ficheroCifras);
        String lectura;
        char arrayLectura[];
        int num = 0;
        int j = 0;
        int k = 0;
        //Inicializo un array con el tamaño necesario para los 24
        //numeros del fichero
        int arrayAux[] = new int[24];
        Random random = new Random();

        //Uso un String para leer el fichero, al haber solo una linea en el fichero
        //no hace falta que ejecute un bucle while de lectura, simplemente una
        //sola instruccion y convierto el String "lectura" a array de caracteres
        //"arrayLectura"
        lectura = ficheroDeCifras.leerFicheroCifras();
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
        for (int i = 0; i < arrayLectura.length; i++) {

            if (arrayLectura[i] != ' ') { //Si es un numero
                num = num * 10 + (arrayLectura[i] - '0');
            } else {
                arrayAux[j] = num;
                j++;
                num = 0;
            }
        }

        /*
        Mediante un bucle for desde 0 hasta el tamaño del "arrayAux", genero numeros
        aleatorios "randomAux" desde 0 hasta el  arrayAux.length - 1, asi mientras
        un indice k < cifrasAleatorias.length (hasta llenar cifrasAleatorias), 
        el valor de  cifrasAleatorias sera igual a alguna posicion aleatoria
        del "arrayAux" (que contiene todos los numeros del fichero)     
         */
        for (int i = 0; i < arrayAux.length; i++) {
            int randomAux = random.nextInt(0, arrayAux.length - 1);
            if (k < cifrasAleatorias.length) {
                cifrasAleatorias[k] = arrayAux[randomAux];
                k++;
            }
        }
    }

    public void operacionesCifras() {
        boolean hacerOperaciones = true;
        Random objRandom = new Random();
        int objetivo = objRandom.nextInt(100, 999);
        int numOperacion = 1;
        //Este entero solo podra ser uno o dos, ya que
        //siempre tendremos solo dos operandos por 
        //operacion
        int numOperando = 1;

        while (hacerOperaciones) {
            System.out.print("Cifras disponibles: ");
            for (int i = 0; i < cifrasAleatorias.length; i++) {
                System.out.print(cifrasAleatorias[i] + " ");
            }
            System.out.println("\nObjetivo: " + objetivo);
            System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
            char tipoOperacion = lector.llegirCaracter();
            //Mientras no sea una de las operaciones validas, dara error y se pedira
            //al usuario que introduzca una operacion entre las proporcionadas
            while (tipoOperacion != '+' && tipoOperacion != '-' && tipoOperacion != '*'
                    && tipoOperacion != '/' && tipoOperacion != '=') {
                System.err.println("ERROR. Introduce una operacion valida!");
                System.out.print("Operacion " + numOperacion + " (+|-|*|/|=): ");
                tipoOperacion = lector.llegirCaracter();
            }
            //Si ha llegado hasta aqui, la operacion que 
            //ha elegido el usuario es valida y 
            //incrementamos de forma adelantada ya que la 
            //proxima operacion que esperamos es la segunda
            numOperacion++;

            //Este System,out.println() y todo el bucle for
            //es para que se vea de la manera que queremos
            //por pantalla
            System.out.print("Operando " + numOperando + " (");
            //Primer bucle for para el 1r operando
            for (int j = 0; j < cifrasAleatorias.length; j++) {
                if (j == cifrasAleatorias.length - 1) {
                    System.out.print(cifrasAleatorias[j] + "): ");
                } else {
                    System.out.print(cifrasAleatorias[j] + " ");
                }
            }
            //Se guarda el primer operando del usuario
            int operando1 = lector.llegirEnter();

            //Booleano que usaremos para determinar si el operando
            //que ha elegido el usuario existe entre las
            //cifras aleatorias
            boolean existeElOperando1 = false;

            //Supondremos que el operando que ha puesto el usuario
            //es erroneo por facilidad a la hora de programar
            //y miraremos lo antes posible si es correcto
            //o no mirando si existe entre cifrasAleatorias      
            while (!existeElOperando1) {
                //Si en todas las cifras aleatorias, encuentra
                //alguna que sea igual que el operando del
                //usuario entonces, existe el operando
                for (int l = 0; l < cifrasAleatorias.length && !existeElOperando1; l++) {
                    if (operando1 == cifrasAleatorias[l]) {
                        existeElOperando1 = true;
                    }
                }
                //Si no existiera, se llevaria a cabo un bucle 
                //constante hasta que el usuario escribiera
                //un operando valido
                if (!existeElOperando1) {
                    System.err.println("ERROR. Escribe un operando valido!");
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
            }

            //Inicializaremos esta variable int "indiceAEliminar" con el valor
            //de -1, asi luego la usaremos para comprobar si el opearando
            //escrito por el usuario existe entre el array (tiene indice),
            //logicamente si por la comprobacion anterior pero esta hecho 
            //para refuerzo de errores
            int indiceAEliminar = -1;
            boolean indiceEncontrado = false;

            //Busco cual es la cifra que introduzco el usuario y cuando
            //lo encuentre, guardo el indice de esa cifra
            for (int i = 0; i < cifrasAleatorias.length && !indiceEncontrado; i++) {
                if (cifrasAleatorias[i] == operando1) {
                    indiceAEliminar = i;
                    indiceEncontrado = true;
                }
            }

            //Si el indice a eliminar es distinto de -1 (hemos encontrado la cifra),
            //entonces en el nuevoArrayAux[] que es del tamaño de
            //cifrasAleatorias.length - 1, guardaremos todo 
            //menos el numero en ese indice
            if (indiceAEliminar != -1) {
                int[] nuevoArrayAux = new int[cifrasAleatorias.length - 1];
                for (int i = 0, j = 0; i < cifrasAleatorias.length; i++) {
                    if (i != indiceAEliminar) {
                        //Si el indice 'i' no es el mismo que el indice
                        //a eliminar, entonces rellenamos de forma
                        //normal el nuevoArrayAux
                        nuevoArrayAux[j++] = cifrasAleatorias[i];
                    }
                }
                cifrasAleatorias = nuevoArrayAux;
            }

            numOperando++;

            //Segundo bucle for para el 2n operando
            System.out.print("Operando " + numOperando + " (");
            for (int j = 0; j < cifrasAleatorias.length; j++) {
                if (j == cifrasAleatorias.length - 1) {
                    System.out.print(cifrasAleatorias[j] + "): ");
                } else {
                    System.out.print(cifrasAleatorias[j] + " ");
                }
            }

            int operando2 = lector.llegirEnter();
            //Booleano que usaremos para determinar si el operando
            //que ha elegido el usuario existe entre las
            //cifras aleatorias
            boolean existeElOperando2 = false;
            //Supondremos que el operando que ha puesto el usuario
            //es erroneo por facilidad a la hora de programar
            //y miraremos lo antes posible si es correcto
            //o no mirando si existe entre cifrasAleatorias
            while (!existeElOperando2) {
                //Si en todas las cifras aleatorias, encuentra
                //alguna que sea igual que el operando del
                //usuario entonces, existe el operando
                for (int i = 0; i < cifrasAleatorias.length && !existeElOperando2; i++) {
                    if (operando2 == cifrasAleatorias[i]) {
                        existeElOperando2 = true;
                    }
                }
                //Si no existiera, se llevaria a cabo un bucle 
                //constante hasta que el usuario escribiera
                //un operando valido
                if (!existeElOperando2) {
                    System.err.println("ERROR. Escribe un operando valido!");
                    System.out.print("Operando " + numOperando + " (");
                    for (int i = 0; i < cifrasAleatorias.length; i++) {
                        if (i == cifrasAleatorias.length - 1) {
                            System.out.print(cifrasAleatorias[i] + "): ");
                        } else {
                            System.out.print(cifrasAleatorias[i] + " ");
                        }
                    }
                    operando2 = lector.llegirEnter();
                }
                System.out.println();
            }
            int indiceAEliminar2 = -1;
            boolean indiceEncontrado2 = false;
            //Si llego aqui, he salido del bucle, y por tanto, el operando si existe

            //Busco cual es la cifra que introduzco el usuario y cuando
            //lo encuentre, guardo el indice de esa cifra
            for (int i = 0; i < cifrasAleatorias.length && !indiceEncontrado2; i++) {
                if (cifrasAleatorias[i] == operando2) {
                    indiceAEliminar2 = i;
                    indiceEncontrado2 = true;
                }
            }

            //Si el indice a eliminar es distinto de -1 (hemos encontrado la cifra),
            //entonces en el nuevoArrayAux[] que es del tamaño de
            //cifrasAleatorias.length - 1, guardaremos todo 
            //menos el numero en ese indice
            if (indiceAEliminar2 != -1) {
                int[] nuevoArrayAux2 = new int[cifrasAleatorias.length - 1];
                for (int i = 0, j = 0; i < cifrasAleatorias.length; i++) {
                    if (i != indiceAEliminar2) {
                        //Si el indice 'i' no es el mismo que el indice
                        //a eliminar, entonces rellenamos de forma
                        //normal el nuevoArrayAux
                        nuevoArrayAux2[j++] = cifrasAleatorias[i];
                    }
                }
                cifrasAleatorias = nuevoArrayAux2;
            }
            numOperando--;

            switch (tipoOperacion) {
                case '+' -> {
                    int resultado = operando1 + operando2;
                }

                case '-' -> {
                    int resultado = operando1 - operando2;
                }

                case '*' -> {
                    int resultado = operando1 * operando2;
                }

                case '/' -> {
                    //Esto da problemas ya que encogemos el array 
                    //antes de comprobar si se puede hacer o no
                    //la division entera FALTA ARREGLAR ESTO
                    if (operando1 % operando2 == 0) {
                        int resultado = operando1 * operando2;
                    } else {
                        System.err.println("ERROR. La division no es entera!");
                        operacionesCifras();
                    }
                }
                case '=' -> {
                    //POR IMPLEMENTAR
                }
                default -> {
                    System.err.println("ERROR. Escribe un operando valido!");
                }
            }
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
                char[] copiaLetras = new char[caracteresAleatorios.length];
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
                    System.err.println("\nLa palabra NO puede formarse con las letras disponibles! Intentalo de nuevo.");
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
        char[] palabraCPU = null;
        int contador = 0;

        FicherosLectura ficheroDic = new FicherosLectura(diccionarioEspanyol);
        String palabraDic;

        /*
        Mientras lo que lea no sea igual a 0 (final de linea), es decir,
        ha devuelto null y el array.length de palabraDic es igual a 0
        entonces que vaya comprobando si la palabraDic puede formarse
        con auxLetras
         */
        while ((palabraDic = ficheroDic.leerFicheroLetras()) != null) {

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
            char[] palabraDicArray = palabraDic.toCharArray();
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
            registroPartida.setPuntuacionCPU(palabraCPU.length);
        } else {
            System.out.println("CPU no pudo formar ninguna palabra.");
        }
    }

    public void existeEnDiccionarioJugador() throws Exception {

        boolean palabraValida = false;

        while (!palabraValida) {
            boolean existeEnDic = false;
            FicherosLectura ficheroDic = new FicherosLectura(diccionarioEspanyol);
            String lineaDic;

            while ((lineaDic = ficheroDic.leerFicheroLetras()) != null && !existeEnDic) {
                char[] lineaDicArray = lineaDic.toCharArray();
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
        registroPartida.setPuntuacionJugador1(entradaPorTeclado.length);
        System.out.println("Felicidades " + registroPartida.getNombreJugador1() + "! Has ganado " + entradaPorTeclado.length + " puntos!");
    }

    public void puntuacionLetrasCPU() {
        int puntosRonda = registroPartida.getPuntuacionCPU();
        acumuladorPuntosCPU += puntosRonda;
        System.out.println("Felicidades " + registroPartida.getNombreCPU() + "! Has ganado " + puntosRonda + " puntos!\n");
    }

    public void finalPartida() {
        // Solo llega aqui al final de la partida
        System.out.println("Se acabo la partida! Muy bien jugado ambos!");
        registroPartida.determinarGanador();
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.menuPrincipal();
    }
}
