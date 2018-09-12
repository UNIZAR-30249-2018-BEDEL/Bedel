package dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Intervalos {

    private SimpleDateFormat format;
    private ArrayList<Date> inicio;
    private ArrayList<Date> fin;

    public Intervalos() {
        format = new SimpleDateFormat("HH:mm");
        inicio = new ArrayList<>();
        fin = new ArrayList<>();
    }

    public void add(String intervalo) throws Exception {
        String[] inicioYFin = intervalo.split("-");
        Date i = format.parse(inicioYFin[0].trim());
        Date f = format.parse(inicioYFin[1].trim());

        if(i.before(f) && (fin.size() == 0 || (fin.size() > 0 && i.after(fin.get(fin.size()-1))))) {
            inicio.add(i);
            fin.add(f);
        }
        else {
            throw new Exception("Formato de horario incorrecto");
        }
    }

    @Override
    public String toString() {
        String resultado = "";

        if(inicio.size() == 0) {
            resultado = "; ";
        }

        for (int i=0; i<inicio.size(); i++) {
            resultado += format.format(inicio.get(i)) + " - " + format.format(fin.get(i));
            if (i != inicio.size()-1) {
                resultado += ", ";
            }
            else {
                resultado += "; ";
            }
        }

        return resultado;
    }

}