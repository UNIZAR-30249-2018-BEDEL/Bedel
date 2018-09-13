package servlets;

import aplicacion.CrearIncidenciaServlet;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.Assert.assertTrue;

public class CrearIncidenciaTest extends Mockito {

    @Test
    public void testServletIncidencia() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"asunto\":\"Bombilla fundida\"," +
                    "\"descripcion\":\"Nada mas entrar pulsar el primer interruptor\"," +
                    "\"lon\":\"675746.8750289385\"," +
                    "\"lat\":\"4616759.077585834\"," +
                    "\"planta\":\"P0\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CrearIncidenciaServlet().doPost(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("Incidencia creada"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletFallo() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"descripcion\":\"Nada mas entrar pulsar el primer interruptor\"," +
                    "\"lon\":\"675746.8750289385\"," +
                    "\"lat\":\"4616759.077585834\"," +
                    "\"planta\":\"P0\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new CrearIncidenciaServlet().doPost(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("error"));
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}