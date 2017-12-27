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

    //日志对象
    public static Logger log = Logger.getLogger(DBimp.class);


    @Override
    public boolean executeNoneQuery(String sql) {
        // 更新删除添加功能
        int i = 0;
        synchronized (this) {
            QueryRunner qr = new QueryRunner();
            try {
                Connection conn = jdbcUtil.getConnection();

                i = qr.update(conn, sql);
                log.debug("执行的sql语句为" + "[" + sql + "]");
                log.debug("成功更新了" + i + "语句");
                jdbcUtil.releaseConnection(conn);
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error("执行的sql语句为" + "[" + sql + "]");
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
        try {
            synchronized (this) {
                Connection conn = jdbcUtil.getConnection();
                log.debug("执行的sql语句为" + "[" + sql + "]");
                log.debug("查询成功");
                List<Object[]> query = qr.query(conn, sql, handler);
                for (Object[] objects : query) {
                    for (Object object : objects) {
                        list.add(object.toString());
                    }
                }
                jdbcUtil.releaseConnection(conn);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }

        return null;

    }

}
