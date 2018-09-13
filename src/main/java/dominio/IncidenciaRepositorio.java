package dominio;

import infraestructura.BaseRepositorio;
import infraestructura.ResultadoQuery;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

public class IncidenciaRepositorio {

    public IncidenciaRepositorio() {}

    public void creaIncidencia(Connection con, Incidencia in) throws Exception {
        BaseRepositorio.creaIncidencia(con, in.getAsunto(), in.getDescripcion(), in.getFecha(),
                in.getLocalizacion().getLongitud(), in.getLocalizacion().getLatitud(), in.getLocalizacion().getPlanta(),
                in.getEstado().getEstado(), in.getId().toString());
    }

    public void modificaEstadoIncidencia(Connection con, Incidencia in) throws Exception {
        BaseRepositorio.modificaEstadoIncidencia(con, in.getFecha(), in.getEstado().getEstado(), in.getId().toString());
    }

    public Optional<Incidencia> obtenIncidenciaPorId(String id) throws Exception {
        Optional<Incidencia> resultado;
        ResultadoQuery rq = BaseRepositorio.buscaIncidenciaPorId(id);

        if (rq == null) {
            resultado = Optional.empty();
        }
        else {
            Incidencia incidencia = new Incidencia(rq.next());
            resultado = Optional.of(incidencia);
        }

        return resultado;
    }

    public Optional<ArrayList<Incidencia>> obtenIncidencias(Administrador admin) throws Exception {
        Optional<ArrayList<Incidencia>> resultado;
        ResultadoQuery rq;

        if (admin != null) {
            rq = BaseRepositorio.buscaIncidencias(true);
        }
        else {
            rq = BaseRepositorio.buscaIncidencias(false);
        }

        if (rq == null) {
            resultado = Optional.empty();
        }
        else {
            ArrayList<Incidencia> incidencias = new ArrayList<>();
            while (rq.hasNext()) {
                incidencias.add(new Incidencia(rq.next()));
            }
            resultado = Optional.of(incidencias);
        }

        return resultado;
    }

}
