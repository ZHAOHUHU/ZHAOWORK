package rtyery;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import teamway.shenzhen.tms9000.DBimp;

public class rwet {

	@Test
	public void test() {
		DBimp db = new DBimp();
		List<String> list = db.queryObject("select * from user");
		System.out.println(list);
	}

}
