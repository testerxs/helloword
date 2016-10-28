package test.TestSelenium;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;




public class TestSeleniumTry {
	
	public static void saveXcarCookie(String url,WebDriver driver){
		driver.get(url);
		
		System.out.println("title is:"+driver.getTitle());
		//WebElement element=driver.findElement(By.id("kw"));
		//element.sendKeys("test");
		//WebElement element1=driver.findElement(By.id("su"));
		//element1.submit();
	
		WebElement element1=driver.findElement(By.name("username"));
		element1.sendKeys("15828245881");
		WebElement element2=driver.findElement(By.name("password"));
		element2.sendKeys("0512shmily");
		WebElement element3=driver.findElement(By.name("btn"));
		element3.click();
		File cookiefile=new File("testcookie.txt");
		try{
			cookiefile.delete();
			cookiefile.createNewFile();
			FileWriter filewriter=new FileWriter(cookiefile);
			BufferedWriter bufferwriter=new BufferedWriter(filewriter);
			for(Cookie cookie:driver.manage().getCookies()){
				bufferwriter.write(cookie.getName()+";"
						+cookie.getValue()+";"
						+cookie.getDomain()+";"
						+cookie.getPath()+";"
						+cookie.getExpiry()+";"
						+cookie.isSecure());
				bufferwriter.newLine();
			}
			bufferwriter.flush();
			bufferwriter.close();
			filewriter.close();	
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		driver.quit();
	
		
	}
	public static void loginWithCookie(String url,WebDriver driver){
		driver.get(url);
		try{
			File cookieFile=new File("testcookie.txt");
			FileReader fr=new FileReader(cookieFile);
			BufferedReader br=new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null){
				StringTokenizer stringTokenizer=new StringTokenizer(line,";");
				while(stringTokenizer.hasMoreTokens()){
					String name=stringTokenizer.nextToken();
					String value=stringTokenizer.nextToken();
					String domain=stringTokenizer.nextToken();
					String path=stringTokenizer.nextToken();
					Date expiry=null;
					String dt;
					if(!(dt=stringTokenizer.nextToken()).equals("null")){
						expiry=new Date(dt);
						
					}
					boolean IsSecure=new Boolean(stringTokenizer.nextToken()).booleanValue();
					Cookie cookie=new Cookie(name,value,domain,path,expiry,IsSecure);
					driver.manage().addCookie(cookie);
					
				}
				
			}
			
			
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	driver.get(url);
	}

	public static void main(String[] args) throws InterruptedException, IOException{
		FileHandler.createDir(new File("\\filehandler"));
		
		System.setProperty("webdrivers.firefox.bin","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		WebDriver driver=new FirefoxDriver();
		String url="http://my.xcar.com.cn/logging.php?action=login&referer=%2F";
		//saveXcarCookie(url,driver);
		loginWithCookie(url,driver);
		driver.navigate().to("www.baidu.com");
		driver.navigate().back();
		//driver.navigate().forward();
		File srcFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Thread.sleep(3000);
		FileUtils.copyFile(srcFile, new File("screen.jpg"));
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
