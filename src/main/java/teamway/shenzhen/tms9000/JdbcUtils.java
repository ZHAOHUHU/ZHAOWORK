package teamway.shenzhen.tms9000;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {

	static ComboPooledDataSource c = null;

	public static Connection getConnection(String ip, long port, String user, String password, String dbInstance,
			int initialPoolSize, int maxPoolSize) {
		
		String jdbcUrl = "jdbc:mysql://" + ip + ":" + port + "/" + dbInstance+"?useSSL=true";
		// 获取连接对象
		c = new ComboPooledDataSource();
		c.setUser(user);
		c.setPassword(password);

		try {
			c.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.setJdbcUrl(jdbcUrl);
		c.setInitialPoolSize(initialPoolSize);
		c.setMaxPoolSize(maxPoolSize);
		c.setAutoCommitOnClose(true);
		try {
			return c.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnection() {
		try {
			c.close();
			c.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
