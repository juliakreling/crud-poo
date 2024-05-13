package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class UsuarioDAO {
	
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public UsuarioVO realizarLoginDAO(UsuarioVO usuarioVO) {		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;		
		String query = "SELECT u.idusuario, tipo.descricao, u.nome, u.cpf, u.email, "
				+ "u.datacadastro, u.dataexpiracao "
				+ "FROM usuario u, tipousuario tipo "
				+ "WHERE u.login = '" + usuarioVO.getLogin() + "' "
				+ "AND u.senha = '" + usuarioVO.getSenha() + "' "
				+ "AND u.idtipousuario = tipo.idtipousuario" ;		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuario(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setDataCadastro(LocalDate.parse(resultado.getString(6)));
				if(resultado.getString(7) != null) {
					usuarioVO.setDataExpiracao(LocalDate.parse(resultado.getString(7)));
				}				
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query que realiza o login.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}				
		return usuarioVO;
	}

	
	public ArrayList<TipoUsuarioVO> consultarTipoUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;		
		ArrayList<TipoUsuarioVO> listaTipoUsuarioVO = new ArrayList<TipoUsuarioVO>();
		
		String query = "SELECT descricao FROM tipoUsuario";		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				TipoUsuarioVO tipoUsuariVO = 
						TipoUsuarioVO.valueOf(resultado.getString(1));
				listaTipoUsuarioVO.add(tipoUsuariVO);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query de consulta de tipo de usuários.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return listaTipoUsuarioVO;
	}

	
	public boolean verificarExistenciaRegistroPorCpfDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;		
		boolean retorno = false;		
		String query = "SELECT cpf FROM usuario WHERE cpf = '" 
				+ usuarioVO.getCpf() + "'";		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query que verifica existência de usuário por CPF.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}		
		return retorno;
	}

	
	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {		
		String query = "INSERT INTO usuario (idtipousuario, nome, cpf, email, "
				+ "datacadastro, login, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";			
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);					
		try {
			pstmt.setInt(1, usuarioVO.getTipoUsuario().getValor());
			pstmt.setString(2, usuarioVO.getNome());
			pstmt.setString(3, usuarioVO.getCpf());
			pstmt.setString(4, usuarioVO.getEmail());
			pstmt.setObject(5, usuarioVO.getDataCadastro());
			pstmt.setString(6, usuarioVO.getLogin());
			pstmt.setString(7, usuarioVO.getSenha());
			pstmt.execute();			
			
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query de cadastro de usuário!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}		
		return usuarioVO;
	}	
	
	
	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;		
		String query = "UPDATE usuario SET dataexpiracao = '"
				+ usuarioVO.getDataExpiracao() + "' "
				+ "WHERE idusuario = " + usuarioVO.getIdUsuario();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			} 
		} catch (SQLException e){
			System.out.println("Erro ao executar a query da exclusão do usuário.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;		
	}


	public boolean verificarExistenciaRegistroPorIdUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;		
		String query = "SELECT idusuario FROM usuario WHERE idusuario = " + idUsuario;		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query que verifica a "
					+ "existência de usuário por ID");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}


	public boolean verificarDesligamentoPorIdUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;		
		String query = "SELECT dataexpiracao FROM usuario WHERE idusuario = " 
				+ usuarioVO.getIdUsuario()
				+ " AND dataexpiracao is not null";		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query que verifica o "
					+ "desligamento do usuário por ID");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}


	public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE usuario SET idtipousuario = "
				+ usuarioVO.getTipoUsuario().getValor() 
				+ ", nome = '" + usuarioVO.getNome()
				+ "', cpf = '" + usuarioVO.getCpf()
				+ "', email = '" + usuarioVO.getEmail()
				+ "', datacadastro = '" + usuarioVO.getDataCadastro()
				+ "', login = '" + usuarioVO.getLogin()
				+ "', senha = '" + usuarioVO.getSenha()
				+ "' WHERE idusuario = " + usuarioVO.getIdUsuario();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			} 
		} catch (SQLException e){
			System.out.println("Erro ao executar a query da atualização do usuário.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;	
	}


	public ArrayList<UsuarioVO> consultarTodosUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<UsuarioVO> listaUsuariosVO = new ArrayList<UsuarioVO>();
		String query = "SELECT u.idusuario, tipo.descricao, u.nome, u.cpf, u.email, u.datacadastro, "
				+ "u.dataexpiracao, u.login, u.senha "
				+ "FROM usuario u, tipousuario tipo "
				+ "WHERE u.idTipoUsuario = tipo.idTipoUsuario";
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				UsuarioVO usuarioVO = new UsuarioVO();
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuario(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setDataCadastro(LocalDate.parse(resultado.getString(6), dataFormatter));
				if(resultado.getString(7) != null) {
					usuarioVO.setDataExpiracao(LocalDate.parse(resultado.getString(7), dataFormatter));
				}
				usuarioVO.setLogin(resultado.getString(8));
				usuarioVO.setSenha(resultado.getString(9));
				listaUsuariosVO.add(usuarioVO);
			}
			
		} catch(SQLException e) {
			System.out.println("Erro ao executar a Query de consulta de usuários.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaUsuariosVO;
	}


	public UsuarioVO consultarUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		UsuarioVO usuario = new UsuarioVO();
		String query = "SELECT u.idusuario, tipo.descricao, u.nome, u.cpf, u.email, u.datacadastro, "
				+ "u.dataexpiracao, u.login, u.senha "
				+ "FROM usuario u, tipousuario tipo "
				+ "WHERE u.idTipoUsuario = tipo.idTipoUsuario "
				+ "AND u.idusuario = " + usuarioVO.getIdUsuario();
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuario.setTipoUsuario(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuario.setNome(resultado.getString(3));
				usuario.setCpf(resultado.getString(4));
				usuario.setEmail(resultado.getString(5));
				usuario.setDataCadastro(LocalDate.parse(resultado.getString(6), dataFormatter));
				if(resultado.getString(7) != null) {
					usuario.setDataExpiracao(LocalDate.parse(resultado.getString(7), dataFormatter));
				}
				usuario.setLogin(resultado.getString(8));
				usuario.setSenha(resultado.getString(9));
			}
		} catch(SQLException e) {
			System.out.println("Erro ao executar a Query de consulta de usuários.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuario;		
	}
	
	
}