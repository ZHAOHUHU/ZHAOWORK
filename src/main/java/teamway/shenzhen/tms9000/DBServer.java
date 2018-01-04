package teamway.shenzhen.tms9000;

import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class DBServer {

	private static final int port = 10745;
	public static Logger log = Logger.getLogger(DBServer.class);
	public static void main(String[] args) {
		  try {
	            TNonblockingServerSocket serverTransport = new MyTNonblockingServerSocket(port);
	            // 设置协议工厂为 TBinaryProtocol.Factory
	            // 关联处理器与 Hello 服务的实现
	            TProcessor tprocessor = new DbService.Processor(new DBimp());
	            //异步IO，需要使用TFramedTransport，它将分块缓存读取。
	            TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
	            tArgs.processor(tprocessor);
	            tArgs.transportFactory(new TFramedTransport.Factory());
	            //使用二进制协议
	            tArgs.protocolFactory(new TBinaryProtocol.Factory());
	            //线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
	            TServer server =new TNonblockingServer(tArgs);

	            log.info("MySQLService服务启动成功，端口号为： "+port);
	            server.serve();
	        } catch (TTransportException e) {
	           log.error(e);
	           log.error("服务启动失败");
	        }
	    }
	}

