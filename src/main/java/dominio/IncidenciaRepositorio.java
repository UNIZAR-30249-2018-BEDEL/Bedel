package dominio;

import infraestructura.BaseRepositorio;

import java.sql.Connection;

public class IncidenciaRepositorio {

    public IncidenciaRepositorio() {}

    public void creaIncidencia(Connection con, Incidencia in) throws Exception {
        BaseRepositorio.creaIncidencia(con, in.getAsunto(), in.getDescripcion(), in.getFecha(),
                in.getLocalizacion().getLongitud(), in.getLocalizacion().getLatitud(), in.getLocalizacion().getPlanta(),
                in.getEstado().getEstado(), in.getId().toString());
    }

}
