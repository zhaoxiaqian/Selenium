package com.zhang;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

 /**
  *试客联盟自动申请
  * @Author:  GAOSHU
  * @Date:   2020/9/29
  * @version  1.0
  */
public class Goods {

	//需要申请的商品
	private static final String YOUR_USERNAME = "123456";
	private static final String YOUR_PASSWORD = "abc123";

	//需要申请的商品(关键字查询)
	private static final String YOU_NEED_SHOP = "垃圾袋";
	//申请商品顺序(最新排序、)
	//需要申请商品的价格范围(最高价格、最低价格(元))
	private static final Integer MAX_PRICE = 100;
	private static final Integer MIN_PRICE = 0;

	//设置要申请的页数
	private static final Integer PAGENUMBER = 20;
	//官网地址
	private static final String WEBSITE_ADDRESS = "http://list.shikee.com/";
	//chromedriver存放地址(谷歌驱动插件)
	private static final String CHROMEDRIVER_ADDRESS = "config/chromedriver.exe";
	//排序方式，0.最新排序 1.价值 2.人气 3.份数 4.剩余份数
	private static final Integer SORT_MODE = 0;

	public static void main(String[] args) throws InterruptedException {
		//加载谷歌驱动
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_ADDRESS);

		WebDriver driver = new ChromeDriver();
		//到试客联盟页面
		driver.get(WEBSITE_ADDRESS);
		Thread.sleep(10);

		//获取登录点，并点击
		WebElement baiduLogin = driver.findElement(By.linkText("登录"));
		baiduLogin.click();
		//加载页面
		Thread.sleep(1000);

		//点击图片，使用账户密码登录iconimg pc
		driver.findElement(By.cssSelector("div[class=login-hd] a[class=login-hd-switch]")).click();
		Thread.sleep(1000);

//        输入账号密码，并点击登录
		driver.findElement(By.name("username")).sendKeys(YOUR_USERNAME);
		driver.findElement(By.name("password")).sendKeys(YOUR_PASSWORD);
		driver.findElement(By.id("J_submit")).click();
		Thread.sleep(1000);
		//需要查询的页数
		int requestShopNum = 0;
		int norequest = 0;
		int reuqest = 0;
		int numShop = 0;




		String searchName = "";
		try {
			searchName=URLEncoder.encode(YOU_NEED_SHOP,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		for(int s = 0; s<PAGENUMBER;s++) {

			driver.get("http://list.shikee.com/list-"+s+".html?type=0&cate=0&posfree=0&try_order=0&try_type="+SORT_MODE+"&qr_code=0&sort=desc&pkey=465e54672f4&keyword="+searchName);
			Thread.sleep(1000);

			List<WebElement> elements = driver.findElements(By.xpath("//div[@class='item-box']"));
			List<String> linkLst = new ArrayList<>();
			HashMap<String,String> map = new HashMap();

			//获取每件商品链接
			for(int i = 0;i<elements.size();i++) {
				String href = elements.get(i).findElement(By.tagName("a")).getAttribute("href");
				String name = elements.get(i).findElement(By.xpath("h4/a")).getText();
				map.put(href, name);
			}
			System.out.println("这是第"+(s+1)+"页");

			WebElement price = null;
			for(Entry<String, String> entry : map.entrySet()){
				if(entry.getValue().indexOf(YOU_NEED_SHOP) > -1) {
					try {
						driver.get(entry.getKey());
						Thread.sleep(1000);
						price = driver.findElement(By.id("J_backMoney"));

						System.out.println("访问第"+(++numShop)+"商品");
						Thread.sleep(10000);
					} catch (Exception e) {
						Thread.sleep(2000);
						driver.get(entry.getKey());
						System.out.println("跳过Exception");
						break;
						// TODO: handle exception
					}
					float itemPrice = Float.parseFloat(price.getText());
					if(itemPrice > MIN_PRICE && itemPrice < MAX_PRICE) {
						WebElement quest = driver.findElement(By.id("J_btn"));
						String text = quest.getText();
						if(text.equals("申请试用")) {
							requestShopNum++;
							quest.click();
							Thread.sleep(500);
							norequest = 0;
							reuqest++;
							System.out.println("这是第"+(s+1)+"页，这是申请的第"+(reuqest)+"件商品,名称是:"+entry.getValue()+"价格是："+itemPrice);
						}else {
							norequest++;
							System.out.print(norequest+",");
							//当已试用连续大于5件，就停止
							if(norequest == 5) {
								driver.close();
							}
						}
					}
				}
			}
		}
		Thread.sleep(3000);
		System.out.println("共申请"+requestShopNum+"个");
		driver.close();
	}
}
