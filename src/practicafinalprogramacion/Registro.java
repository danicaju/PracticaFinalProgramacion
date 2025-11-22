package practicafinalprogramacion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Registro {

    private LocalDateTime fechaHora;
    private int totalPartidas;
    String tipoPartida;
    private int nivelCPU;
    private int numeroRondas;
    String nombreJugador1;
    String nombreJugador2;
    private int puntuacionJugador1;
    private int puntuacionJugador2;
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Registro() {
        this.fechaHora = LocalDateTime.now();
        this.totalPartidas = totalPartidas = 1;
        this.tipoPartida = tipoPartida;
        this.nivelCPU = nivelCPU;
        this.numeroRondas = numeroRondas;
        this.nombreJugador1 = nombreJugador1;
        this.nombreJugador2 = nombreJugador2;
        this.puntuacionJugador1 = puntuacionJugador1;
        this.puntuacionJugador2 = puntuacionJugador2;

    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setTipoPartida(String tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public void setNivelCPU(int nivelCPU) {
        this.nivelCPU = nivelCPU;
    }

    public void setNumeroRondas(int numeroRondas) {
        this.numeroRondas = numeroRondas;
    }

    public void setNombreJugador1(String nombreDelJugador1) {
        this.nombreJugador1 = nombreDelJugador1;
    }

    public void setNombreJugador2(String nombreDelJugador2) {
        this.nombreJugador2 = nombreDelJugador2;
    }

    public void setPuntuacionJugador1(int puntuacionJugador1) {
        this.puntuacionJugador1 = puntuacionJugador1;
    }

    public void setPuntuacionJugador2(int puntuacionJugador2) {
        this.puntuacionJugador2 = puntuacionJugador2;
    }

    public void determinarGanador() {
        if (puntuacionJugador1 > puntuacionJugador2) {
            System.out.print("Ha ganado el jugador " + getNombreJugador1() + "!\n");
        } else if (puntuacionJugador1 < puntuacionJugador2) {
            System.out.print("Ha ganado el jugador " + getNombreJugador2() + "!\n");
        } else {
            System.out.print("El jugador " + getNombreJugador1() + " y el jugador " + getNombreJugador2()
                    + " han empatado!\n");
        }
    }

    public String getFechaHoraFormateada() {
        return fechaHora.format(formato);
    }

    public String getTipoPartida() {
        return tipoPartida;
    }

    public int getNivelCPU() {
        return nivelCPU;
    }

    public int getNumeroRondas() {
        return numeroRondas;
    }

    public String getNombreJugador1() {
        return nombreJugador1;
    }

    public String getNombreJugador2() {
        return nombreJugador2;
    }

    public int getPuntuacionJugador1() {
        return puntuacionJugador1;
    }

    public int getPuntuacionJugador2() {
        return puntuacionJugador2;
    }

    public int getTotalPartidas() {
        return totalPartidas;
    }
    
    public String toString() {
        return getFechaHoraFormateada() + "#" + getTipoPartida() + "#" + getNombreJugador1() + "#" + getNombreJugador2() + "#" + getNivelCPU()
                + "#" + getNumeroRondas() + "#" + getPuntuacionJugador1() + "#" + getPuntuacionJugador2();

    }

}
