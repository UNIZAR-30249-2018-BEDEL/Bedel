package aplicacion;

import dominio.Incidencia;
import dominio.IncidenciaRepositorio;
import infraestructura.PoolDeConexiones;
import infraestructura.ServletCommon;
import jdk.nashorn.internal.ir.annotations.Ignore;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

@WebServlet("/crearIncidencia")
public class CrearIncidenciaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CrearIncidenciaServlet() {
        super();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IncidenciaRepositorio incidenciaRepo = new IncidenciaRepositorio();
        Incidencia incidencia;

        JSONObject jsonRequest;
        try (Connection con = PoolDeConexiones.getConnection()) {
            con.setAutoCommit(false);
            try {
                jsonRequest = ServletCommon.readJSON(request.getReader());
                incidencia = new Incidencia(jsonRequest.getString("asunto"), jsonRequest.getString("descripcion"),
                        jsonRequest.getDouble("lon"), jsonRequest.getDouble("lat"),
                        jsonRequest.getString("planta"));
                incidenciaRepo.creaIncidencia(con, incidencia);;
                con.commit();
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Incidencia creada");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException ex) {}
                finally {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("error");
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("error");
        }
    }

}

