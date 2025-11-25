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

    //Entero que leera la opcion por teclado que proporcione el usuario
    int opcion;
    //Entrada por teclado que leera como array de caracteres algunos de los datos del usuario
    //como su nombre, tambien la palabra que introduzca en la ronda de letras, etc.
    char entradaPorTeclado[];
    //Usado para la lectura de fichero, guardara el fichero en un array de caracteres
    char arrayFichero[];
    //Numero de caracteres aleatorios de la ronda de letras
    char caracteresAleatorios[] = new char[10];
    //Entero que representa la ronda en la que se encuentran, supondremos que siempre
    //el numero de rondas es >= 1
    int rondaActual = 1;
    // Entero que acumula la puntuación de las rondas del jugador 1
    int acumuladorPuntosJugador1 = 0;
    // Entero que acumula la puntuación de las rondas del jugador 1
    int acumuladorPuntosJugador2 = 0;
    // Entero que acumula la puntuación de las rondas de la CPU
    int acumuladorPuntosCPU = 0;
    //Random usado para leer letras de forma aleatoria del fichero "letras_es.txt"
    Random random = new Random();
    //Strings con los nombres de los ficheros proporcionados
    String ficheroLetras = "letras_es.txt";
    String diccionarioEspanyol = "dic_es.txt";

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

        opcion = lector.llegirCaracter();
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

        opcion = lector.llegirCaracter();
        //Switch si le ha dado al 1 (Jugar)
        switch (opcion) {
            case '1' -> {
                //Jugar contra la CPU
                casoJugarContraCPU();
                casoTurnoJugadorContraCPULetras();
                mostrarLetrasDisponibles();

                //Validacion de palabra
                puedeFormarseJugador();
                existeEnDiccionarioJugador();

                //En el caso de que exista, premiar al jugador con puntos
                puntuacionJugador1();

                //Aqui empieza el turno de la CPU
                casoTurnoCPUContraJugador();
                mostrarLetrasDisponibles();
                puedeFormarseCPU();
                puntuacionCPU();
                
                //
                //Por implementar:
                
                //finalRonda();

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

        opcion = lector.llegirCaracter();
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
        opcion = lector.llegirEnter();
        registroPartida.setNumeroRondas(opcion);
        while (opcion % 2 != 0) {
            System.err.println("Escribe un numero par de rondas");
            System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            opcion = lector.llegirEnter();
        }
    }

    public void casoTurnoJugadorContraCPULetras() {
        System.out.println("Ronda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");

        System.out.println("Turno de: " + registroPartida.getNombreJugador1());

    }

    public void casoTurnoCPUContraJugador() {
        System.out.println("\nTurno de: " + registroPartida.getNombreCPU());
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
            arrayFichero = lecturaFichero.leerFicheroLinea();
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                int indiceAleatorio = random.nextInt(arrayFichero.length);
                caracteresAleatorios[i] = arrayFichero[indiceAleatorio];
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

    public void puedeFormarseJugador() throws Exception {
        System.out.print("\nIntroduce tu palabra: ");
        entradaPorTeclado = lector.llegirLinia();
        System.out.println("Validando palabra...");

        // 1. Comprobar si puede formarse con las letras disponibles
        //Copiamos el array de caracteresAleatorios en otro array 
        //que nos ayudara mas adelante, "copiaLetras"
        char[] copiaLetras = new char[caracteresAleatorios.length];
        for (int i = 0; i < caracteresAleatorios.length; i++) {
            copiaLetras[i] = caracteresAleatorios[i]; // copiar manualmente         
        }

        /*
        Recorremos las letras de la palabra que el jugador ha introducido (entradaPorTeclado)
        Por cada letra, buscamos si existe en el array de letras disponibles (copiaLetras)
        Si la encontramos, la “marcamos” sustituyéndola por '*' para que no pueda volver a reutilizarse.
        Si alguna letra no se encuentra, significa que la palabra NO puede formarse con las letras dadas.
         */
        boolean puedeFormarse = true;

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
            puedeFormarseJugador();

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
        char[] palabraDic;

        /*
        Mientras lo que lea no sea igual a 0 (final de linea), es decir,
        ha devuelto null y el array.length de palabraDic es igual a 0
        entonces que vaya comprobando si la palabraDic puede formarse
        con auxLetras
         */
        while ((palabraDic = ficheroDic.leerFicheroLinea()).length > 0) {

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
            for (int i = 0; i < palabraDic.length && puedeFormarse; i++) {
                char letra = palabraDic[i];
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
                    palabraCPU = palabraDic; // elegimos esta palabra
                }
            }
        }

        ficheroDic.cerrarFichero();

        // Mostrar la palabra elegida por la CPU
        if (palabraCPU != null) {
            System.out.println("\nCPU elige: " + new String(palabraCPU));
            registroPartida.setPuntuacionJugador2(palabraCPU.length);

        } else {
            System.out.println("CPU no pudo formar ninguna palabra.");
        }
    }

    public void existeEnDiccionarioJugador() throws Exception {
        FicherosLectura ficheroDic = new FicherosLectura(diccionarioEspanyol);

        boolean existeEnDic = false;
        while (!existeEnDic) {
            try {
                char array[] = ficheroDic.leerFicheroLinea();
                if (array.length == entradaPorTeclado.length) {
                    boolean iguales = true;
                    int i = 0;

                    while (iguales && i < entradaPorTeclado.length) {
                        if (array[i] != entradaPorTeclado[i]) {
                            iguales = false;
                        }
                        i++;
                    }
                    if (iguales);
                    existeEnDic = true;
                }
            } catch (IOException e) {
                System.err.println("\nERROR. Fichero no encontrado");

            }
        }

        // 3. Resultado final
        if (existeEnDic) {
            System.out.println(" - puede crearse con las letras disponibles");
            System.out.println(" - existe en el diccionario");
            registroPartida.setPuntuacionJugador1(entradaPorTeclado.length);

        } else {
            System.err.println("La palabra NO existe en el diccionario. Intentalo de nuevo!");
            existeEnDiccionarioJugador();
        }
        ficheroDic.cerrarFichero();

    }

    /*
    En estos metodos de puntuacion, usamos 
    una variable acumulador para que vaya acumulando los puntos 
    de todas las rondas que quiera jugar el jugador
     */
    public void puntuacionJugador1() {
        acumuladorPuntosJugador1 += entradaPorTeclado.length;
        System.out.println("Felicidades " + registroPartida.getNombreJugador1() + "! Has ganado " + acumuladorPuntosJugador1 + " puntos!");
    }

    public void puntuacionCPU() {
        acumuladorPuntosCPU += registroPartida.getPuntuacionCPU();
        System.out.println("Felicidades " + registroPartida.getNombreCPU() + "! Has ganado " + acumuladorPuntosCPU + " puntos!");
    }
    
    public void finalRonda() {
        System.out.println("Ronda " + rondaActual + "de" + registroPartida.getNumeroRondas());
        
        if (rondaActual == registroPartida.getNumeroRondas()) {
            System.out.println("Se acabo la partida! Muy bien jugados ambos!");
            registroPartida.determinarGanador();
        } else {
            rondaActual++;
            casoTurnoJugadorContraCPULetras();
        }
    }


    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.menuPrincipal();
    }
}
