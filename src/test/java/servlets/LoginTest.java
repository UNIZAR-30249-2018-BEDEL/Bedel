package servlets;

import aplicacion.LoginServlet;
import net.sf.json.JSONObject;
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

public class LoginTest extends Mockito {

    Logger log = LoggerFactory.getLogger(LoginTest.class);

    @Test
    public void testServletAdministrador() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"nick\":\"Manolo\"," +
                    "\"pass\":\"123\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new LoginServlet().doGet(request, response);

            writer.flush();

            log.info(stringWriter.toString());
            JSONObject JSONResponse = JSONObject.fromObject(stringWriter.toString());
            assertTrue(JSONResponse.containsKey("token"));
        } catch (ServletException|IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServletNoAdministrador() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        BufferedReader bf = new BufferedReader(new StringReader(
                "{\"nick\":\"Pedro\"," +
                    "\"pass\":\"123\"}"));
        when(request.getReader()).thenReturn(bf);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        try {
            new LoginServlet().doGet(request, response);

            writer.flush();

            assertTrue(stringWriter.toString().contains("El usuario no existe"));
        } catch (ServletException|IOException e) {
            e.printStackTrace();
        }
    }

}