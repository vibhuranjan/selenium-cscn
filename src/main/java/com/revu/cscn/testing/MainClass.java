package com.revu.cscn.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.revu.cscn.utils.CommonUtil;

public class MainClass {

	public static void main(String[] args) throws Exception {
		List<String> listOfUrls = CommonUtil.readDataFromFile("C:\\Users\\vibhu.ranjan\\luna\\workspace\\funke\\com.revu.cscn.testing\\Urls_QA.xlsx");
		/*
		for(String urlString : listOfUrls){
			URL url = new URL(urlString);
			String responseOfUrl = isLinkBroken(url);
			System.out.println("[Status]:: "+urlString+" :: "+responseOfUrl);
		}
		*/
		checkVanityUrl(listOfUrls);
		
	}

	public static String isLinkBroken(URL url) throws Exception{
		String response = "";
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try{
			connection.connect();
			response = connection.getResponseMessage();
			connection.disconnect();
			return response;
		}catch (Exception exp){
			return exp.getMessage();
		}
	}
	
	public static List<String> checkVanityUrl(List<String> listOfUrls){
		List<String> listOfTitles = new ArrayList<String>();
		System.setProperty("webdriver.chrome.driver", "./tools/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		for(String urlString : listOfUrls){
			driver.get(urlString);
			listOfTitles.add(driver.getTitle());
			System.out.println("[Status]:: "+urlString+" :: "+driver.getTitle());
		}
		driver.close();
		return listOfTitles;
	}
}
