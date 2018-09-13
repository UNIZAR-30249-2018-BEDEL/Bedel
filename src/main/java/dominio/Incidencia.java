package dominio;

import infraestructura.ResultadoQueryInstancia;

import java.text.SimpleDateFormat;
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

    public Incidencia(ResultadoQueryInstancia instancia) throws Exception {
        asunto = instancia.next();
        descripcion = instancia.next();
        fecha = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        fecha.setTime(format.parse(instancia.next()));
        localizacion = new Localizacion(Double.parseDouble(instancia.next()), Double.parseDouble(instancia.next()),
                instancia.next());
        estado = Estado.fromString(instancia.next());
        id = UUID.fromString(instancia.next());
    }

    public void aceptar() throws Exception {
        if(estado == Estado.CREADO) {
            estado = Estado.ACEPTADO;
            fecha = Calendar.getInstance();
        }
        else {
            throw new Exception("Estado de destino incompatible");
        }
    }

    public void cancelar() throws Exception {
        if(estado == Estado.CREADO) {
            estado = Estado.CANCELADO;
            fecha = Calendar.getInstance();
        }
        else {
            throw new Exception("Estado de destino incompatible");
        }
    }

    public void completar() throws Exception {
        if(estado == Estado.ACEPTADO) {
            estado = Estado.COMPLETADO;
            fecha = Calendar.getInstance();
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

    public String serializar() {
        return String.format("{ \"asunto\": \"%s\", \"descripcion\": \"%s\", \"fecha\": \"%s\", " +
                        "\"lon\": \"%f\", \"lat\": \"%f\", \"planta\": \"%s\", \"estado\": \"%s\", " +
                        "\"id\": \"%s\" } ",
                asunto, descripcion, fecha.getTime().toString(), localizacion.getLongitud(), localizacion.getLatitud(),
                localizacion.getPlanta(), estado.getEstado(), id.toString());
    }

}
