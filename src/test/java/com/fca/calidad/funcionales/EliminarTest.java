package com.fca.calidad.funcionales;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class EliminarTest {
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
  public void testEliminar() throws Exception {
    driver.get("https://mern-crud-mpfr.onrender.com/");
    assertTrue("La tabla de registros no se cargó correctamente", isElementPresent(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr[4]/td[5]/button[2]")));
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr[4]/td[5]/button[2]")).click();
    assertTrue("El botón de confirmación de eliminación no apareció", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='User pruebadafne'])[2]/following::button[1]")));
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='User pruebadafne'])[2]/following::button[1]")).click();
    // Validar que el texto si se borro (dejo mi usuario registrado¿¿)
    assertTextNotFound("pruebadafne", "El texto 'pruebadafne' sigue presente en la página después de la eliminación");
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

  // Método para mi texto no encontrado (adicional)
  private void assertTextNotFound(String text, String errorMessage) {
    boolean isTextPresent = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0;
    assertFalse(errorMessage, isTextPresent);
  }
}
