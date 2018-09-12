package dominio;

import infraestructura.BaseRepositorio;
import infraestructura.ResultadoQuery;

import java.util.Optional;

public class EspacioRepositorio {

    public EspacioRepositorio() {}

    public Optional<Espacio> obtenEspacioporLocalizacion(Localizacion loc) throws Exception {
        Optional<Espacio> resultado;
        ResultadoQuery rq = BaseRepositorio.buscaEspacioPorCoordenadas(loc.getLongitud(), loc.getLatitud(), loc.getPlanta());

        if (rq == null) {
            resultado = Optional.empty();
        }
        else {
            Espacio espacio = new Espacio(rq.next());
            resultado = Optional.of(espacio);
        }

        return resultado;
    }

}
