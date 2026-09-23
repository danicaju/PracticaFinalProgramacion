package practicafinalprogramacion;

/*
AUTOR: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Jugador {

    private String nombre;
    private int puntuacion;
    private boolean esCpu;

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

    // Encapsulamiento puro: el jugador se suma los puntos a sí mismo
    public void sumarPuntos(int puntosExtra) {
        this.puntuacion += puntosExtra;
    }

    public boolean isCpu() {
        return esCpu;
    }
}
