package teamway.shenzhen.tms9000;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class DBServer {

	private static final int port = 9996;
	//private static final int clientTimeout = null;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		System.out.println("action.......");
		TProcessor tp = new DbService.Processor<DbService.Iface>(new DBimp());
		TServerSocket socket = new TServerSocket(port);
		TThreadPoolServer.Args thb_args = new TThreadPoolServer.Args(socket);
		thb_args.processor(tp);
		thb_args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server =new TThreadPoolServer(thb_args);
		
		System.out.println("端口数："+port);
		server.serve();
	}
}
