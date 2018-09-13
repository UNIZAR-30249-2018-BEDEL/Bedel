package dominio;

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

}
