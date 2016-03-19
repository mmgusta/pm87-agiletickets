package br.com.caelum.agiletickets.acceptance.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class SessaoPage {
	
	private static final String BASE_URL = "http://localhost:8080";
	private final WebDriver driver;
	
	public SessaoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void abrePaginaSessao(long id) {
		driver.get(BASE_URL + "/sessao/" + id);
	}
	
	public void reservarIngressoCom10PortcentAMais(int quantidade) {
		WebElement form = form();
		form.findElement(By.name("quantidade")).sendKeys(String.valueOf(quantidade));
		form.submit();
		WebElement caixa = caixaResultado();
		assertThat(caixa.getText(), is("Sessão reservada com sucesso por R$ 55,00"));
	}
	
	private WebElement caixaResultado() {
		WebElement caixaSucesso = driver.findElement(By.id("message"));
		return caixaSucesso;
	}
	
	private WebElement form() {
		return driver.findElement(By.id("reservaForm"));
	}


}
