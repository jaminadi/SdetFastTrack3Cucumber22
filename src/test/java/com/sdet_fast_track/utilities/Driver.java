package com.sdet_fast_track.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>() ;

    private Driver() {
    }

   /* this get driver methods are for specifying browser type with tags tests, see also hooks @Before and Configuration Reader class
   //you just need to put @firefox tag on top of scenario and if you dont specify, other tests will run in chrome


    public static WebDriver getDriver(){
        String browser = ConfigurationReader.getProperty("browser");
        return getDriver(browser);
    }
     public static  WebDriver getDriver(String browserType) {
        if (driverPool.get() == null) {
           setDriver(browserType);
            }
             return driverPool.get();
             }

            public static void setDriver(String browserType){
                synchronized (Driver.class) {
                switch (browserType) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        ChromeOptions.setAcceptInsecureCerts(true);
                        driverPool.set(new ChromeDriver));
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driverPool.set(new FirefoxDriver());
                        break;
                        }
                        }
                        }

    */


    public static  WebDriver getDriver() {
        if (driverPool.get() == null) { // if driver does not exist
            synchronized (Driver.class) { // create it
                String browser = ConfigurationReader.getProperty("browser"); // based on the browser type

                switch (browser) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        driverPool.set(new ChromeDriver());
                        driverPool.get().manage().window().maximize();
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driverPool.set(new FirefoxDriver());
                        driverPool.get().manage().window().maximize();
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        break;

                }
            }
        }

        //This same driver will be returned every time we call Driver.getDriver() method
        return driverPool.get();

    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

}