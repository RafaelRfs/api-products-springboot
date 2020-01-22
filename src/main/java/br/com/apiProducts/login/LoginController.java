package br.com.apiProducts.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Users;

@Component
public class LoginController {
	private String email;
	private String senha;
	private String url;
	private RestTemplate restTemplate;
	public LoginController() {
		this.restTemplate = new RestTemplate();	
	}
	
	public LoginController(String email) {
		this.restTemplate = new RestTemplate();	
		this.email = email;
	}
	
	public LoginController(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Users authenticate() {
	Users usr = new Users();
	usr.setId(0);
	usr.setEmail(this.getEmail());
	usr.setSenha(this.getSenha());
	ResponseEntity<Users[]> users = restTemplate.postForEntity(Assets.API_VERIFY, usr, Users[].class);	
	return users.getBody()[0];
	}
	
	public Users getUserData() {
		Users usr = new Users();
		usr.setId(0);
		usr.setEmail(this.getEmail());
		usr.setSenha(this.getSenha());
		ResponseEntity<Users> users = restTemplate.postForEntity(url, usr, Users.class);	
		return users.getBody();
	}
	
	public  Users  verifyToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return  restTemplate.exchange(Assets.API_TOKEN_DATA, HttpMethod.PUT, entity, Users.class).getBody(); 
	}
	
}
