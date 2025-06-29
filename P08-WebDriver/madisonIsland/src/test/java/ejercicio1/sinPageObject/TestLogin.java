package ejercicio1.sinPageObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    WebDriver driver;

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
    }
    @AfterEach
    public void closeDriver(){
        //Cerramos la instancia del navegador
        driver.close();
    }

    @Test
    public void loginOk(){

        //Verificamos que el titulo de la pagina es el correcto
        assertEquals("Madison Island", driver.getTitle());
        //Seleccionamos Account
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        //Seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals("Customer Login", driver.getTitle());
        //Selecciono el campo email
        driver.findElement(By.id("email")).sendKeys("alvaropedrenorubio@gmail.com");

        driver.findElement(By.id("send2")).click();
        WebElement mensajeError = driver.findElement(By.id("advice-required-entry-pass"));
        assertEquals("This is a required field.", mensajeError.getText());

        driver.findElement(By.id("pass")).sendKeys("ppssPassword123");
        driver.findElement(By.id("send2")).click();
        assertEquals("My Account", driver.getTitle());

    }

    @Test
    public void loginFailed(){
        //Verificamos que el titulo de la pagina es el correcto
        assertEquals("Madison Island", driver.getTitle());
        //Seleccionamos Account
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        //Seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals("Customer Login", driver.getTitle());
        //Selecciono el campo email
        driver.findElement(By.id("email")).sendKeys("alvaropedrenorubio@gmail.com");

        driver.findElement(By.id("pass")).sendKeys("passWordIncorrecto");
        driver.findElement(By.id("send2")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement mensajeError = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li/ul/li/span"));
        assertEquals("Invalid login or password.", mensajeError.getText());
    }
}