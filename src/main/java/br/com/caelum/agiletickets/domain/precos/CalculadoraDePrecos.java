package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = null;
		double qtdPorPrecoTotal =  (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos...
			preco = calculaPrecoPorQtdIngressoEMultiplicador(sessao, preco, 0.10, 0.05, qtdPorPrecoTotal);

		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {

			preco = calculaPrecoPorQtdIngressoEMultiplicador(sessao, preco, 0.20, 0.50, qtdPorPrecoTotal);

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = calculaPrecoPorDuracao(sessao, preco);
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoPorQtdIngressoEMultiplicador(Sessao sessao, BigDecimal preco, double multiplicador, double qtdIngressos, double qtdPorPrecoTotal) {

		if(qtdPorPrecoTotal <= qtdIngressos) {
			preco = calculaPrecoPorMultiplicador(sessao, multiplicador);
		} else {
			preco = sessao.getPreco();
		}

		return preco;
	}

	private static BigDecimal calculaPrecoPorDuracao(Sessao sessao, BigDecimal preco) {
		return preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
	}

	private static BigDecimal calculaPrecoPorMultiplicador(Sessao sessao, double multiplicador) {
		return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(multiplicador)));
	}

}