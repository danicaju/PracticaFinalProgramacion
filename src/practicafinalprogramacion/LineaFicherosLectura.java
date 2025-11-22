package practicafinalprogramacion;

import java.io.BufferedReader;
import java.io.FileReader;

public class LineaFicherosLectura {

    private BufferedReader fichero;
    private int codigoLeido;

    public LineaFicherosLectura(String nombre) throws Exception {
        fichero = new BufferedReader(new FileReader(nombre));
        codigoLeido = fichero.read();
    }

    public boolean quedanLineas() {
        return codigoLeido != -1;
    }

    public Linea leerFichero() throws Exception {
        Linea linea = new Linea();

        while (codigoLeido != -1 && codigoLeido != '\n' && codigoLeido != '\r') {
            linea.añadirCaracter((char) codigoLeido);
            codigoLeido = fichero.read();
        }

        // saltar '\n' o '\r'
        if (codigoLeido == '\r' || codigoLeido == '\n') {
            fichero.read();
        }
        codigoLeido = fichero.read();

        return linea;
    }

    public void cerrarFichero() throws Exception {
        fichero.close();
    }
}
