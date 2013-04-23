package com.xmltomysql;

import java.util.ArrayList;


class DriverProg {
  
	public static void main (String[] args) throws Exception {
		MySQLAccess my_access = new MySQLAccess();
		my_access.readDataBase();
		ParseXML p = new ParseXML();
		p.xmlparse();
		my_access.write_name(p.country_name_list);
		my_access.write_border(p.country_border_list);
		my_access.write_area(p.country_area_list);
		//String table, String param, String type, String order, String limit
		my_access.query("Total,country_area,order by,asc,5");
	    System.out.println();
	    display (my_access.result_array);
		my_access.build_border_graph();
		my_access.query("country_border,CHN");
		display (my_access.result_array);
		my_access.query("check_connection,CHN,IRN");
		
	}
	
	public static void display (ArrayList<String> input) {
		if (input == null)
			return;
		System.out.println(" in display ");
		for (int i = 0; i < input.size(); i++)
			System.out.print(input.get(i)+" ");
		System.out.println();
	}
	
}

