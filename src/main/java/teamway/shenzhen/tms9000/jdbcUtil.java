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
            log.info("获取数据库连接成功");
            return connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e);
            log.error("数据库连接异常");
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
                log.error("数据库关闭异常");
            }
        }
    }
}
