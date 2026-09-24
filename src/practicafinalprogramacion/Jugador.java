package practicafinalprogramacion;

/**
 * Representa a un jugador en el juego, ya sea humano o CPU.
 * 
 * AUTOR: Maria Teresa Sbert Gomila y Daniel Casado Juan
 * GRUPO: 2
 */
public class Jugador {

    private final String nombre;
    private int puntuacion;
    private final boolean esCpu;

    /**
     * Constructor del jugador.
     * @param nombre Nombre del jugador
     * @param esCpu Indica si el jugador es controlado por la computadora
     */
    public Jugador(String nombre, boolean esCpu) {
        this.nombre = nombre;
        this.esCpu = esCpu;
        this.puntuacion = 0; // Siempre empiezan con 0
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Añade puntos a la puntuación actual del jugador.
     * @param puntosExtra Puntos a sumar
     */
    public void sumarPuntos(int puntosExtra) {
        this.puntuacion += puntosExtra;
    }

    public boolean isCpu() {
        return esCpu;
    }
}
