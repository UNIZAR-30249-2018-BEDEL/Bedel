package dominio;

import infraestructura.ResultadoQueryInstancia;

import java.util.UUID;

public class Espacio extends Entidad {

    private String descripcion;
    private double superficie;
    private double aforo;
    private String tipo;
    private String planta;
    private Horario horario;
    private String edificio;

    public Espacio(ResultadoQueryInstancia instancia) throws Exception {
        descripcion = instancia.next();
        superficie = Double.parseDouble(instancia.next());
        aforo = Double.parseDouble(instancia.next());
        tipo = instancia.next();
        planta = instancia.next();
        horario = new Horario(instancia.next());
        edificio = instancia.next();
        id = UUID.fromString(instancia.next());
    }

    public String serializar() {
        return String.format("{ \"descripcion\": \"%s\", \"superficie\": \"%f\", \"aforo\": \"%f\", " +
                        "\"tipo\": \"%s\", \"planta\": \"%s\", \"horario\": \"%s\", \"edificio\": \"%s\", " +
                        "\"id\": \"%s\" } ",
                descripcion, superficie, aforo, tipo, planta, horario.toString(), edificio, id.toString());
    }

}
