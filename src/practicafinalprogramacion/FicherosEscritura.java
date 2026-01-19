package practicafinalprogramacion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class FicherosEscritura {

    private final BufferedWriter bw;

    public FicherosEscritura(String fichero) throws IOException {
        this.bw = new BufferedWriter(new FileWriter(fichero, true));
    }

    public void escribirFichero(String parametroAEscribir) throws IOException {
        bw.write(parametroAEscribir);
    }

    public void escribirSaltoLinea() throws IOException {
        bw.newLine();
    }

    public void cerrarFichero() throws IOException {
        bw.close();
    }
}
