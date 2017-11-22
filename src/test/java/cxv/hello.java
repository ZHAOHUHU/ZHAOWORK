package cxv;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import teamway.shenzhen.tms9000.DBimp;

public class hello {

	@Test
	public void test() {
		DBimp db=new DBimp();
		//db.executeNoneQuery("insert into user (name,password,group_id,role_id)values ('huhu',78887,2,0)");
		//db.getConnection();
		List<String> list = db.queryObject("select * from user");
		for (String string : list) {
			System.out.println(string);
		}
		
	}

}
