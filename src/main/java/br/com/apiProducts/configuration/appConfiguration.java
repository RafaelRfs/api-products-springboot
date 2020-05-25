package br.com.apiProducts.configuration;

import java.net.URI;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import br.com.apiProducts.domain.Assets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class appConfiguration {
	
	@Bean
	  public DataSource dataSource() {
        String user = null;
        String pass = null;
        String url = null;
        String uri = System.getenv(Assets.ENV_DATABASE_URL);
        uri = (uri == null) ? Assets.ENV_DATABASE_URL_LOCAL : uri;
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
                URI dbUri = new URI(uri);
                user = dbUri.getUserInfo().split(":")[0];
                pass = dbUri.getUserInfo().split(":").length > 1 ?
                        dbUri.getUserInfo().split(":")[1].trim() : "" ;
            String driver = "postgres".equalsIgnoreCase(dbUri.getScheme())
                    ? "postgresql"
                    : dbUri.getScheme().trim().toLowerCase() ;

                url = "jdbc:"+driver+"://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                        +
                        "?useUnicode=true&characterEncoding=utf-8&useTimezone=true&serverTimezone=UTC";
        }catch(Exception e) {
            log.error("ERROR DATASOURCE >> ", e);
        }
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }	
	
}
