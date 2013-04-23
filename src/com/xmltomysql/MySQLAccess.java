package com.xmltomysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement pstmt = null;
	private ResultSet resultSet = null;
	
	ArrayList<CountryName> country_name;
	ArrayList<CountryArea> country_area;
	ArrayList<CountryBorder> country_border;
	ArrayList<String> result_array = new ArrayList<String>();
	ArrayList< ArrayList<String> > graph_list = new ArrayList< ArrayList<String> >();
	
	public void readDataBase() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
	}
	
	public void write_name(ArrayList<CountryName> country_name_list) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
			pstmt = connect.prepareStatement("insert into country_name (codeISO3, codeISO2, OfficialName, ListName, ShortName) values (?, ?, ?, ?, ?)"); 
			int count = 0;
			for (int i = 0; i < country_name_list.size(); i++) {
				System.out.println(country_name_list.get(i).codeISO3+" "+country_name_list.get(i).codeISO2);
				pstmt.setString(1, country_name_list.get(i).codeISO3);
				pstmt.setString(2, country_name_list.get(i).codeISO2);
				pstmt.setString(3, country_name_list.get(i).OfficialName);
				pstmt.setString(4, country_name_list.get(i).ListName);
				pstmt.setString(5, country_name_list.get(i).ShortName);
				pstmt.execute();
				
				statement = connect.createStatement();
				count++;
			}
			System.out.println("count is "+count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}		
	}
	
	public void write_border(ArrayList<CountryBorder> country_border_list) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
			pstmt = connect.prepareStatement("insert into country_border (codeISO3, NcodeISO3) values (?, ?)"); 
			int count = 0;
			for (int i = 0; i < country_border_list.size(); i++) {
				
				String borderList = null;
				for (int j = 0; j < country_border_list.get(i).NcodeISO3.size(); j++) {
					if (borderList == null)
						borderList = country_border_list.get(i).NcodeISO3.get(j);
					else 
					    borderList = borderList+","+country_border_list.get(i).NcodeISO3.get(j);
				}
				pstmt.setString(1, country_border_list.get(i).codeISO3);
				pstmt.setString(2, borderList);
				pstmt.execute();
				statement = connect.createStatement();
				count++;
			}
			System.out.println("count is "+count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}		
	}

	public void write_area(ArrayList<CountryArea> country_area_list) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
			pstmt = connect.prepareStatement("insert into country_area (codeISO3, Total, Unit, Year) values (?, ?, ?, ?)"); 
			int count = 0;
			for (int i = 0; i < country_area_list.size(); i++) {
				pstmt.setString(1, country_area_list.get(i).codeISO3);
				pstmt.setString(2, country_area_list.get(i).Total.toString());
				pstmt.setString(3, country_area_list.get(i).Unit);
				pstmt.setString(4, country_area_list.get(i).Year.toString());
				pstmt.execute();
				statement = connect.createStatement();
				count++;
			}
			System.out.println("count is "+count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}		
	}
		
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		int count = 0;
		try {
		  while (resultSet.next()) {
			String codeISO3 = resultSet.getString(1);
			result_array.add(codeISO3);
			count++;
		  }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void writeResultSet_ListName (ResultSet resultSet) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
			pstmt = connect.prepareStatement("SELECT ListName from country_name WHERE codeISO3 = ?");
			result_array.clear();
			while (resultSet.next()) {
				//System.out.print(resultSet.getString(1)+" ");
				pstmt.setString(1, resultSet.getString(1));
				writeResultSet(pstmt.executeQuery());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
	}
	
	public void query (String query_string) {
		
		String[] query_parse = query_string.split(",");
		if (query_parse == null)
			return;
		for (int i = 100; i < query_parse.length; i++)
			System.out.print(query_parse[i]+" ");
		System.out.println();
		if (query_parse[1].equals("country_area")) {
			query_area (query_parse[0], query_parse[1], query_parse[2], (query_parse[3].equals("desc") ? "desc" : "asc"), query_parse[4]);
		} else if (query_parse[0].equals("country_name")) {
			
		} else if (query_parse[0].equals("country_border")) {
            query_border(query_parse[1]);
		} else if (query_parse[0].equals("check_connection")){
			System.out.println(query_parse[1]+" "+query_parse[2]+" "+check_connection(query_parse[1],query_parse[2]));
		} else {
			return;
		}
		
	}
	
	private void query_area (String param, String table, String type, String order, String limit) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");
			String SQLstring = "SELECT codeISO3 FROM "+table+" "+type+" "+param+" "+order+" LIMIT "+limit;
			System.out.println(SQLstring);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(SQLstring);
			writeResultSet_ListName(resultSet);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
	}
	
	private void query_name (String input) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
	}
	
	public void build_border_graph () {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");			
			String SQLstring = "SELECT codeISO3 FROM country_name";
			statement = connect.createStatement();
			ResultSet resultSet2 = statement.executeQuery(SQLstring);
			while (resultSet2.next()) {
				query_border (resultSet2.getString(1));
				graph_list.add(result_array);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		
	}
	
	private void query_border (String input) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/country_details?"
			              + "user=root&password=root");			
			String SQLstring = "SELECT NcodeISO3 FROM country_border WHERE codeISO3 = '"+input+"'";
			statement = connect.createStatement();
			resultSet = statement.executeQuery(SQLstring);
			result_array = new ArrayList<String>();
            result_array.add(input);
			while (resultSet.next()) {
				String[] temp = resultSet.getString(1).split(",");
				for (int i = 0; i < temp.length; i++)
                {	
                	result_array.add(temp[i]);
                }
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
	}
	
    private void close () {
        try {
            if (resultSet != null) {
              resultSet.close();
            }

            if (statement != null) {
              statement.close();
            }

            if (connect != null) {
              connect.close();
            }
          } catch (Exception e) {

          }
    }
    
    public boolean check_connection (String a, String b) {
        ArrayList<String> connection_list = new ArrayList<String>();
        ArrayList<String> list_index0 = new ArrayList<String>();
        for ( int i = 0; i < graph_list.size(); i++)
        {  
        	if (graph_list.get(i).size() != 0)
        		list_index0.add(graph_list.get(i).get(0));
        }	
        
        if ( (list_index0.indexOf(a) == -1) || (list_index0.indexOf(b) == -1) )
        	return false;
        connection_list.add(a);
        int list_index = 0;
        while (true) {
        	String temp = connection_list.get(list_index);
        	if (connection_list.indexOf(b) != -1)
        		return true;
        	int temp_index = list_index0.indexOf(temp);
        	for (int i = 0; i < graph_list.get(temp_index).size(); i++ ) {
        		String temp_sub = graph_list.get(temp_index).get(i);
        		if (connection_list.indexOf(temp_sub) == -1) {
        			connection_list.add(temp_sub);
        		}
        	}
        	list_index++;
        	if (list_index >= connection_list.size())
        	    break;
        }
        return false;
    }
    
}
