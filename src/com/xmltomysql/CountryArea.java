package com.xmltomysql;

class CountryArea
{
	String codeISO3;
	Float Total;
	String Unit;
	Integer Year;
	
	public CountryArea()
	{	
	}
	
	public CountryArea(String codeISO3, Float Total, String Unit, Integer Year)
	{
		this.codeISO3 = codeISO3;
		this.Total = new Float (Total);
		this.Unit = Unit;
		this.Year = new Integer (Year);
    }
	
	void populate(String a, String b)
	{
		if(a == "codeISO3")
			codeISO3 = b;
		if(a == "Total")
			Total = new Float (b);
        if(a == "Unit")
		    Unit = b;
		if(a == "Year")
			Year = new Integer (b);
    }
	
	
	void display()
	{
		System.out.println("Country code is "+codeISO3);
		System.out.println("Total is "+Total);
        System.out.println("Unit is "+Unit);
		System.out.println("Year is "+Year);
	}
}


