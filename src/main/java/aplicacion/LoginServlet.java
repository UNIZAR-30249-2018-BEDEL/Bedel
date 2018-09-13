package aplicacion;

import dominio.Administrador;
import dominio.Administrador;
import dominio.AdministradorRepositorio;
import infraestructura.ServletCommon;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdministradorRepositorio administradorRepo = new AdministradorRepositorio();
        Optional<Administrador> administrador;

        JSONObject jsonRequest;
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonRequest = ServletCommon.readJSON(request.getReader());
            administrador = administradorRepo.obtenCredenciales(jsonRequest.getString("nick"),
                    jsonRequest.getString("pass"));
            if(administrador.isPresent()) {
                JSONObject jsonToken = JSONObject.fromObject(administrador.get().getToken());
                jsonResponse.put("token", jsonToken);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("El usuario no existe");
            }
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("error");
        }
    }

}

