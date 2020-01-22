package br.com.apiProducts.configuration;

import java.net.URI;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class appConfiguration {
	
	@Bean
	public DataSource dataSource() {
		String user = null;
		String pass = null;
		String url = null;
		try {
		if(System.getenv("DATABASE_URL") != null) {	
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		user = dbUri.getUserInfo().split(":")[0];
		pass = dbUri.getUserInfo().split(":")[1];
		url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		}else {
		url = "jdbc:mysql://localhost/teste?useUnicode=true&characterEncoding=utf-8&useTimezone=true&serverTimezone=UTC";
		user = "root";
		pass =  "";
		}
		}catch(Exception e) {
			log.info("ERROR DATASOURCE >> ", e);
		}
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setUrl(url);
	    dataSource.setUsername(user);
	    dataSource.setPassword(pass);
		return dataSource;
	}
	
}
