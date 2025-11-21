package practicafinalprogramacion;

/*
AUTORES: Maria Teresa Sbert Gomila y Daniel Casado Juan
GRUPO: 2
 */
public class Palabra {

    private char[] letras;
    private final int maximo = 20;
    private int longitud;
    
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
