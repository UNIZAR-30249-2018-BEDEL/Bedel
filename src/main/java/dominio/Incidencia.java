package dominio;

import java.util.Calendar;
import java.util.UUID;

public class Incidencia extends Entidad {

    private String asunto;
    private String descripcion;
    private Calendar fecha;
    private Localizacion localizacion;
    private Estado estado;

    public Incidencia(String as, String dc, double lon, double lat, String pl) {
        asunto = as;
        descripcion = dc;
        fecha = Calendar.getInstance();
        localizacion = new Localizacion(lon, lat, pl);
        estado = Estado.CREADO;
        id = UUID.randomUUID();
    }

    public void aceptar() throws Exception {
        if(estado.getEstado().equals(Estado.CREADO)) {
            estado = Estado.ACEPTADO;
        }
        else {
            throw new Exception("Estado de destino incompatible");
        }
    }

    public void cancelar() throws Exception {
        if(estado.getEstado().equals(Estado.CREADO)) {
            estado = Estado.CANCELADO;
        }
        else {
            throw new Exception("Estado de destino incompatible");
        }
    }

    public void completar() throws Exception {
        if(estado.getEstado().equals(Estado.ACEPTADO)) {
            estado = Estado.COMPLETADO;
        }
        else {
            throw new Exception("Estado de destino incompatible");
        }
    }

    public String getAsunto() {
        return asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public Estado getEstado() {
        return estado;
    }

}
