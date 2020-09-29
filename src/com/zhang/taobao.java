package com.zhang;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

//findElement(By.id())
//findElement(By.name())
//findElement(By.className())
//findElement(By.tagName())
//findElement(By.linkText())
//findElement(By.partialLinkText())
//findElement(By.xpath())
//findElement(By.cssSelector())
public class taobao {
	public static void main(String[] args) throws InterruptedException {
		//加载驱动
		System.setProperty("webdriver.chrome.driver", "E:\\01.java\\eclipse+selenium\\01\\chromedriver.exe");




		//创建驱动对象
		WebDriver driver = new ChromeDriver();
		//到淘宝登录页面
		driver.get("https://login.taobao.com/member/login.jhtml?spm=a21bo.2017.754894437.1.5af911d9SJy6dm&f=top&redirectURL=https%3A%2F%2Fwww.taobao.com%2F");
		Thread.sleep(3);

		//获取登录点，并点击，
//      	driver.findElement(By.id("J_Quick2Static")).click();
//        Thread.sleep(1000);//加载页面

		//点击图片，使用账户密码登录iconimg pc
//        driver.findElement(By.cssSelector("div[class=login-hd] a[class=login-hd-switch]")).click();
//        Thread.sleep(1000);

//        输入账号密码，并点击登录
//		driver.findElement(By.name("TPL_username")).sendKeys("生活的喜悦33");
//		driver.findElement(By.name("TPL_password")).sendKeys("562987458jie");


		Thread.sleep(10000);
//		driver.findElement(By.id("J_SubmitStatic")).click();
//		Thread.sleep(5000);
		//输入你要下单的网址
		driver.get("https://item.taobao.com/item.htm?spm=a230r.1.14.253.20eb103aBGfwJn&id=600401002382&ns=1&abbucket=4#detail");
		Thread.sleep(2000);
		WebElement findElement = null;

		long startTime = 0L;
		long endTime = 0L;
		while(true) {
			startTime = System.currentTimeMillis();
			if(null != driver.findElement(By.className("J_LinkBuy"))) {
				driver.findElement(By.className("J_LinkBuy")).click();
				findElement = driver.findElement(By.className("J_LinkBuy"));
				break;
			}
		};
		Thread.sleep(250);
		while(true) {
			if(null != driver.findElement(By.className("go-btn"))) {
				driver.findElement(By.className("go-btn")).click();
				endTime = System.currentTimeMillis();
				break;
			}
		};

		System.out.println(endTime-startTime);
			/*System.out.println(12);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.className("J_LinkBuy")).click();
			System.err.println(13);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.className("btn")).click();;*/
		//显式等待， 针对某个元素等待
		   /* WebDriverWait wait = new WebDriverWait(driver,10,1);

		    wait.until(new ExpectedCondition<WebElement>(){
		      @Override
		      public WebElement apply(WebDriver text) {
		            return driver.findElement(By.name("J_LinkBuy"));
		          }
		    }).click();

		    System.out.println(1);

		    wait.until(new ExpectedCondition<WebElement>(){
		    	@Override
		    	public WebElement apply(WebDriver text) {
		    		return driver.findElement(By.name("go-btn"));
		    	}
		    }).click();*/


		driver.close();
	}

	/**
	 * 淘宝链接购买
	 * @param driver
	 * @param url
	 * @throws InterruptedException
	 */
	private static void getTaoBao(WebDriver driver, String url)
			throws InterruptedException {

		//等待
		try {
			//到达商品页
			driver.get(url);
			Thread.sleep(1000);

			while(true) {
				if(null != driver.findElement(By.name("J_LinkBuy"))) {
					driver.findElement(By.name("J_LinkBuy")).click();
				}
				if(null != driver.findElement(By.name("go-btn"))) {
					driver.findElement(By.name("go-btn")).click();
				}
				if(1==1) {
					break;
				}
			}
		} finally {
			driver.close();
			// TODO: handle finally clause
		}

	}
}
