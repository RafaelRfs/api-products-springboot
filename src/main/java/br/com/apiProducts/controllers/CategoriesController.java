package br.com.apiProducts.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.apiProducts.configuration.HelperUtils;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Categories;
import br.com.apiProducts.exception.AppException;
import br.com.apiProducts.services.CategoriesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static br.com.apiProducts.domain.Assets.setConfigs;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoriesController {
	private CategoriesService categoriesService;
	
	@RequestMapping("/")
	public String api() {
		return " Api feita por Rafael F.S. ";
	}

	@PostMapping("/v1/categories")
	public ResponseEntity<Object> addCategories(@RequestBody Categories category) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			resp = new ResponseEntity<Object>(categoriesService.addCategory(category), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	@PutMapping("/v1/categories")
	public ResponseEntity<Object> updateCategories(@RequestBody Categories category) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			resp = new ResponseEntity<Object>(categoriesService.updateCategory(category), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	@DeleteMapping("/v1/categories")
	public ResponseEntity<Object> deleteCategories(@RequestBody Categories category) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			categoriesService.deleteCategory(category);
			resp = new ResponseEntity<Object>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	@GetMapping("/v1/categories")
	public ResponseEntity<Object> listCategories(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "", name = Assets.SEARCH) String search,
			@RequestParam(required = false, defaultValue = "false", name = Assets.GETSUBCATEGORIES) boolean getSubCategories
			) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search, getSubCategories,0,0);
		return new ResponseEntity<Object>(categoriesService.listCategories(configs), HttpStatus.OK);
	}
	
	@GetMapping("/v1/categories/search/{search}/page/{page}")
	public ResponseEntity<Object> listCategoriesByUrl(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "false", name = Assets.GETSUBCATEGORIES) boolean getSubCategories,
			@PathVariable String search, @PathVariable int page) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search,getSubCategories,0,0);
		return new ResponseEntity<Object>(categoriesService.listCategories(configs), HttpStatus.OK);
	}

	@GetMapping("/v1/categories/{id}")
	public ResponseEntity<Object> listCategoryById(@PathVariable Long id) {
		return new ResponseEntity<Object>(categoriesService.findCategorieByID(id), HttpStatus.OK);
	}
	
	@GetMapping("/v1/categories/name/{name}")
	public ResponseEntity<Object> listCategoryByNome(@PathVariable String name) {
		return new ResponseEntity<Object>(categoriesService.findCategorieByName(name), HttpStatus.OK);
	}
	
	@GetMapping("/v1/categories/{id}-{name}/subcategories")
	public ResponseEntity<Object> listCategorySubCategoriesSubByNome(@PathVariable Long id, @PathVariable String name) throws AppException{
		return new ResponseEntity<Object>(categoriesService.findCategorieSubByName(id, name), HttpStatus.OK);
	}
}
