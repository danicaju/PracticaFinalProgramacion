package practicafinalprogramacion;

public class Linea {

    private char[] caracteres;
    private int numeroCaracteres;

    public Linea() {
        caracteres = new char[255];
        numeroCaracteres = 0;
    }

    public void añadirCaracter(char c) {
        if (numeroCaracteres < caracteres.length) {
            caracteres[numeroCaracteres] = c;
            numeroCaracteres++;
        }
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public char getCaracter(int pos) {
        return caracteres[pos];
    }

    @Override
    public String toString() {
        String salida = "";
        for (int i = 0; i < numeroCaracteres; i++) {
            salida += caracteres[i];
        }
        return salida;
    }
}
