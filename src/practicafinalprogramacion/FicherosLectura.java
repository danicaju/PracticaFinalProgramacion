package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class FicherosLectura {

    private BufferedReader br;

    public FicherosLectura(String nombreFichero) throws FileNotFoundException, IOException {
        this.br = new BufferedReader(new FileReader(nombreFichero));
    }

    public char[] leerFicheroLinea() throws IOException {

        String total = "";

        String linea = br.readLine();

        if (linea == null) {
            return total.toCharArray();
        }

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c != ' ' && c != '\t') {
                total = total + c;   
            }
            
        }

        return (total).toCharArray();
    }

    public void cerrarFichero() throws IOException {
        br.close();
    }

}
