package teamway.shenzhen.tms9000;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBimp implements DbService.Iface {
    static Connection conn;

    //日志对象
    public static Logger log = Logger.getLogger(DBimp.class);

    public static void getConnection() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
        try {
            conn = dataSource.getConnection();
            log.info("获取连接成功");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }

    }

    @Override
    public boolean executeNoneQuery(String sql) {
        // 更新删除添加功能
        int i = 0;
        synchronized (this) {
            QueryRunner qr = new QueryRunner();
            // DBimp.getConnection();
            try {
                if (conn != null) {
                    i = qr.update(conn, sql);
                    log.info("执行的sql语句为" + "[" + sql + "]");
                    log.info("成功更新了" + i + "语句");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.info("执行的sql语句为" + "[" + sql + "]");
                log.error(e);
            }
        }
        if (i != 0) {
            try {
                conn.close();
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e);
            }
        }
        return false;
    }

    @Override
    public List<String> queryObject(String sql) {
        // TODO Auto-generated method stub
        ArrayList<String> list = new ArrayList<>();
        QueryRunner qr = new QueryRunner();
        ArrayListHandler handler = new ArrayListHandler();
        // DBimp.getConnection();
        try {
            synchronized (this) {
                if (conn == null) {
                    DBimp.getConnection();
                    log.info("执行的sql语句为" + "[" + sql + "]");
                    log.info("查询成功");
                }
                List<Object[]> query = qr.query(conn, sql, handler);
                for (Object[] objects : query) {
                    for (Object object : objects) {
                        list.add(object.toString());
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e);
        } finally {
            try {
                if(conn==null){
                    // 关闭连接
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return list;

    }

}
