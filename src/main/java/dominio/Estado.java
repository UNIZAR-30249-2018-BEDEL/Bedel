package dominio;

public enum Estado {

    CREADO("creado"),
    CANCELADO("cancelado"),
    ACEPTADO("aceptado"),
    COMPLETADO("completado");

    private final String estado;

    Estado(String e) {
        estado = e;
    }

    public String getEstado() {
        return estado;
    }

}