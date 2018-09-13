package servlets;

import aplicacion.ObtenerIncidenciasServlet;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.Assert.assertTrue;

public class ObtenerIncidenciasTest extends Mockito {

    Logger log = LoggerFactory.getLogger(ObtenerIncidenciasTest.class);

    @Test
    public void testServletIncidenciasAdministrador() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"token\":" +
                        "{\"nick\":\"Manolo\"," +
                         "\"pass\":\"123\"," +
                        " \"id\":\"c4136206-487f-4e8e-887d-e3dd0ab270aa\"}}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerIncidenciasServlet().doGet(request, response);

            writer.flush();

            log.info(stringWriter.toString());
            JSONArray JSONResponseArray = JSONObject.fromObject(stringWriter.toString()).getJSONArray("incidencias");
            JSONObject JSONResponse = JSONObject.fromObject(JSONResponseArray.get(0).toString());
            assertTrue(JSONResponse.getString("id").contains("3abe01b4-769a-451e-890d-4979f7e1f838"));
        } catch (ServletException|IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletIncidencias() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader("{}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerIncidenciasServlet().doGet(request, response);

            writer.flush();

            log.info(stringWriter.toString());
            JSONArray JSONResponseArray = JSONObject.fromObject(stringWriter.toString()).getJSONArray("incidencias");
            JSONObject JSONResponse = JSONObject.fromObject(JSONResponseArray.get(0).toString());
            assertTrue(JSONResponse.getString("id").contains("5e89367a-8180-489d-816c-b43f0bd0e108"));
        } catch (ServletException|IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletFallo() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(""));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new ObtenerIncidenciasServlet().doGet(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("error"));
        } catch (ServletException|IOException e) {
            e.printStackTrace();
        }
    }

}