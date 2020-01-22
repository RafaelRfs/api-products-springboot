package br.com.apiProducts.domain;

import org.springframework.stereotype.Component;

import br.com.apiProducts.enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Users {
	private Integer id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private String descricao;
	private String img;
	private String data;
	private String sexo;
	private Integer tipo;
	private Roles roles;
	
	public Users(String email) {
		this.email = email;
	}
	
	public Users() {
	}
	
	public Users(Users usr) {
		this.id = usr.getId();
		this.senha = usr.getSenha();
		this.email = usr.getEmail();
		this.nome = usr.getNome();
	}
	
	
}
