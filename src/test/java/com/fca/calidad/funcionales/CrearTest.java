package com.fca.calidad.funcionales;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class CrearTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
	WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testCrear() throws Exception {
      driver.get("https://mern-crud-mpfr.onrender.com/");
      assertTrue("La página principal no cargó correctamente", isElementPresent(By.xpath("//div[@id='root']/div/div[2]/button")));
      driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
      assertTrue("El formulario de creacion no aparecio", isElementPresent(By.name("name")));
      
      driver.findElement(By.name("name")).click();
      driver.findElement(By.name("name")).clear();
      driver.findElement(By.name("name")).sendKeys("Dafne Cruz Aguilar");
      assertEquals("El nombre no se ingresó correctamente", "Dafne Cruz Aguilar", driver.findElement(By.name("name")).getAttribute("value"));
      
      driver.findElement(By.name("email")).click();
      driver.findElement(By.name("email")).clear();
      driver.findElement(By.name("email")).sendKeys("dafcruz@gmail.com");
      assertEquals("El correo no se ingresó correctamente","dafcruz@gmail.com", driver.findElement(By.name("email")).getAttribute("value"));
      
      driver.findElement(By.name("age")).click();
      driver.findElement(By.name("age")).clear();
      driver.findElement(By.name("age")).sendKeys("21");
      assertEquals("La edad no se ingreso correctamente", "21", driver.findElement(By.name("age")).getAttribute("value"));
      
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[2]")).click();
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Female'])[1]/following::span[1]")).click();
      assertTrue("No se seleccionó correctamente el género 'Female'", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Female'])[1]/following::span[1]")));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
      assertTrue("El mensaje de exito no aparecio", isElementPresent(By.xpath("//i")));
      driver.findElement(By.xpath("//i")).click();
      assertTrue("El mensaje no se cerro correctamente", 
                 !isElementPresent(By.xpath("//i")));
  }//agregue assert para validar y mensajes nada mas

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}



