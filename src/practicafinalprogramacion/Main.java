package practicafinalprogramacion;

import java.io.IOException;
import java.util.Arrays;
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
                casoTurnoJugadorContraCPU();
                mostrarLetrasDisponibles();

                //Validacion de palabra
                puedeFormarseJugador();
                existeEnDiccionarioJugador();

                //En el caso de que exista, premiar al jugador con puntos
                puntuacionJugador1();

                casoTurnoCPUContraJugador();
                mostrarLetrasDisponibles();
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
        registroPartida.setNombreJugador2("CPU");
        System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
        opcion = lector.llegirEnter();
        while (opcion % 2 != 0) {
            System.err.println("Escribe un numero par de rondas");
            System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            opcion = lector.llegirEnter();
        }
        registroPartida.setNumeroRondas(opcion);
        System.out.println("Ronda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");
    }

    public void casoTurnoJugadorContraCPU() {
        System.out.println("Turno de: " + registroPartida.getNombreJugador1());

    }

    public void casoTurnoCPUContraJugador() {
        System.out.println("Turno de: " + registroPartida.getNombreJugador2());
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
            arrayFichero = lecturaFichero.leerFichero();
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
        Recorremos las letras de la palabra que el jugador ha introducido (copiaLetras).
        Por cada letra, buscamos si existe en el array de letras disponibles.
        Si la encontramos, la “marcamos” sustituyéndola por '*' para que no pueda volver a reutilizarse.
        Si alguna letra no se encuentra, significa que la palabra NO puede formarse con las letras dadas.
         */
        boolean puedeFormarse = true;

        for (int i = 0; i < copiaLetras.length; i++) {
            char letra = copiaLetras[i];
            boolean encontrada = false;
            for (int j = 0; j < copiaLetras.length && !encontrada; j++) {
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
    
    
    public void puedeFormarseCPU() {
         System.out.print("\nIntroduce tu palabra: ");
         
    }
     */
    public void existeEnDiccionarioJugador() throws Exception {
        LineaFicherosLectura ficheroDic = new LineaFicherosLectura(diccionarioEspanyol);

        boolean existeEnDic = false;
        while (ficheroDic.quedanLineas() && !existeEnDic) {
            try {

                Linea lineaDic = ficheroDic.leerFichero();

                if (lineaDic.getNumeroCaracteres() == entradaPorTeclado.length) {
                    boolean iguales = true;
                    int i = 0;

                    while (iguales && i < entradaPorTeclado.length) {
                        if (lineaDic.getCaracter(i) != entradaPorTeclado[i]) {
                            iguales = false;
                        }
                        i++;
                    }

                    if (iguales) {
                        existeEnDic = true;
                    }
                }

            } catch (IOException e) {
                System.err.println("\nERROR. Fichero no encontrado");
            }
        }

        ficheroDic.cerrarFichero();

        // 3. Resultado final
        if (existeEnDic) {
            System.out.println(" - puede crearse con las letras disponibles");
            System.out.println(" - existe en el diccionario");
        } else {
            System.err.println("La palabra NO existe en el diccionario. Intentalo de nuevo!");
            existeEnDiccionarioJugador();
        }
    }

    public void puntuacionJugador1() {
        registroPartida.setPuntuacionJugador1(entradaPorTeclado.length);
        System.out.println("Felicidades. Has ganado " + entradaPorTeclado.length + " puntos!");
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.menuPrincipal();
    }
}
