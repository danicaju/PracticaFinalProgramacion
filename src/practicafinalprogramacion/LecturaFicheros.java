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
    
    public char[] leerFichero(String fichero) throws IOException {
        String texto;
        char res[] = null;
        while((texto = br.readLine()) != null) {
            res = texto.toCharArray();
        }
        return res;
    }

    public String getFichero() {
        return fichero;
    }
    
}
