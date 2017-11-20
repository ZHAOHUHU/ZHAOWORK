package teamway.shenzhen.tms9000;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class DBClients {

	private static final int port = 9996;
	private static String host = "127.0.0.1";

	public static void main(String[] args) {
//		try {
//			// 设置调用的服务地址为本地，端口为 7911 // 设置传输通道
//			TTransport transport = new TSocket(host, port);
//			transport.open();
//			// 设置传输协议为 TBinaryProtocol
//			/*
//			 * 有错误再说 TSocket transport = new TSocket("localhost", 9090); transport.open();
//			 * TProtocol protocol = new TBinaryProtocol(new
//			 * TFastFramedTransport(transport));
//			 */
//			TProtocol protocol = new TBinaryProtocol(transport);
//			DbService.Client client = new DbService.Client(protocol);
//
//			client.initDB("127.0.0.1", 3306, "root", "74110", "test", 3, 10);
//			// System.out.println(b);
//			// System.out.println(c);
//			//String sql = "INSERT INTO huhu (id,girlname)VALUES(96,'wodetianan怎么会这样')";
//		//	client.executeNoneQuery(sql);
//			String sql2 = "select * from huhu";
//			String string = client.queryObject(sql2);
//			System.out.println(string);
//
//			transport.close();
//		} catch (TTransportException e) {
//			e.printStackTrace();
//		} catch (TException e) {
//			e.printStackTrace();
//		}
		 try {
	           //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
	            TTransport transport = new TFramedTransport(new TSocket("localhost", port,3000));
	            transport.open();
	            // 协议要和服务端一致
	            //HelloTNonblockingServer
	            ////使用高密度二进制协议
	            TProtocol protocol = new TBinaryProtocol(transport);
	            DbService.Client client = new DbService.Client(protocol);
	            // 调用服务的方法
	            client.initDB("127.0.0.1", 3306, "root", "74110", "test", 3, 10);
	            String sql2 = "select * from huhu";
				String string = client.queryObject(sql2);
				System.out.println(string);
	            transport.close();
	        } catch (TTransportException e) {
	            e.printStackTrace();
	        } catch (TException e) {
	            e.printStackTrace();
	        }
	    }
	}
