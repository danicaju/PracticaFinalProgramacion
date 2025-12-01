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

    public char[] leerFicheroLetras() throws IOException {

        String total = "";

        String linea = br.readLine();

        /*
        Cuando lea todo, la ultima linea sera null, y ademas 
        al no almacenar ese null, lo que devolvera sera todo 
        lo anterior a null, es decir total.toCharArray().
        
        Por ende, el length en ese momento es 0. Y eso lo 
        usaremos a nuestro favor en el main, ya que leeremos
        hasta que el .length de nuestra variable array de 
        caracteres char total sea 0 (ultima linea).
       `*/
        if (linea == null) {
            return total.toCharArray();
        }
        char aux[] = linea.toCharArray();
        for (int i = 0; i < aux.length; i++) {
            char c = aux[i];

            if (c != ' ' && c != '\t') {
                /*
                Concatenamos el string total con el
                caracter distinto de ' ' y '\t'. 
                Por tanto, en nuestro string solo 
                habra palabras del diccionario.
                 */
                total = total + c;
            }
            aux = linea.toCharArray();

        }

        return (total).toCharArray();
    }

    public int rellenarArrayFicheroCifras() throws Exception {
        int contador = 0;
        boolean enNumero = false;
        int codigo;

        while ((codigo = br.read()) != -1) {
            char c = (char) codigo;

            if (c >= '0' && c <= '9') {      // si es un dígito
                if (!enNumero) {             // empieza un nuevo número
                    contador++;              // contamos un número completo
                    enNumero = true;         // ahora estamos dentro del número
                }
            } else {                         // cualquier otro carácter (espacio, tab, salto de línea)
                enNumero = false;            // termina el número actual
            }
        }

        return contador;                     // número de enteros en el fichero
    }

    public void cerrarFichero() throws IOException {
        br.close();

    }
}
