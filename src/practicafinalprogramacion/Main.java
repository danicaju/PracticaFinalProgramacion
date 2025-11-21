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
    //Random usado para leer letras de forma aleatoria del fichero "letras_es.txt"
    Random random = new Random();
    //Strings con los nombres de los ficheros proporcionados
    String ficheroLetras = "letras_es.txt";
    String diccionarioEspanyol = "dic_es.txt";
    boolean puedeFormarse = false;
    boolean existeEnDiccionario = false;
    boolean encontrada = false;
    Linea linea = new Linea();

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

                //Switch si le ha dado al 1 (Jugar)
                switch (opcion) {
                    case '1' -> {
                        //Jugar contra la CPU
                        casoJugarContraCPU();

                        //Validacion de palabra
                        validarPalabra();

                    }
                    case '2' -> {
                        //Jugar contra otra persona (por hacer)
                        //casoJugarContraJugador()
                    }

                    case 's' -> {
                        menuPrincipal();
                    }

                    default -> {
                        System.err.println("\nERROR. Introduce una opcion valida!\n");
                        opcionRegistro();
                    }

                }
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
    public void opcionJugar() {
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
        registroPartida.setNombreJugador1(entradaPorTeclado);
        System.out.println("Nombre del jugador 2: CPU.");
        registroPartida.setNombreJugador2("CPU".toCharArray());
        System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
        opcion = lector.llegirEnter();
        while (opcion % 2 != 0) {
            System.err.println("Escribe un numero par de rondas");
            System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
            opcion = lector.llegirEnter();
        }
        registroPartida.setNumeroRondas(opcion);
        int rondaActual = 1;
        System.out.println("Ronda " + rondaActual + " de " + registroPartida.getNumeroRondas() + ": letras.");
        mostrarLetrasDisponibles();
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
            LecturaFicheros lecturaFichero = new LecturaFicheros(ficheroLetras);
            arrayFichero = lecturaFichero.leerFichero();
            lecturaFichero.cerrarFichero();
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                int indiceAleatorio = random.nextInt(arrayFichero.length);
                caracteresAleatorios[i] = arrayFichero[indiceAleatorio];
            }
            System.out.println("Letras disponibles: ");
            for (int i = 0; i < caracteresAleatorios.length; i++) {
                System.out.print(caracteresAleatorios[i] + " ");
            }

        } catch (IOException e) {
            System.err.println("\nERROR. Fichero no encontrado\n");
        }
    }

    public void validarPalabra() throws Exception {

        System.out.print("\nIntroduce tu palabra: ");
        entradaPorTeclado = lector.llegirLinia();
        System.out.println("Validando palabra...");

        // 1. Comprobar si puede formarse con las letras disponibles
        char[] copiaLetras = new char[caracteresAleatorios.length];
        for (int i = 0; i < caracteresAleatorios.length; i++) {
            copiaLetras[i] = caracteresAleatorios[i]; // copiar manualmente
        }

        puedeFormarse = true;

        for (int i = 0; i < entradaPorTeclado.length; i++) {
            char letra = entradaPorTeclado[i];
            boolean encontrada = false;
            int j = 0;

            while (!encontrada && j < copiaLetras.length) {
                if (copiaLetras[j] == letra) {
                    copiaLetras[j] = '*'; // marcar como usada
                    encontrada = true;
                } else {
                    j++;
                }
            }

            if (!encontrada) {
                puedeFormarse = false;
            }
        }

        if (!puedeFormarse) {
            System.err.println("La palabra NO puede formarse con las letras disponibles! Inténtalo de nuevo.");
            validarPalabra(); // volver a pedir palabra
            return;
        }

        // 2. Buscar palabra en diccionario
        LineaFicherosLectura ficheroDic = new LineaFicherosLectura(diccionarioEspanyol);
        boolean encontradaEnDiccionario = false;

        while (ficheroDic.quedanLineas() && !encontradaEnDiccionario) {
            Linea lineaDic = ficheroDic.leerFichero();

            if (lineaDic.getNumeroCaracteres() == entradaPorTeclado.length) {
                boolean iguales = true;
                int k = 0;

                while (iguales && k < entradaPorTeclado.length) {
                    if (lineaDic.getCaracter(k) != entradaPorTeclado[k]) {
                        iguales = false;
                    }
                    k++;
                }

                if (iguales) {
                    encontradaEnDiccionario = true;
                }
            }
        }

        ficheroDic.cerrarFichero();

        // 3. Resultado final
        if (encontradaEnDiccionario) {
            System.out.println(" - puede crearse con las letras disponibles");
            System.out.println(" - existe en el diccionario");
        } else {
            System.err.println("La palabra NO existe en el diccionario. Intentalo de nuevo! ");
            validarPalabra();
        }
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.menuPrincipal();
    }
}
