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

    public String leerFicheroLetras() throws IOException {

        String linea = br.readLine();
        String total = "";

        if (linea == null) {
            return null;
        }

        char aux[] = linea.toCharArray();
        for (int i = 0; i < aux.length; i++) {
            char c = aux[i];

            if (c != ' ' && c != '\t') {
                total = total + c;
            }
        }

        return total;
    }

    public String leerFicheroCifras() throws IOException {
        String linea = br.readLine();

        if (linea == null) {
            return null;
        }

        return linea;
    }

    public void cerrarFichero() throws IOException {
        br.close();

    }
}
