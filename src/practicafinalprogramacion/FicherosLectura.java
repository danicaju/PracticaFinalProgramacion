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

    private final BufferedReader br;

    public FicherosLectura(String nombreFichero) throws FileNotFoundException, IOException {
        this.br = new BufferedReader(new FileReader(nombreFichero));
    }

    public String leerFichero() throws IOException {

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
