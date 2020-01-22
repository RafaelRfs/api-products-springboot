package br.com.apiProducts.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import br.com.apiProducts.domain.Users;
import br.com.apiProducts.login.LoginController;

@Service
public class MyUserDetailService implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginController loginController = new LoginController();
		String name = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		loginController.setEmail(name);
		loginController.setSenha(password);
		Users usr = loginController.authenticate();
		if (usr.getId() > 0) {
			Set authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority(usr.getTipo().toString()));
			Authentication auth = new UsernamePasswordAuthenticationToken(name, password,authorities);
			return auth;
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
