package br.com.apiProducts.domain;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Assets {
	public static final String ROLE_USER = "USER";
	public static final String API_LOGIN = "https://serene-sea-70010.herokuapp.com/";
	public static final String API_VERIFY = API_LOGIN+"v1/login/auth";
	public static final String API_TOKEN_DATA = API_LOGIN+"token/data";
	public static final String EMAIL = "email";
	public static final String SENHA = "senha";
	public static final String LOGIN_MSG_AUTH = "Cabeçalho não enviado.";
	public static final String LOGIN_MSG = "Usuario não autenticado.";
	public static final String LOGIN_MSG_FORBIDDEN = "Função não permitida para o usuário.";
	public static final Integer ROOT_USER = 0;
	public static final String LIMIT = "limit";
	public static final String ID = "id";
	public static final String PAGE = "page";
	public static final String IS_ALL = "isAll";
	public static final String ORDER_BY = "orderBy";
	public static final String SEARCH = "search";
	public static final String GETSUBCATEGORIES = "getSubCategories";
	public static final String CATEGORY_ID = "categoryId";
	public static final String SUBCATEGORY_ID = "subcategoryId";
	public static final String ERROR_MSG = "ERROR >> ";
	public static final String ENV_DATABASE_URL = "DATABASE_URL";
	public static final String ENV_DATABASE_URL_LOCAL = "mysql://root@127.0.0.1:3306/teste";
	
	public static Sort getSort(Map<String, Object> configs) {
		String orderby = (String) configs.get(Assets.ORDER_BY);
		String[] dataOrder = orderby.trim().split(" ");
		Sort sort = Sort.by(dataOrder[0].toLowerCase());
		return dataOrder.length > 1 && "ASC".equalsIgnoreCase(dataOrder[1])? sort.ascending() : sort.descending();
	}

	public static Pageable getPaginator(Map<String, Object> configs, boolean isAll) {
		int page = (int) configs.get(Assets.PAGE);
		int limit = isAll ? (int) configs.get(Assets.LIMIT) : 1000;
		return PageRequest.of(page, limit, getSort(configs));
	}
	
	public static final Map<String, Object> setConfigs(int limit, int page, boolean isAll, String orderBy,
			String search, boolean getSubCategories, long categoryId, long subcategoryId) {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(Assets.LIMIT, limit);
		configs.put(Assets.PAGE, page);
		configs.put(Assets.IS_ALL, isAll);
		configs.put(Assets.ORDER_BY, orderBy);
		configs.put(Assets.SEARCH, search);
		configs.put(Assets.GETSUBCATEGORIES, getSubCategories);
		configs.put(Assets.CATEGORY_ID, categoryId);
		configs.put(Assets.SUBCATEGORY_ID, subcategoryId);
		return configs;
	}
	
}
