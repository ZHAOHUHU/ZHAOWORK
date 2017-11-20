package teamway.shenzhen.tms9000;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.thrift.TException;


public class DBimp implements DbService.Iface {
	private String ip;
	private String user;
	private String password;
	private String dbInstance;
	private long port;
	private int initialPoolSize;
	private int maxPoolSize;

	@Override
	public boolean initDB(String ip, int port, String user, String password, String dbInstance, int initialPoolSize,
			int maxPoolSize) throws TException {
		// TODO Auto-generated method stub
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.dbInstance = dbInstance;
		this.initialPoolSize = initialPoolSize;
		this.maxPoolSize = maxPoolSize;
		return false;
	}

	@Override
	public boolean executeNoneQuery(String sql) throws TException {
		// TODO Auto-generated method stub
		// 更新删除添加功能
		int i = 0;
		Connection conn = null;
		synchronized (this) {
			conn = JdbcUtils.getConnection(ip, port, user, password, dbInstance, initialPoolSize, maxPoolSize);
			QueryRunner qr = new QueryRunner();
			try {
				i = qr.update(conn, sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (i != 0) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public String queryObject(String sql) throws TException {
		// TODO Auto-generated method stub
		ArrayList<Object> list = new ArrayList<>();
		QueryRunner qr = new QueryRunner();
		ArrayListHandler handler = new ArrayListHandler();
		Connection conn = null;
		try {
			synchronized (this) {
				conn = JdbcUtils.getConnection(ip, port, user, password, dbInstance, initialPoolSize, maxPoolSize);
				List<Object[]> query = qr.query(conn, sql, handler);
				for (Object[] objects : query) {
					for (Object object : objects) {
						list.add(object);
					}
				}
			}
			String string = list.toString();
			return string;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "查询值为空";
	}

}
