package com.xmltomysql;

class CountryName
{   
	String codeISO3;
	String codeISO2;
	String OfficialName;
	String ShortName;
	String ListName;
	static int count = 0;
	  
	public CountryName () {
		
	}
	
    public CountryName(String codeISO3, String codeISO2, String OfficialName, String ShortName, String ListName) 
	{
	  this.OfficialName = OfficialName;
	  this.ShortName = ShortName;
	  this.ListName = ListName;
	  this.codeISO2 = codeISO2;
	  this.codeISO3 = codeISO3;
	}
	  
	public void populate (String Ptype, String PName)
	{ if (Ptype == "nameOfficialEN") 
		OfficialName = PName;
	  if (Ptype == "nameShortEN")
		ShortName = PName;
	  if (Ptype == "nameListEN")
		ListName = PName;
	  if (Ptype == "codeISO2")
		codeISO2 = PName;
	  if (Ptype == "codeISO3")
	   	codeISO3 = PName;
      if (Ptype == "self_governing")
    	count++;
    }  
	  
    public void display ()
	{ System.out.println("OfficialName is " +OfficialName +" ");
	  System.out.println("ShortName is " +ShortName);
	  System.out.println("ListName is " +ListName);
	  System.out.println("codeISO2 is " +codeISO2);
	  System.out.println("codeISO3 is " +codeISO3);
    }
}

