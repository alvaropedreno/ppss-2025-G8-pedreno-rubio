package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestShoes {

    WebDriver driver;

    MyAccountPage poMyAccountPage;
    ShoesPage poShoesPage;

    ComparePage poComparePage;

    @BeforeAll
    public static void setupCookies(){
        Cookies.storeCookiesToFile("alvaropedrenorubio@gmail.com","ppssPassword123", "cookies.data");
    }

    @BeforeEach
    public void setup(){
        ChromeOptions co = new ChromeOptions();
        co.setBrowserVersion("131");
        co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        //co.addArguments("--ignore-certificate-errors");
        co.addArguments("--allow-running-insecure-content");
        //co.addArguments("--allow-insecure-localhost");
        driver = new ChromeDriver(co);
        driver.get("http://demo.magento.recolize.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Cookies.loadCookiesFromFile(driver);
        poMyAccountPage = PageFactory.initElements(driver, MyAccountPage.class);
    }


    @AfterEach
    public void teardown(){
        driver.close();
    }

    @Test
    public void compareShoes(){
        assertEquals("My Account", driver.getTitle());
        poShoesPage= poMyAccountPage.enterShoesPage();
        assertEquals("Shoes - Accessories", driver.getTitle());
        poShoesPage.clickShoesCompares(5);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        poShoesPage.clickShoesCompares(6);
        poShoesPage.clickCompare();
        poComparePage = poShoesPage.cambiarVentanaCompare();
        assertEquals("Products Comparison List - Magento Commerce", driver.getTitle());
        poComparePage.cerrarVentanaCompare();
        assertEquals("Are you sure you would like to remove all products from your comparison?",poShoesPage.clearButtonClick());
    }


}