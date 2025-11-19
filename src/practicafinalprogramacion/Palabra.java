/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinalprogramacion;

/**
 *
 * @author danic
 */
public class Palabra {

    char[] letras;
    final int maximo = 20;
    int longitud;
    
    public Palabra() {
       this.letras = new char[maximo];
       this.longitud = 0;
    }
    
    
    public char[] getLetras() {
        return letras;
    }

    public int getLongitud() {
        return longitud;
    }
    
    public boolean sonIguales(Palabra p1) {
        p1.getLetras();
        for(int i = 0; i < longitud; i++) {
            return letras[i] == p1.letras[i];
        }
        return false;
    }
}
