package view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.RelatorioController;
import model.dto.RelatorioDTO;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class MenuRelatorio {

	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static final int QTDE_CHAMADO_ABERTO = 1;	
	private static final int QTDE_CHAMADO_FECHADO = 2;
	private static final int OPCAO_MENU_RELATORIO_SAIR = 9;
	
	
	public void apresentarMenuRelatorio() {
		int opcao = apresentarOpcoesMenuRelatorio();
		while (opcao != OPCAO_MENU_RELATORIO_SAIR) {
			switch (opcao) {
				case QTDE_CHAMADO_ABERTO: {
					this.quantidadeChamadoAbertos();
					break;
				}
				case QTDE_CHAMADO_FECHADO: {
					this.quantidadeChamadoFechados();
					break;
				}
				default: {
					System.out.println("\nOpção Inválida"); 
				}
			}
			opcao = apresentarOpcoesMenuRelatorio();
		}		
	}

	
	


	private int apresentarOpcoesMenuRelatorio() {
		System.out.println("\n---- Sistema Socorro Desk ---- ");
		System.out.println("\n---- Menu de Relatórios ----");
		System.out.println("\nOpções:");
		System.out.println(QTDE_CHAMADO_ABERTO + " - Listar a quantidade de chamados Abertos");
		System.out.println(QTDE_CHAMADO_FECHADO + " - Listar a quantidade de chamados Fechados");
		System.out.println(OPCAO_MENU_RELATORIO_SAIR + " - Voltar");
		System.out.print("\nDigite a Opção: ");
		return Integer.parseInt(teclado.nextLine());
	}
	
	
	
	private void quantidadeChamadoAbertos() {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> listaRelatorioDTO = relatorioController.listasChamadosAbertosController();
		System.out.print("\n--------- RESULTADO DA CONSULTA DE CHAMADOS ABERTO ---------");
		System.out.printf("\n%10s  %-30s ", 
				"QTDE CHAMADOS", "NOME SOLICITANTE");
		for (int i = 0; i < listaRelatorioDTO.size(); i++) {
			listaRelatorioDTO.get(i).imprimirQtdeChamadosAbertos();
		}
		System.out.println();		
	}
	
	
	private void quantidadeChamadoFechados() {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> listaRelatorioDTO = relatorioController.listasChamadosFechadosController();
		System.out.printf("\n%10s %-30s %10s %-30s %-30s %-15s %-30s %-15s ", 
				"ID CHAMADO", "NOME USUARIO", "NOME TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", 
				"SOLUÇÃO", "DATA FECHAMENTO");
		for (int i = 0; i < listaRelatorioDTO.size(); i++) {
			listaRelatorioDTO.get(i).imprimirQtdeChamadosFechados();
		}
		System.out.println();		
	}
	

	


	

}
