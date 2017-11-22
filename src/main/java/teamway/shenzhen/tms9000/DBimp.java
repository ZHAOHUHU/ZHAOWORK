package teamway.shenzhen.tms9000;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.thrift.TException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBimp implements DbService.Iface {
	static Connection conn ;

	public static void getConnection() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean executeNoneQuery(String sql) {
		// TODO Auto-generated method stub
		// 更新删除添加功能
		int i = 0;
		synchronized (this) {
			QueryRunner qr = new QueryRunner();
			DBimp.getConnection();
			try {
				if (conn != null) {

					i = qr.update(conn, sql);
				} else {
					i = 0;
				}
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
	public List<String> queryObject(String sql) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		QueryRunner qr = new QueryRunner();
		ArrayListHandler handler = new ArrayListHandler();
		DBimp.getConnection();
		try {
			synchronized (this) {
				if (conn != null) {

					List<Object[]> query = qr.query(conn, sql, handler);
					for (Object[] objects : query) {
						for (Object object : objects) {
							list.add(object.toString());
						}
					}
				} else {
					throw new Error();
				}
			}
			return list;
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
		return list;

	}

}
