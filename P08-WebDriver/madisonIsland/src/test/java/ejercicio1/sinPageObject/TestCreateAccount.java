package ejercicio1.sinPageObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestCreateAccount {
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

    @Tag("OnlyOnce")
    @Test
    public void createAccount(){
        //Verifficamos que el titulo de la pagina es el correcto
        assertEquals("Madison Island", driver.getTitle());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //Seleccionamos account
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        //Seleccionamos el hiperenlace login
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();
        //Verificamos que el titulo de la pagina es Customer Login
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals("Customer Login", driver.getTitle());
        //Seleccionamos el boton Create Account
        driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[1]/div[2]/a/span/span")).click();
        //Verificamos el titulo Create new Customer Account
        assertEquals("Create New Customer Account", driver.getTitle());
        //Escribimos en los textboc
        driver.findElement(By.id("firstname")).sendKeys("Alvaro");
        driver.findElement(By.id("middlename")).sendKeys("Pedreño");
        driver.findElement(By.id("lastname")).sendKeys("Rubio");
        driver.findElement(By.id("email_address")).sendKeys("alvaropedren32o3232nuevo@gmail.com");
        driver.findElement(By.id("password")).sendKeys("ppssPassword123");
        driver.findElement(By.id("confirmation")).sendKeys("ppssPassword123");

        //Los vuelvo a enviar
        driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals("My Account", driver.getTitle());
    }




}