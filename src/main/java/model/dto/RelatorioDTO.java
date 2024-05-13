package model.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatorioDTO {

	
	DateTimeFormatter formaterDate  = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	
	private int idChamado;
	private int idUsuario;
	private int idTecnico;
	private String nomeUsuario;
	private String nomeTecnico;
	private String titulo;
	private String descricao;
	private LocalDate dataAbertura;
	private String solucao;
	private LocalDate dataFechamento;
	
	
	public RelatorioDTO(DateTimeFormatter formaterDate, int idChamado, int idUsuario, int idTecnico, String titulo,
			String descricao, LocalDate dataAbertura, String solucao, LocalDate dataFechamento) {
		super();
		this.formaterDate = formaterDate;
		this.idChamado = idChamado;
		this.idUsuario = idUsuario;
		this.idTecnico = idTecnico;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataAbertura = dataAbertura;
		this.solucao = solucao;
		this.dataFechamento = dataFechamento;
	}

	public RelatorioDTO() {
		super();
	}
	
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	
	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public DateTimeFormatter getFormaterDate() {
		return formaterDate;
	}

	public void setFormaterDate(DateTimeFormatter formaterDate) {
		this.formaterDate = formaterDate;
	}

	public int getIdChamado() {
		return idChamado;
	}

	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	

	public void imprimirQtdeChamadosAbertos() {
		System.out.printf("\n%10d %-30s",
				this.getIdChamado(),
				this.getNomeUsuario());
	}

	public void imprimirQtdeChamadosFechados() {
		System.out.printf("\n%10s %-30s %-30s %-30s %-15s %-30s %-15s",
				this.getIdChamado(),
				this.getNomeUsuario(),
				this.getTitulo(),
				this.getDescricao(),
				validarData(this.getDataAbertura()),
				this.getSolucao(),
				validarData(this.getDataFechamento()));
				
	}

	private Object validarData(LocalDate data) {
		String resultado = "";
		if(data != null) {
			resultado = data.format(formaterDate);
		}
		return resultado;
	}
}