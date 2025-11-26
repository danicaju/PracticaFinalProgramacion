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

    public char[] leerFicheroCaracter() throws IOException {

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

//    public int[] leerFicheroCifra() throws IOException {
//
//        String total = "";
//
//        String linea = br.readLine();
//
//        char totalAux[];
//        int aux1[] = new int[50];
//
//        /*
//        Cuando lea todo, la ultima linea sera null, y ademas 
//        al no almacenar ese null, lo que devolvera sera todo 
//        lo anterior a null, es decir total.toCharArray().
//        
//        Por ende, el length en ese momento es 0. Y eso lo 
//        usaremos a nuestro favor en el main, ya que leeremos
//        hasta que el .length de nuestra variable array de 
//        caracteres char total sea 0 (ultima linea).
//       `*/
//        if (linea == null) {
//            return aux1;
//        }
//        char aux[] = linea.toCharArray();
//        for (int i = 0; i < aux.length; i++) {
//            aux = linea.toCharArray();
//            char c = aux[i];
//            if (c != ' ' && c != '\t') {
//                /*
//                Concatenamos el string total con el
//                caracter distinto de ' ' y '\t'. 
//                Por tanto, en nuestro string solo 
//                habra palabras del diccionario.
//                 */
//                total = total + c;
//            } else {
//                c = (char) aux[i];
//            }
//
//        }
//
//        totalAux = total.toCharArray();
//        for (int i = 0; i < total.length(); i++) {
//            aux1[i] = (int) totalAux[i];
//        }
//        return aux1;
//    }
//    public int[] leerFicheroCifras() throws Exception {
//
//        String total = "";
//
//        String linea = br.readLine();
//
//        /*
//        Cuando lea todo, la ultima linea sera null, y ademas 
//        al no almacenar ese null, lo que devolvera sera todo 
//        lo anterior a null, es decir total.toCharArray().
//        
//        Por ende, el length en ese momento es 0. Y eso lo 
//        usaremos a nuestro favor en el main, ya que leeremos
//        hasta que el .length de nuestra variable array de 
//        caracteres char total sea 0 (ultima linea).
//       `*/
//        if (linea == null) {
//            return total.toCharArray();
//        }
//        char aux[] = linea.toCharArray();
//        for (int i = 0; i < aux.length; i++) {
//            char c = aux[i];
//
//            if (c != ' ' && c != '\t') {
//                /*
//                Concatenamos el string total con el
//                caracter distinto de ' ' y '\t'. 
//                Por tanto, en nuestro string solo 
//                habra palabras del diccionario.
//                 */
//                total = total + c;
//            }
//            aux = linea.toCharArray();
//
//        }
//        return total.;
//
//    }

    public void cerrarFichero() throws IOException {
        br.close();

    }
}
