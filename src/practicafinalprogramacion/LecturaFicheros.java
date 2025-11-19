/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class LecturaFicheros {

    String fichero;
    BufferedReader br;

    public LecturaFicheros(String fichero) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(fichero));
    }
    
    public void leerFichero(String fichero) throws IOException {
        String texto;
        while((texto = br.readLine()) != null) {
            char res[] = texto.toCharArray();
        }
    }

    public String getFichero() {
        return fichero;
    }
    
}
