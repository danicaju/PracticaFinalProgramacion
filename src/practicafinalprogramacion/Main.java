/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinalprogramacion;

import java.io.IOException;
import java.util.Random;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Main {

    LT lector = new LT();
    Registro registroPartida = new Registro();
    int opcion;
    char entradaPorTeclado[];
    char arrayFichero[];
    char caracteresAleatorios[];
    Random random = new Random();
    int numeroRandom;

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
                            LecturaFicheros lecturaFichero = new LecturaFicheros("letras_es.txt");
                            arrayFichero = lecturaFichero.leerFichero("letras_es.txt");
                            for (int i = 0; i < 10; i++) {
                                int indiceAleatorio = random.nextInt(arrayFichero.length);
                                arrayFichero[indiceAleatorio] = caracteresAleatorios[i];
                            }
                            for (int i = 0; i < caracteresAleatorios.length; i++) {
                                System.out.println("Letras disponibles: " + caracteresAleatorios[i]);
                            }
                            
                            System.out.print("Introduce tu palabra: ");
                            entradaPorTeclado = lector.llegirLinia();
                            
                            //Queda por hacer la validacion de la palabra

                        } catch (IOException e) {
                            System.err.println("ERROR. Archivo no encontrado");
                        }

                    }
                    case 2 -> {
                    }
                    case 's' ->
                        menuPrincipal();
                    default -> {
                        System.err.println("ERROR. Introduce una opcion valida");
                        menuPrincipal();
                    }
                }
            }
            case 2 -> {

            }
            case 's' -> {

            }

            default -> {
                System.err.println("ERROR. Introduzca una opcion valida!");
                menuPrincipal();
            }
        }

    }
}
