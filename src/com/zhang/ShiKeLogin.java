package com.zhang;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ShiKeLogin {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:\\01.java\\eclipse+selenium\\01\\chromedriver.exe");

		int d = 0;
		int c = 0;
		int norequest = 0;
		int reuqest = 0;
		WebElement price = null;

		WebDriver driver = new ChromeDriver();
		//到试客联盟页面
		driver.get("http://list.shikee.com/");
		Thread.sleep(3);

		//获取登录点，并点击
		WebElement baiduLogin = driver.findElement(By.linkText("登录"));
		baiduLogin.click();
		Thread.sleep(1000);//加载页面

		//点击图片，使用账户密码登录iconimg pc
		driver.findElement(By.cssSelector("div[class=login-hd] a[class=login-hd-switch]")).click();
		Thread.sleep(1000);

//        输入账号密码，并点击登录
		driver.findElement(By.name("username")).sendKeys("13298102001");
		driver.findElement(By.name("password")).sendKeys("562987458jie");
		driver.findElement(By.id("J_submit")).click();
		Thread.sleep(1000);

		//往输入框输入查询条件//keySearch/
		//driver.findElement(By.name("keyword")).sendKeys("花呗");
		//WebElement baiduLogin3 = driver.findElement(By.linkText("登录"));
		//baiduLogin3.click();
		//Thread.sleep(300);

		//到达花呗页面 1.花6呗最近 2，花呗升序  3。花呗降序
//		http://list.shikee.com/list-1.html?type=1&cate=0&posfree=0&try_order=0&try_type=0&sort=0&keyword=%E8%8A%B1%E5%91%97
//		http://list.shikee.com/list-1.html?type=1&try_type=0&posfree=0&try_order=1&cate=0&try_type=0&qr_code=0&sort=asc&keyword=%E8%8A%B1%E5%91%97
//		http://list.shikee.com/list-1.html?type=1&try_type=0&posfree=0&try_order=1&cate=0&try_type=0&qr_code=0&sort=desc&keyword=%E8%8A%B1%E5%91%97
		//需要查询的页数
		int num = 30;
		for(int s = 0; s<num;s++) {

			//到达每一个花呗的页面
			//获取花呗从高到低
//			driver.get("http://list.shikee.com/list-"+s+".html?type=1&try_type=0&posfree=0&try_order=1&cate=0&try_type=0&qr_code=0&sort=desc&keyword=%E8%8A%B1%E5%91%97");
			//获取花呗最新
			driver.get("http://list.shikee.com/list-"+s+".html?type=1&try_type=0&posfree=0&try_order=0&cate=0&try_type=0&qr_code=0&sort=desc&keyword=%E8%8A%B1%E5%91%97");
			//获取所有
//			driver.get("http://list.shikee.com/list-"+s+".html?type=1&cate=0&posfree=0&try_order=0&try_type=0&qr_code=0&sort=desc");
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

			for(Entry<String, String> entry : map.entrySet()){

				//判断是否是禁止信用卡试用的
				if(entry.getValue().indexOf("禁")<0 && entry.getValue().indexOf("不")<0) {
					//若加载不出来这个页面，捕获异常执行下面
					try {
						driver.get(entry.getKey());
						Thread.sleep(1000);
						price = driver.findElement(By.id("J_backMoney"));

					} catch (Exception e) {
//						driver.navigate().refresh();
						Thread.sleep(2000);
						driver.get(entry.getKey());
						System.out.println("跳过Exception");
						break;
						// TODO: handle exception
					}
					float itemPrice = Float.parseFloat(price.getText());
					//判断价格是否大于288
					if(itemPrice >= 100 ) {
						d++;
						System.out.print("申请的第"+d+"件,");
						WebElement quest = driver.findElement(By.id("J_btn"));
						//String commodityName = driver.findElement(By.xpath("h2[@class='floatL']")).getText();
						String text = quest.getText();

						//判断是否为已经试用过的商品
						if(text.equals("申请试用")) {
							c++;
							quest.click();
							Thread.sleep(500);
							norequest = 0;
							reuqest++;
							System.out.println("通过的第"+(reuqest)+"件商品,名称是:"+entry.getValue()+"价格是："+itemPrice);
						}else {
							norequest++;
//							System.out.print(norequest+",");
							System.out.println("已经申请的第"+norequest+"件商品");
							if(norequest == 20) {
								driver.close();
							}
						}
					}
				}
			}
			System.out.println("*************截止第"+(s+1)+"页，共申请"+c+"个");

			System.out.println("**************这是第"+(s+2)+"页****************");

		}
		Thread.sleep(3000);
		driver.close();
	}
}
