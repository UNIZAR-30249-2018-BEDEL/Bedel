package aplicacion;

import dominio.Estado;
import dominio.Incidencia;
import dominio.IncidenciaRepositorio;
import infraestructura.PoolDeConexiones;
import infraestructura.ServletCommon;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/cambiarEstadoIncidenciaServlet")
public class CambiarEstadoIncidenciaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CambiarEstadoIncidenciaServlet() {
        super();
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IncidenciaRepositorio incidenciaRepo = new IncidenciaRepositorio();
        Optional<Incidencia> incidencia;

        JSONObject jsonRequest;
        try (Connection con = PoolDeConexiones.getConnection()) {
            con.setAutoCommit(false);
            try {
                jsonRequest = ServletCommon.readJSON(request.getReader());
                incidencia = incidenciaRepo.obtenIncidenciaPorId(jsonRequest.getString("id"));
                if(incidencia.isPresent()) {
                    if (jsonRequest.getString("nuevoEstado").equals(Estado.ACEPTADO.getEstado())) {
                        incidencia.get().aceptar();
                    }
                    else if (jsonRequest.getString("nuevoEstado").equals(Estado.CANCELADO.getEstado())) {
                        incidencia.get().cancelar();
                    }
                    else if (jsonRequest.getString("nuevoEstado").equals(Estado.COMPLETADO.getEstado())) {
                        incidencia.get().completar();
                    }
                    incidenciaRepo.modificaEstadoIncidencia(con, incidencia.get());
                    con.commit();
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Estado de la incidencia modificado");
                }
                else {
                    throw new Exception("La incidencia no existe");
                }
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

