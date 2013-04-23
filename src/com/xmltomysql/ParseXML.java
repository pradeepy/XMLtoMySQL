package com.xmltomysql;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

class ParseXML {
	ArrayList<CountryName> country_name_list = new ArrayList<CountryName>();
	ArrayList<CountryArea> country_area_list = new ArrayList<CountryArea>();
	ArrayList<CountryBorder> country_border_list = new ArrayList<CountryBorder>();
	
	public void xmlparse () throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); 
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		XPath xpath;
		
		XPathExpression expr = null;
		Object result = null;
		NodeList nodes = null;
		org.w3c.dom.Document doc = null, doc_area = null, doc_neighbour = null;	
		
		try {
			String codeISO3 = null;
	        String codeISO2 = null;
	        String OfficialName = null;
	        String ShortName = null;
	        String ListName = null;
	        
	        float Total = 0;
	        String Unit = null;
	        int Year = 0;
	        
	        
	        xpath = XPathFactory.newInstance().newXPath();
	        expr = xpath.compile("//*");
	        
	        doc = builder.parse("http://www.fao.org/countryprofiles/geoinfo/ws/allCountries/EN/");
			result = expr.evaluate(doc, XPathConstants.NODESET);
	        nodes = (NodeList) result;
	        for (int i = nodes.getLength()+1; i < nodes.getLength(); i++) {
	        	String node_name = nodes.item(i).getNodeName();
	        	if (node_name == "codeISO3") {
	        	    codeISO3 = nodes.item(i).getTextContent();
	        	} else if (node_name == "codeISO2") {
		            codeISO2 = nodes.item(i).getTextContent();
		        } else if (node_name == "nameOfficialEN") {
		            OfficialName = nodes.item(i).getTextContent();
		        } else if (node_name == "nameShortEN") {
		            ShortName = nodes.item(i).getTextContent();
		        } else if (node_name == "nameListEN") {
		            ListName = nodes.item(i).getTextContent();
		        } else if (node_name == "FAO_MEMBERS") {
	        		country_name_list.add(new CountryName(codeISO3, codeISO2, OfficialName, ShortName, ListName));
	        		codeISO3 = null; codeISO2 = null; OfficialName = null; ShortName = null; ListName = null;
	        	}
	        }
	        
	        for (int i = nodes.getLength()+1; i < country_name_list.size(); i++) {
	        	codeISO3 = country_name_list.get(i).codeISO3;
	        	try {
	        	    doc_area = builder.parse("http://www.fao.org/countryprofiles/geoinfo/ws/countryStatistics/countryArea/"+codeISO3);
				    result = expr.evaluate(doc_area, XPathConstants.NODESET);
		            nodes = (NodeList) result;
	        	} catch (Exception e) {
	        		System.out.println(e.getMessage());
	        		continue;
	        	}
		        for (int j = 0; j < nodes.getLength(); j++) {
		        	String node_name = nodes.item(j).getNodeName();
		        	if (node_name == "codeISO3") {
			            codeISO3 = nodes.item(j).getTextContent();
			        }  else if (node_name == "Total") {
			            Total = Float.parseFloat(nodes.item(j).getTextContent());
			        } else if (node_name == "Unit") {
			            Unit = nodes.item(j).getTextContent();
			        } else if (node_name == "Year") {
			            Year = Integer.parseInt(nodes.item(j).getTextContent());
			        } else if (node_name == "Source") {
		        	    country_area_list.add(new CountryArea(codeISO3, new Float (Total), Unit, new Integer (Year)));
		        	    codeISO3 = null; Total = 0; Unit = null; Year = 0;
		        	}
		        }
		        
		        codeISO3 = country_name_list.get(i).codeISO3;
		        try {
		            doc_neighbour = builder.parse("http://www.fao.org/countryprofiles/geoinfo/ws/borderWith/"+codeISO3+"/EN");
				    result = expr.evaluate(doc_neighbour, XPathConstants.NODESET);
		            nodes = (NodeList) result;
		        } catch (Exception e) {
		        	System.out.println(e.getMessage());
		        	continue;
		        }
		        ArrayList<String> neighbour_list = new ArrayList<String>();
		        for (int j = 0; j < nodes.getLength(); j++) {
		        	String node_name = nodes.item(j).getNodeName();
		        	if (node_name == "codeISO3") {
		        		if (!nodes.item(j).getTextContent().equals(codeISO3))
		        		    neighbour_list.add(nodes.item(j).getTextContent());
		        	}
		        }
		        country_border_list.add(new CountryBorder(codeISO3, neighbour_list));
		        
	        }
	        
	        System.out.println(country_area_list.size()+" "+country_name_list.size()+" "+country_border_list.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
	}
	
	public void populate_table () {
		
	}
	
}


