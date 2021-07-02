package com.web.lib;

public class WebDriverUtils {
	public static String get_auth_code (String url) {
		WebDriverFactory.openBrowser(url);
		WebDriverFactory.sendKeys("//input[@name = 'username']", "ysbyccauto@gmail.com");
		WebDriverFactory.sendKeys("//input[@name = 'password']", "subratocts1");
		WebDriverFactory.click("//button[text() = 'Log In']");
		if (WebDriverFactory.isElementExist("//p[text() = 'APIs your consumers will love']")) {
			System.out.println("User authrization granted..");
			url = WebDriverFactory.getCurrentUrl();
		}
		WebDriverFactory.tearDown();
		return url.split("code=")[1];
	}
}
