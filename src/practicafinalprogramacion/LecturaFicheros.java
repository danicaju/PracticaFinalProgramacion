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

    private String fichero;
    private BufferedReader br;
    private static final char separador = ' ';

    public LecturaFicheros(String fichero) throws FileNotFoundException {
        this.fichero = fichero;
        this.br = new BufferedReader(new FileReader(fichero));
    }
    
    public char[] leerFichero(String fichero) throws IOException {
        String texto;
        String total = "";
        char res[];
        while((texto = br.readLine()) != null) {
            total += (separador + texto);
        }
        res=total.toCharArray();
        return res;
    }
    
   

    public String getFichero() {
        return fichero;
    }
    
}
