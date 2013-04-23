package com.xmltomysql;
import java.util.ArrayList;

class CountryBorder
{
	String codeISO3;
	ArrayList<String> NcodeISO3 = new ArrayList<String>();
    
	public CountryBorder()
	{	
	}
	
	public CountryBorder(String a, String b)
	{
		codeISO3 = a;
		if (!b.equals(a))
			NcodeISO3.add(b);
    }
	
	public CountryBorder (String a, ArrayList<String> b) {
		codeISO3 = a;
		NcodeISO3 = (ArrayList<String>) b.clone();
	}
	
	void populate(String a, String b)
	{
		if(a == "codeISO3orig")
	      codeISO3 = b;
        if(a == "codeISO3")
          if (!codeISO3.equals(b)) 	
          NcodeISO3.add(b);
    }
	
	void display()
	{
		System.out.println("Country code is "+codeISO3);
		System.out.println("Neighbour code is ");
		for (String s : NcodeISO3)
			System.out.print(s+" ");
	}
	
}


