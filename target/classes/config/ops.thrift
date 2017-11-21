  namespace java teamway.shenzhen 

	  service DbService{ 
	 
	 
	 bool initDB(1:string ip,2:i32 port,3:string user,4:string password,5:string dbInstance, 6:i32 initialPoolSize,7:i32 maxPoolSize),
	 
	  bool executeNoneQuery(1:string sql), 
	 
	string queryObject(1:string sql)
	  
	  }