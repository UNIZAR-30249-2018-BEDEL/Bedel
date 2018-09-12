package infraestructura;

import java.util.ArrayList;

public class ResultadoQueryInstancia {

    private int indice;
    private int longitud;
    private ArrayList<String> resultado;

    public ResultadoQueryInstancia(ArrayList<String> res) {
        indice = 0;
        longitud = res.size();
        resultado = res;
    }

    public String next() {
        if (hasNext()) {
            return resultado.get(indice++);
        }
        return null;
    }

    public boolean hasNext() {
        return indice < longitud;
    }

    public void toFirst() {
        indice = 0;
    }

}
