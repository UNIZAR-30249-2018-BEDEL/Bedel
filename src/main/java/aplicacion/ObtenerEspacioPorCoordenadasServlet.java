package aplicacion;

import infraestructura.ServletCommon;
import dominio.EspacioRepositorio;
import dominio.Espacio;
import dominio.Localizacion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/obtenerEspacioPorCoordenadas")
public class ObtenerEspacioPorCoordenadasServlet extends HttpServlet {

    final static Logger log = LoggerFactory.getLogger(ObtenerEspacioPorCoordenadasServlet.class);

    private static final long serialVersionUID = 1L;

    public ObtenerEspacioPorCoordenadasServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EspacioRepositorio espacioRepo = new EspacioRepositorio();
        Optional<Espacio> espacio;

        JSONObject jsonRequest;
        JSONObject jsonResponse;

        try {
            jsonRequest = ServletCommon.readJSON(request.getReader());
            espacio = espacioRepo.obtenEspacioporLocalizacion(new Localizacion(jsonRequest.getDouble("lon"),
                    jsonRequest.getDouble("lat"), jsonRequest.getString("planta")));
            if(espacio.isPresent()) {
                jsonResponse = JSONObject.fromObject(espacio.get().serializar());
            }
            else {
                jsonResponse = new JSONObject();
                jsonResponse.put("exterior", true);
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

