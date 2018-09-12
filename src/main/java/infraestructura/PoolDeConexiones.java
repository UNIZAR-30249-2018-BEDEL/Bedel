package infraestructura;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolDeConexiones {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql:UNIZAR-30249-2018-BEDEL-bedel");
        config.setUsername("bedel");
        config.setPassword("bedel");
        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("cachePrepStmts" , "true");
        config.addDataSourceProperty("prepStmtCacheSize" , "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit" , "2048");
        ds = new HikariDataSource(config);
    }
    private PoolDeConexiones() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
