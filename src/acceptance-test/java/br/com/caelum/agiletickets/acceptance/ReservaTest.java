package br.com.caelum.agiletickets.acceptance;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.acceptance.page.SessaoPage;
import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Estabelecimento;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;
import br.com.caelum.agiletickets.persistencia.JPAEspetaculoDao;

public class ReservaTest {
	
	private WebDriver browser;
	
	@Before
	public void setUp() throws Exception {
		browser = new FirefoxDriver();
	}
	
	@After
	public void tearDown() throws Exception {
		browser.close();
	}
	
	@Test
	public void aoComprarUltimosIngressosCobra10PorcentAMais() throws Exception {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tests");
		
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Teste Aceitacao - 10 porcent (estab)");
		estabelecimento.setEndereco("Rua dos Silveiras, 12345");

		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setEstabelecimento(estabelecimento);
		espetaculo.setNome("Teste Aceitacao - 10 Porcent (espetac)");
		espetaculo.setTipo(TipoDeEspetaculo.SHOW);

		manager.persist(estabelecimento);
		manager.persist(espetaculo);
		Sessao sessao = new Sessao();
		
		sessao.setEspetaculo(espetaculo);
		sessao.setPreco(new BigDecimal("50"));
		sessao.setTotalIngressos(100);
		sessao.setIngressosReservados(95);
		
		manager.getTransaction().commit();
		manager.close();
		factory.close();
		
		Sessao sessaoRetorno = manager.find(Sessao.class, "oi");
		
		SessaoPage page = new SessaoPage(browser);
		page.abrePaginaSessao(1L);
		page.reservarIngressoCom10PortcentAMais(1);
		
	}

}
