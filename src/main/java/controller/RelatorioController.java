package controller;

import java.util.ArrayList;
import model.bo.RelatorioBO;
import model.dto.RelatorioDTO;

public class RelatorioController {

	
	
	public ArrayList<RelatorioDTO> listasChamadosAbertosController() {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.relatorioChamadosAbertosBO();		
	}

	public ArrayList<RelatorioDTO> listasChamadosFechadosController() {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.relatorioChamadosFechadosBO();	
	}	
	
	
	
}
