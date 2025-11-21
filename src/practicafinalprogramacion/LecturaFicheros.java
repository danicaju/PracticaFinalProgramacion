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

    public char[] leerFichero() throws IOException {
        String texto;
        String total = "";
        while ((texto = br.readLine()) != null) {
            if (texto == "\n") {
                texto = " ";
            }
            total += texto;
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
