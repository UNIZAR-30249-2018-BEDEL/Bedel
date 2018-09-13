package infraestructura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public final class BaseRepositorio {

    private BaseRepositorio() {}


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

    public static void modificaEstadoIncidencia(Connection con, Calendar fecha, String estado,
                                                String id) throws Exception {
        PreparedStatement stmt = con.prepareStatement(
                "UPDATE incidencia " +
                "SET fecha=?, estado=? " +
                "WHERE id=?;");
        stmt.setDate(1, new java.sql.Date(fecha.getTimeInMillis()));
        stmt.setString(2, estado);
        stmt.setString(3, id);

        stmt.executeUpdate();
    }

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

    public static ResultadoQuery buscaIncidenciaPorId(String id) {
        try (Connection con = PoolDeConexiones.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM incidencia " +
                    "WHERE id=? " +
                    "LIMIT 1;");
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            ResultadoQuery rq;
            if(rs.next()) {
                rq = new ResultadoQuery();
                ArrayList<String> instancia;
                do {
                    instancia = new ArrayList<>();
                    instancia.add(rs.getString("asunto"));
                    instancia.add(rs.getString("descripcion"));
                    instancia.add(rs.getDate("fecha").toString());
                    instancia.add(Double.toString(rs.getDouble("lon")));
                    instancia.add(Double.toString(rs.getDouble("lat")));
                    instancia.add(rs.getString("planta"));
                    instancia.add(rs.getString("estado"));
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

    public static ResultadoQuery buscaIncidencias(boolean admin) {
        try (Connection con = PoolDeConexiones.getConnection()) {
            PreparedStatement stmt;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -7);
            java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
            if (admin) {
                stmt = con.prepareStatement(
                        "SELECT * FROM incidencia " +
                                "WHERE estado=\'creado\' OR estado=\'aceptado\' " +
                                "OR (estado=\'completado\' AND fecha>=?) " +
                                "OR (estado=\'cancelado\' AND fecha>=?);");
                stmt.setDate(1, date);
                stmt.setDate(2, date);
            }
            else {
                stmt = con.prepareStatement(
                        "SELECT * FROM incidencia " +
                                "WHERE estado=\'aceptado\' " +
                                "OR (estado=\'completado\' AND fecha>=?);");
                stmt.setDate(1, date);
            }

            ResultSet rs = stmt.executeQuery();
            ResultadoQuery rq;
            if(rs.next()) {
                rq = new ResultadoQuery();
                ArrayList<String> instancia;
                do {
                    instancia = new ArrayList<>();
                    instancia.add(rs.getString("asunto"));
                    instancia.add(rs.getString("descripcion"));
                    instancia.add(rs.getDate("fecha").toString());
                    instancia.add(Double.toString(rs.getDouble("lon")));
                    instancia.add(Double.toString(rs.getDouble("lat")));
                    instancia.add(rs.getString("planta"));
                    instancia.add(rs.getString("estado"));
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

    public static ResultadoQuery buscaAdministradorPorCredenciales(String nick, String pass) {
        try (Connection con = PoolDeConexiones.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM administrador " +
                            "WHERE nick=? AND pass=?" +
                            "LIMIT 1;");
            stmt.setString(1, nick);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();
            ResultadoQuery rq;
            if(rs.next()) {
                rq = new ResultadoQuery();
                ArrayList<String> instancia;
                do {
                    instancia = new ArrayList<>();
                    instancia.add(rs.getString("nick"));
                    instancia.add(rs.getString("pass"));
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
}
