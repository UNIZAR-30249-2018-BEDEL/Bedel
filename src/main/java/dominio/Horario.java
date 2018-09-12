package dominio;

import java.util.LinkedHashMap;
import java.util.Map;

public class Horario {

    private Map<String, Intervalos> horario;

    public Horario(String h) throws Exception {
        if (h != null) {
            String dia = "";
            horario = new LinkedHashMap<>();
            for (String s1 : h.trim().split(";")) {
                String[] p1 = s1.split("::");
                dia = p1[0].trim();
                horario.put(dia, new Intervalos());
                if (p1.length != 1) {
                    for (String s2 : p1[1].trim().split(",")) {
                        horario.get(dia).add(s2);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String resultado = "";

        if (horario != null) {
            for (Map.Entry<String, Intervalos> entry : horario.entrySet()) {
                resultado += entry.getKey() + ":: " + entry.getValue().toString();
            }
        }

        return resultado;
    }

}