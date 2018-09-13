package servlets;

import aplicacion.CambiarEstadoIncidenciaServlet;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CambiarEstadoIncidenciaTest extends Mockito {

    @Test
    public void testServletIncidenciaAceptada() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"id\":\"3abe01b4-769a-451e-890d-4979f7e1f838\"," +
                    "\"nuevoEstado\":\"aceptado\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CambiarEstadoIncidenciaServlet().doPut(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("Estado de la incidencia modificado"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletIncidenciaCanceladaErronea() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"id\":\"3abe01b4-769a-451e-890d-4979f7e1f838\"," +
                        "\"nuevoEstado\":\"cancelado\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CambiarEstadoIncidenciaServlet().doPut(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("error"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletIncidenciaCompletada() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"id\":\"3abe01b4-769a-451e-890d-4979f7e1f838\"," +
                        "\"nuevoEstado\":\"completada\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CambiarEstadoIncidenciaServlet().doPut(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("Estado de la incidencia modificado"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletIdNoExistente() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"id\":\"4abe01b4-769a-451e-890d-4979f7e1f838\"," +
                    "\"nuevoEstado\":\"completada\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CambiarEstadoIncidenciaServlet().doPut(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("error"));
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}