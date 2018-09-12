package servlets;

import aplicacion.ObtenerEspacioPorCoordenadasServlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class obtenerEspacioPorCoordenadasTest extends Mockito {

    final static Logger log = LoggerFactory.getLogger(obtenerEspacioPorCoordenadasTest.class);

    @Test
    public void testServletEspacio() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"lon\":\"675746.8750289385\"," +
                    "\"lat\":\"4616759.077585834\"," +
                    "\"planta\":\"P0\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerEspacioPorCoordenadasServlet().doGet(request, response);

            writer.flush();

            JSONObject JSONResponse = JSONObject.fromObject(stringWriter.toString());
            assertTrue(JSONResponse.getString("id").contains("0f47f99d-8232-4125-a909-0da342f39502"));
            assertTrue(JSONResponse.getString("edificio").contains("Ada Byron"));
            assertTrue(JSONResponse.getString("planta").contains("P0"));
            assertTrue(JSONResponse.getString("horario").contains("Lunes:: 08:00 - 09:00, 16:00 - 18:00; " +
                    "Martes:: ; Jueves:: 09:00 - 11:30; "));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletExterior() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"lon\":\"675913.8938496995\"," +
                    "\"lat\":\"4616758.481090046\"," +
                    "\"planta\":\"P0\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerEspacioPorCoordenadasServlet().doGet(request, response);

            writer.flush();

            JSONObject JSONResponse = JSONObject.fromObject(stringWriter.toString());
            assertTrue(JSONResponse.getString("exterior").contains("true"));
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
                "{\"lat\":\"4616758.481090046\"," +
                    "\"planta\":\"P0\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerEspacioPorCoordenadasServlet().doGet(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("error"));
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}