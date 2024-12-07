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

public class ModificarTest {
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
  public void testModificar() throws Exception {
      driver.get("https://mern-crud-mpfr.onrender.com/");
      assertTrue("El botón para crear un nuevo usuario no está presente", isElementPresent(By.xpath("//div[@id='root']/div/div[2]/button")));
      driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
      assertTrue("la ventana para agregar un nuevo usuario no aparecio", isElementPresent(By.name("name")));
      driver.findElement(By.name("name")).click();
      driver.findElement(By.name("name")).clear();
      driver.findElement(By.name("name")).sendKeys("Dafne Cruz");
      assertEquals("El nombre no se ingresO correctamente", "Dafne Cruz", driver.findElement(By.name("name")).getAttribute("value"));
      driver.findElement(By.name("email")).click();
      driver.findElement(By.name("email")).clear();
      driver.findElement(By.name("email")).sendKeys("comprasfamiliares24@gmail.com");
      assertEquals("El correo electrónico no se ingreso correctamente", "comprasfamiliares24@gmail.com", driver.findElement(By.name("email")).getAttribute("value"));
      driver.findElement(By.name("age")).click();
      driver.findElement(By.name("age")).clear();
      driver.findElement(By.name("age")).sendKeys("18");
      assertEquals("La edad no se ingreso correctamente", "18", driver.findElement(By.name("age")).getAttribute("value"));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[2]/following::div[1]")).click();
      assertTrue("No se seleccionó correctamente el género 'Male'", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[2]/following::div[1]")));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
      assertTrue("El mensaje de éxito no apareció después de guardar el usuario", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Stop'])[1]/following::div[1]")));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Stop'])[1]/following::div[1]")).click();
      assertTrue("El botón para editar el usuario no aperece", isElementPresent(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")));
      driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
      driver.findElement(By.name("email")).click();
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Edit User'])[1]/following::div[1]")).click();
      driver.findElement(By.name("email")).clear();
      driver.findElement(By.name("email")).sendKeys("base250415@gmail.com");
      assertEquals("El correo electronico no se actualizo correctamente", "base250415@gmail.com", driver.findElement(By.name("email")).getAttribute("value"));
      driver.findElement(By.name("age")).click();
      driver.findElement(By.name("age")).clear();
      driver.findElement(By.name("age")).sendKeys("21");
      assertEquals("La edad no se actualizo correctamente", "21", driver.findElement(By.name("age")).getAttribute("value"));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
      assertTrue("El mensaje de Exito NO aparecio despues de actualizar el usuario", isElementPresent(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Do Not Disclose'])[1]/following::div[1]")));
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Do Not Disclose'])[1]/following::div[1]")).click();
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Stop'])[1]/following::div[1]")).click();
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
