package com.fca.calidad.funcionales;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class BuscarTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testBuscar() throws Exception {
    driver.get("https://mern-crud-mpfr.onrender.com/");
    assertTrue("El botón para agregar un usuario no esta", isElementPresent(By.xpath("//div[@id='root']/div/div[2]/button")));
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
    assertTrue("El formulario para agregar un usuario no aparece", isElementPresent(By.name("name")));
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("Dafne Cruz");
    assertEquals("El nombre no se puso correctamente", "Dafne Cruz", driver.findElement(By.name("name")).getAttribute("value"));
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("dafcruzaguilar@gmail.com");
    assertEquals("El correo electronico no se puso correctamente", "dafcruzaguilar@gmail.com", driver.findElement(By.name("email")).getAttribute("value"));
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[2]/following::div[1]")).click();
    assertTrue("No se selecciono correctamente el género (Male)", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[2]/following::div[1]")));
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
    assertTrue("El mensaje de error de (ya existe) no aparecio", isElementPresent(By.xpath("//div[contains(text(),'already exists')]")));
    String mensajeError = driver.findElement(By.xpath("//div[contains(text(),'already exists')]")).getText();
    assertTrue("El mensaje no indica que el usuario ya existe. Mensaje actual: " + mensajeError, mensajeError.contains("already exists"));
    driver.findElement(By.xpath("//i")).click();
    assertTrue("El mensaje emergente no se cerró correctamente", !isElementPresent(By.xpath("//div[contains(text(),'already exists')]")));
    driver.findElement(By.xpath("//div[@id='root']/div")).click();
  }

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

