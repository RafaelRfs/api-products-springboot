package br.com.apiProducts.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import br.com.apiProducts.configuration.jwtutil.JwtRequestFilter;

@EnableWebSecurity
public class AppSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new MyUserDetailService());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private static final String[] AUTH_LIST = { "**/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**", "/sec/authenticate", "/health/check", "/" };

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers(AUTH_LIST).permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.httpBasic().authenticationEntryPoint(swaggerAuthenticationEntryPoint());
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.cors();
	}

	@Bean
	public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("Swagger Realm");
		return entryPoint;
	}

}
