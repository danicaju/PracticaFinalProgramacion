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

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c != ' ' && c != '\t') {
                /*
                Concatenamos el string total con el
                caracter distinto de ' ' y '\t'. 
                Por tanto, en nuestro string solo 
                habra palabras del diccionario.
                 */
                total = total + c;
            }

        }

        return (total).toCharArray();
    }

    public void cerrarFichero() throws IOException {
        br.close();
    }

}
