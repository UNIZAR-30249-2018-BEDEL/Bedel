package aplicacion;

import dominio.Administrador;
import dominio.Incidencia;
import dominio.IncidenciaRepositorio;
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

@WebServlet("/obtenerIncidencias")
public class ObtenerIncidenciasServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ObtenerIncidenciasServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IncidenciaRepositorio incidenciaRepo = new IncidenciaRepositorio();
        Optional<ArrayList<Incidencia>> incidencias;

        JSONObject jsonRequest;
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonRequest = ServletCommon.readJSON(request.getReader());
            if(jsonRequest.containsKey("token")) {
                incidencias = incidenciaRepo.obtenIncidencias(new Administrador(jsonRequest.getString("token")));
            }
            else {
                incidencias = incidenciaRepo.obtenIncidencias(null);
            }
            if(incidencias.isPresent()) {
                JSONArray jsonIncidencias = new JSONArray();
                for(Incidencia in : incidencias.get()) {
                    jsonIncidencias.add(JSONObject.fromObject(in.serializar()));
                }
                jsonResponse.put("incidencias", jsonIncidencias);
            }
            else {
                jsonResponse.put("incidencias", new JSONArray());
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

