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


    public Registro() {
        this.fechaYHora = LocalDateTime.now();
        this.tipoDePartida = tipoDePartida;
        this.nivelCPU = nivelCPU;
        this.numeroRondas = numeroRondas;
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

    }

 

