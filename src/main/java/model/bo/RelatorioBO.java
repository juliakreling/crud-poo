package model.bo;

import java.util.ArrayList;
import model.dao.RelatorioDAO;
import model.dto.RelatorioDTO;


public class RelatorioBO {

	RelatorioDAO relatorioDAO = new RelatorioDAO();
	
	public ArrayList<RelatorioDTO> relatorioChamadosAbertosBO() {
		ArrayList<RelatorioDTO> listaRelatorioDTO = relatorioDAO.consultarChamadosAbertosDAO();
		if(listaRelatorioDTO.isEmpty()) {
			System.out.println("\nO relatório de chamados abertos está vazia.");
		}
		return listaRelatorioDTO;
	}

	
	public ArrayList<RelatorioDTO> relatorioChamadosFechadosBO() {
		ArrayList<RelatorioDTO> listaRelatorioDTO = relatorioDAO.consultarChamadosFechadosDAO();
		if(listaRelatorioDTO.isEmpty()) {
			System.out.println("\nO relatório de chamados fechados está vazia.");
		}
		return listaRelatorioDTO;
	}

//	public ArrayList<ChamadoVO> listasChamadosAbertosPorUsuarioBO() {
//		ArrayList<ChamadoVO> chamadosVO = chamadoDAO.consultarChamadosAbertosUsuarioDAO();
//		if(chamadosVO.isEmpty()){
//			System.out.println("\nLista de Chamados está vazia.");
//		}
//		return chamadosVO;
//	}
//
//	
//	public ArrayList<ChamadoVO> listasChamadosFechadosPorUsuarioBO() {
//		ChamadoDAO chamadoDAO = new ChamadoDAO();
//		ArrayList<ChamadoVO> chamadosVO = chamadoDAO.consultarChamadosFechadosUsuarioDAO();
//		if(chamadosVO.isEmpty()){
//			System.out.println("\nLista de Chamados está vazia.");
//		}
//		return chamadosVO;
//	}
//	
//	
//	
//	ChamadoDAO chamadoDAO = new ChamadoDAO();
	
}