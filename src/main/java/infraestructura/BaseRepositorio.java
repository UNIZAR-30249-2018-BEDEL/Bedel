package infraestructura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public final class BaseRepositorio {

    private BaseRepositorio() {}

    public static ResultadoQuery buscaEspacioPorCoordenadas(double longitud, double latitud, String planta) {
        try (Connection con = PoolDeConexiones.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(
                    "SELECT descripcion, superficie, aforo, tipo, planta, horario, edificio, id " +
                    "FROM espacio " +
                    "WHERE planta=? " +
                    "AND ST_Contains(the_geom, ST_SetSRID(ST_Point(?,?),25830)) " +
                    "LIMIT 1;");
            stmt.setString(1, planta);
            stmt.setDouble(2, longitud);
            stmt.setDouble(3, latitud);

            ResultSet rs = stmt.executeQuery();
            ResultadoQuery rq;
            if(rs.next()) {
                rq = new ResultadoQuery();
                ArrayList<String> instancia;
                do {
                    instancia = new ArrayList<>();
                    instancia.add(rs.getString("descripcion"));
                    instancia.add(Double.toString(rs.getDouble("superficie")));
                    instancia.add(Double.toString(rs.getDouble("aforo")));
                    instancia.add(rs.getString("tipo"));
                    instancia.add(rs.getString("planta"));
                    instancia.add(rs.getString("horario"));
                    instancia.add(rs.getString("edificio"));
                    instancia.add(rs.getString("id"));
                    rq.append(instancia);
                } while(rs.next());

                return rq;
            }

            return null;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void creaIncidencia(Connection con, String asunto, String descripcion, Calendar fecha, double longitud,
                                      double latitud, String planta, String estado, String id) throws Exception {
        PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO incidencia " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        stmt.setString(1, asunto);
        stmt.setString(2, descripcion);
        stmt.setDate(3, new java.sql.Date(fecha.getTimeInMillis()));
        stmt.setDouble(4, longitud);
        stmt.setDouble(5, latitud);
        stmt.setString(6, planta);
        stmt.setString(7, estado);
        stmt.setString(8, id);

        stmt.executeUpdate();
    }

}
