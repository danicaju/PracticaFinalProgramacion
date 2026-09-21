package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class FicherosLectura implements AutoCloseable {

    private final BufferedReader br;

    public FicherosLectura(String nombreFichero) throws FileNotFoundException, IOException {
        this.br = new BufferedReader(new FileReader(nombreFichero));
    }

    public String leerFichero() throws IOException {
        return br.readLine(); // Si es null, devuelve null automáticamente
    }

    @Override
    public void close() throws IOException {
        if (br != null) {
            br.close();
        }
    }
}
