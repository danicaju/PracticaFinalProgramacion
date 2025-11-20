/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinalprogramacion;

import java.time.LocalDateTime;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Registro {

    LocalDateTime fechaYHora;
    int tipoDePartida;
    int nivelCPU;
    int numeroRondas;  
    char[] nombreDelJugador1;
    char[] nombreDelJugador2;
    int puntuacionJugador1;
    int puntuacionJugador2;
    
    


    public Registro() {
        this.fechaYHora = LocalDateTime.now();
        this.tipoDePartida = tipoDePartida;
        this.nivelCPU = nivelCPU;
        this.numeroRondas = numeroRondas;
        this.nombreDelJugador1 = nombreDelJugador1;
        this.nombreDelJugador2 = nombreDelJugador2;
        this.puntuacionJugador1 = puntuacionJugador1;
        this.puntuacionJugador2 = puntuacionJugador2;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public void setTipoDePartida(int tipoDePartida) {
        this.tipoDePartida = tipoDePartida;
    }

    public void setNivelCPU(int nivelCPU) {
        this.nivelCPU = nivelCPU;
    }

    public void setNumeroRondas(int numeroRondas) {
        this.numeroRondas = numeroRondas;
    }
    
       public void setNombreDelJugador1(char[] nombreDelJugador1) {
        this.nombreDelJugador1 = nombreDelJugador1;
    }

    public void setNombreDelJugador2(char[] nombreDelJugador2) {
        this.nombreDelJugador2 = nombreDelJugador2;
    }

    public void setPuntuacionJugador1(int puntuacionJugador1) {
        this.puntuacionJugador1 = puntuacionJugador1;
    }

    public void setPuntuacionJugador2(int puntuacionJugador2) {
        this.puntuacionJugador2 = puntuacionJugador2;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public int getTipoDePartida() {
        return tipoDePartida;
    }

    public int getNivelCPU() {
        return nivelCPU;
    }

    public int getNumeroRondas() {
        return numeroRondas;
    }

    public char[] getNombreDelJugador1() {
        return nombreDelJugador1;
    }

    public char[] getNombreDelJugador2() {
        return nombreDelJugador2;
    }

    public int getPuntuacionJugador1() {
        return puntuacionJugador1;
    }

    public int getPuntuacionJugador2() {
        return puntuacionJugador2;
    }

    
    }

 

