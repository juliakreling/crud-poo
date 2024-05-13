package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.dto.RelatorioDTO;

public class RelatorioDAO {

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public ArrayList<RelatorioDTO> consultarChamadosAbertosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<RelatorioDTO> listaRelatorioDTO = new ArrayList<RelatorioDTO>();
		String query = "SELECT COUNT(c.IDCHAMADO) AS QTDE_CHAMADOS "
				+ " , u.NOME AS NOME_USUARIO_SOLICITANTE "
				+ " FROM CHAMADOS c, USUARIO u "
				+ " WHERE c.IDUSUARIO = u.IDUSUARIO "
				+ " AND DATAFECHAMENTO IS NULL "
				+ " GROUP BY c.IDUSUARIO ";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				RelatorioDTO relatorioDTO = new RelatorioDTO();
				relatorioDTO.setIdChamado(Integer.parseInt(resultado.getString(1)));
				relatorioDTO.setNomeUsuario(resultado.getString(2));
				listaRelatorioDTO.add(relatorioDTO);
			}
		} catch (SQLException e){
			System.out.println("Erro ao executar a Query de Consulta de Todos os Chamados do Usuário.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaRelatorioDTO;
	}

	
	public ArrayList<RelatorioDTO> consultarChamadosFechadosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<RelatorioDTO> listaRelatorioDTO = new ArrayList<RelatorioDTO>();
		String query = "SELECT  c.IDCHAMADO "
				+ " ,us.NOME AS NOME_USUARIO_SOLICITANTE "
				+ " , tc.NOME AS NOME_TECNICO "
				+ " , c.TITULO "
				+ " , c.DESCRICAO "
				+ " , c.DATAABERTURA "
				+ " , c.SOLUCAO "
				+ " , c.DATAFECHAMENTO "
				+ " FROM CHAMADOS c, USUARIO us, USUARIO tc "
				+ " WHERE c.IDUSUARIO = us.IDUSUARIO "
				+ " AND c.IDTECNICO = tc.IDUSUARIO "
				+ " AND DATAFECHAMENTO IS NOT NULL ";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				RelatorioDTO relatorioDTO = new RelatorioDTO();
				relatorioDTO.setIdChamado(Integer.parseInt(resultado.getString(1)));
				relatorioDTO.setNomeUsuario(resultado.getString(2));
				relatorioDTO.setNomeTecnico(resultado.getString(3));
				relatorioDTO.setTitulo(resultado.getString(4));
				relatorioDTO.setDescricao(resultado.getString(5));
				relatorioDTO.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				relatorioDTO.setSolucao(resultado.getString(7));
				relatorioDTO.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				listaRelatorioDTO.add(relatorioDTO);
			} 
		}catch (SQLException e){
			System.out.println("Erro ao executar a Query de Consulta de Todos os Chamados do Usuário.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaRelatorioDTO;
	}

	
	
}