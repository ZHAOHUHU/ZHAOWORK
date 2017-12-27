package teamway.shenzhen.tms9000;

import java.sql.Connection;
import java.sql.SQLException;

import javax.activation.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

public class jdbcUtil {
    public static Logger log = Logger.getLogger(DBimp.class);
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            log.debug("获取连接成功");
            return connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }
        return null;

    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e);
            }
        }
    }
}
