package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.jadira.usertype.dateandtime.joda.PersistentDurationAsMillisLong;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void adicionaSessoesAoEspetaculoPorDiaOuSemana() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2011, 1, 9);
		LocalDate fim = new LocalDate(2011, 1, 23);
		LocalTime horario = new LocalTime(17, 0);
		
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertEquals(3, espetaculo.getSessoes().size());
	}
	
	@Test
	public void verificaDataDeCadaSessaoDoEspetaculo() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2011, 1, 9);
		LocalDate fim = new LocalDate(2011, 1, 23);
		LocalTime horario = new LocalTime(17, 0);
		
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		List<Sessao> sessoes = espetaculo.getSessoes();
		
		inicio.toDateTime(horario);
		
		LocalDate inicioMaisSete = inicio.plusDays(7);
		inicioMaisSete.toDateTime(horario);
		
		LocalDate inicioMaisQuatorze = inicioMaisSete.plusDays(7);
		inicioMaisQuatorze.toDateTime(horario);
		
		
		assertEquals(inicio, sessoes.get(0).getInicio());
		assertEquals(inicioMaisSete, sessoes.get(1).getInicio());
		assertEquals(inicioMaisQuatorze, sessoes.get(2).getInicio());
		
		
	}
	
}
