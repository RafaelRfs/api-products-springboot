package br.com.apiProducts.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Users;
import br.com.apiProducts.enums.Roles;
import br.com.apiProducts.exception.AppException;
import br.com.apiProducts.login.LoginController;

public class HelperUtils {

	public static void main(String [] args) {
		String tstenc = encodeBase64("userApi","userApi");
		Map<String,String> mp = decodeBase64(tstenc);
		String tstrec = mp.get("email")+":"+mp.get("senha");
		System.out.println("ENCODED >> " + tstenc);
		System.out.println("DECODED >> "+tstrec);
	}
	
	public static String encodeBase64(String user, String pass) {
		String hash = user+":"+pass;
		String  retorno = "";
		byte[] byteArray =  Base64.encodeBase64(hash.getBytes(StandardCharsets.UTF_8)); 
		retorno += new String(byteArray);
		return retorno;
	}
	
	public static Map<String, String> decodeBase64(String hash){
		Map<String,String> ret = new HashMap<String, String>();
		byte[] bit = Base64.decodeBase64(hash.getBytes(StandardCharsets.UTF_8));
		String rc =  new String(bit, StandardCharsets.UTF_8);
		String[] hashrec = rc.split(":");
		ret.put(Assets.EMAIL, hashrec[0]);
		ret.put(Assets.SENHA, hashrec[1]);
		return ret;	
	}
	
	public static Users autheticate( Map<String, String> headers, boolean isRoot) throws AppException {
		if( headers.get("authorization") == null) {
			throw new AppException(Assets.LOGIN_MSG_AUTH);
		}
		String hash = headers.get("authorization")!= null ? headers.get("authorization").split(" ")[1] : "";
		Map<String, String> hashDecoded = decodeBase64(hash);
		LoginController login = new LoginController(hashDecoded.get(Assets.EMAIL), hashDecoded.get(Assets.SENHA));
		Users usr = login.authenticate();
		if(usr.getId() <= 0 ) {
			throw new AppException(Assets.LOGIN_MSG);
		}
		if (isRoot && Roles.ADMIN != usr.getRoles()) {
			throw new AppException(Assets.LOGIN_MSG_FORBIDDEN);
		}
		return usr;
	}
	
	public static void verifyAuthorization() throws AppException {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		
		SimpleGrantedAuthority rolesObj =  (SimpleGrantedAuthority) auth.getAuthorities().toArray()[0];
		
		Integer roles = Integer.parseInt(rolesObj.toString());
		
		Roles rules  =  roles == 0 ? Roles.USER : Roles.ADMIN;
		
		if(Roles.ADMIN != rules) {
			throw new AppException("Função não permitida para o usuario.. ");
		}
		
	}
	
}
