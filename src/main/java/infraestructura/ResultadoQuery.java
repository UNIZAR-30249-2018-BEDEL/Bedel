package infraestructura;

import java.util.ArrayList;

public class ResultadoQuery {

    private int indice;
    private int longitud;
    private ArrayList<ResultadoQueryInstancia> resultado;

    public ResultadoQuery() {
        indice = 0;
        longitud = 0;
        resultado = new ArrayList<>();
    }

    public void append(ArrayList<String> instancia) {
        resultado.add(new ResultadoQueryInstancia(instancia));
        longitud++;
    }

    public ResultadoQueryInstancia next() {
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
