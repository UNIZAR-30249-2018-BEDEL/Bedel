package dominio;

public class Localizacion {

    private double longitud;
    private double latitud;
    private String planta;

    public Localizacion() {}

    public Localizacion(double lon, double lat, String pl){
        longitud = lon;
        latitud = lat;
        planta = pl;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public String getPlanta() {
        return planta;
    }

}
