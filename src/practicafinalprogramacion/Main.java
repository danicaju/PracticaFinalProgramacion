/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinalprogramacion;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Main {

    LT lector = new LT();
    DatosJugadores datosJugador1 = new DatosJugadores();
    DatosJugadores datosJugador2 = new DatosJugadores();
    Registro registroPartida = new Registro();
    int opcion;
    char entradaPorTeclado[];

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
                        datosJugador1.setNombreDelJugador1(entradaPorTeclado);
                        System.out.println("Nombre del jugador 2: CPU.");
                        System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
                        opcion = lector.llegirEnter();
                        if (opcion % 2 != 0) {
                            System.err.println("Escribe un numero par de rondas");
                            System.out.print("Introduce cuantas rondas quieres jugar (numero par): ");
                            opcion = lector.llegirEnter();
                        }
                        registroPartida.setNumeroRondas(opcion);
                        

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
