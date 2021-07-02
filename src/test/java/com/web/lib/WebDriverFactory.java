package com.web.lib;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
	static WebDriver driver = null;
	
	public static WebDriver openBrowser (String url) {
		try {
			System.setProperty("webdriver.chrome.silentOutput","true");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "Drivers" + File.separator + "chromedriver");
			ChromeOptions opt = new ChromeOptions();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("chromeOptions", opt);
			capabilities.setBrowserName("chrome");
			opt.addArguments("--no-sandbox");
			opt.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
			driver = new ChromeDriver(opt);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.navigate().to(url);
			return driver;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static void tearDown () {
		try {
			driver.quit();
		} catch (Throwable t) {
		}
	}
	
	public static String getCurrentUrl () {
		try {
			return driver.getCurrentUrl();
		} catch (Throwable t) {
			return null;
		}
	}
	
	public static void sendKeys(String locator, String value){
		try{
			if(isElementExist(locator)){
				driver.findElement(By.xpath(locator)).sendKeys(value);
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	public static void click (String locator) {
		try {
			driver.findElement(By.xpath(locator)).click();
		} catch (Throwable t) {
			t.printStackTrace();
		}
 	}
	
	public static boolean isElementExist(String xpathElement){
		int nTime = 1;
		boolean isElementExist=false;
		while(nTime <= 100){
			try{
				if(driver.findElements(By.xpath(xpathElement)).size()> 0){
					nTime = 101;
					isElementExist = true;
					break;
				}else{
					nTime = nTime + 1;
					isElementExist = false;
				}
			}catch(Exception e){
				nTime = nTime + 1;
				isElementExist = false;
			}
		}
		if (!isElementExist) {
			System.out.println(xpathElement + " is NOT present in DOM============web");
		}
		return isElementExist;
	}
	
	public static boolean isElementExist(String xpathElement, int timeout){
		int nTime = 1;
		boolean isElementExist=false;
		while(nTime <= timeout){
			try{
				if(driver.findElements(By.xpath(xpathElement)).size()> 0){
					nTime = timeout + 1;
					isElementExist = true;
					break;
				}else{
					nTime = nTime + 1;
					isElementExist = false;
				}
			}catch(Exception e){
				nTime = nTime + 1;
				isElementExist = false;
			}
		}
		if (!isElementExist) {
			System.out.println(xpathElement + " is NOT present in DOM============web");
		}
		return isElementExist;
	}
}
