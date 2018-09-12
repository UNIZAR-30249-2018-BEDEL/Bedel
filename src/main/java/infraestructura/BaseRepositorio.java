package infraestructura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class BaseRepositorio {

    private BaseRepositorio() {}

    public static ResultadoQuery buscaEspacioPorCoordenadas(Double longitud, Double latitud, String planta) {
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

}
