/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinalprogramacion;

import java.io.FileNotFoundException;
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
    boolean puedeCrearse = false;
    boolean existeEnDiccionario = false;

    public static void main(String[] args) {
        Main m = new Main();
        m.menuPrincipal();
    }

    public void menuPrincipal() {
        System.out.print("""
                         ************************************
                         MENU PRINCIPAL
                         ************************************
                             1. Jugar
                             2. Registro
                             s. Salir
                         ************************************
                         
                         Opcion(1|2|s): """);

        opcion = lector.llegirEnter();
        switch (opcion) {
            case 1 -> {
                System.out.print("""
                                 
                                 ************************************
                                 JUGAR
                                 ************************************
                                     1. Jugar contra el ordenador
                                     2. Jugar contra otro jugador
                                     s. Volver al menu principal
                                 ************************************
                                 
                                 Opcion (1|2|3|s): """);
                opcion = lector.llegirEnter();
                switch (opcion) {
                    case 1 -> {
                        System.out.println("""
                                           
                                           ************************************
                                           JUGAR CONTRA EL ORDENADOR
                                           ************************************""");

                        System.out.print("\nIntroduce el nombre del jugador: ");
                        entradaPorTeclado = lector.llegirLinia();
                        registroPartida.setNombreDelJugador1(entradaPorTeclado);
                        System.out.println("Nombre del jugador 2: CPU.");
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
                        try {
                            LecturaFicheros lecturaFichero = new LecturaFicheros(ficheroLetras);
                            arrayFichero = lecturaFichero.leerFichero(ficheroLetras);
                            for (int i = 0; i < caracteresAleatorios.length; i++) {
                                int indiceAleatorio = random.nextInt(arrayFichero.length);
                                caracteresAleatorios[i] = arrayFichero[indiceAleatorio];
                            }
                            System.out.println("Letras disponibles: ");
                            for (int i = 0; i < caracteresAleatorios.length; i++) {
                                System.out.print(caracteresAleatorios[i] + " ");
                            }

                            System.out.print("\nIntroduce tu palabra: ");
                            entradaPorTeclado = lector.llegirLinia();
                            System.out.println("Validando palabra...");

                            //Validacion de palabra
                            try {


                                LecturaFicheros lecturaFicheroDiccionario = new LecturaFicheros(diccionarioEspanyol);
                                arrayFichero = lecturaFicheroDiccionario.leerFichero(diccionarioEspanyol);
                                for (int i = 0; i < arrayFichero.length; i++) {
                                    System.out.print(arrayFichero[i]);
                                }

                                int indice1 = 0;
                                int indice2 = 0;
                                while (!existeEnDiccionario) {
                                    for (indice1 = 0; indice1 < arrayFichero.length; indice1++) {
                                        for (indice2 = 0; indice2 < entradaPorTeclado.length; indice2++) {
                                            if (entradaPorTeclado[indice2] != arrayFichero[indice1]) {
                                                indice2 = 0;
                                            }
                                        }
                                    }

                                    System.out.println(indice1);
                                    if (indice2 == indice1) {
                                        existeEnDiccionario = true;
                                    }
                                }

                                if (existeEnDiccionario) {
                                    System.out.println(" -puede crearse con las letras disponibles ✓\n -existe en el diccionario ✓");

                                } else {
                                    System.out.println("Palabra no valida! Intentalo de nuevo");
                                }

                            } catch (IOException e) {
                                System.err.println("ERROR. Fichero no encontrado");
                            }

                        } catch (IOException e) {
                            System.err.println("ERROR. Fichero no encontrado");
                        }

                    }

                }
            }
        }
    }
}
