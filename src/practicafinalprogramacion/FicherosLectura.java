package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class FicherosLectura
{

    private String fichero;
    private BufferedReader br;

    public FicherosLectura(String fichero) throws FileNotFoundException {
        this.fichero = fichero;
        this.br = new BufferedReader(new FileReader(fichero));
    }

    public char[] leerFichero() throws IOException {
    String texto;
    String total = "";

    while ((texto = br.readLine()) != null) {

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            // Comprobamos si NO es un carácter que queremos saltar
            if (caracter != ' ' && caracter != '\t' && caracter != '\n' && caracter != '\r') {
                // Añadimos solo los caracteres válidos
                total = total + caracter;
            }
        }
    }

    return total.toCharArray();
}


    public void cerrarFichero() throws IOException {
        br.close();
    }

    public String getFichero() {
        return fichero;
    }
}
