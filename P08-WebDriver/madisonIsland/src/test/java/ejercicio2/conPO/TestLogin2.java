package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin2 {
    WebDriver driver;
    HomePage poHomePage;
    MyAccountPage poMyAccountPage;

    CustomerLoginPage poCustomerLoginPage;

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
        poHomePage = new HomePage(driver);
    }

    @AfterEach
    public void close(){
        driver.close();
    }
    @Test
    public void test_Login_Correct(){
        //Tiene que hacer lo mismo que loginOk
        assertEquals("Madison Island", driver.getTitle());
        poCustomerLoginPage = poHomePage.openLoginPage();
        assertEquals("Customer Login", driver.getTitle());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        poCustomerLoginPage.writeEmail("alvaropedrenorubio@gmail.com");
        poCustomerLoginPage.writePassword("ppssPassword123");
        poCustomerLoginPage.clickSubmit();
        assertEquals("My Account", driver.getTitle());

    }
    @Test
    public void test_Login_Incorrect(){
        //Tiene que hacer lo mismo que loginFailed
        assertEquals("Madison Island", driver.getTitle());
        poCustomerLoginPage = poHomePage.openLoginPage();
        assertEquals("Customer Login", driver.getTitle());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement mensajeError = poCustomerLoginPage.getMensajeError("alvaropedrenorubio@gmail.com");
        assertEquals("Invalid login or password.", mensajeError.getText());
    }

}