package dominio;

import infraestructura.ResultadoQueryInstancia;
import net.sf.json.JSONObject;

import java.util.UUID;

public class Administrador extends Entidad {

    private String nick;
    private String pass;

    public Administrador(String token) {
        JSONObject admin = JSONObject.fromObject(token);
        nick = admin.getString("nick");
        pass = admin.getString("pass");
        id = UUID.fromString(admin.getString("id"));
    }

    public Administrador(ResultadoQueryInstancia rq) {
        nick = rq.next();
        pass = rq.next();
        id = UUID.fromString(rq.next());
    }

    public String getToken() {
        return String.format("{ \"nick\": \"%s\", \"pass\": \"%s\", \"id\": \"%s\" } ",
                nick, pass, id.toString());
    }

}
