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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.apiProducts.configuration.HelperUtils;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.SubCategories;
import br.com.apiProducts.services.SubCategoriesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static br.com.apiProducts.domain.Assets.setConfigs;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SubCategoriesController {
	
	private SubCategoriesService subcategoriesService;
	
	@PostMapping("/v1/subcategories")
	public ResponseEntity<Object> addsubcategories(@RequestBody SubCategories subcategory) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			resp = new ResponseEntity<Object>(subcategoriesService.addSubCategory(subcategory), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	@PutMapping("/v1/subcategories")
	public ResponseEntity<Object> updatesubcategories(@RequestBody SubCategories subcategory) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			resp = new ResponseEntity<Object>(subcategoriesService.updateSubCategory(subcategory), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	@DeleteMapping("/v1/subcategories")
	public ResponseEntity<Object> deletesubcategories(@RequestBody SubCategories subcategory) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			subcategoriesService.deleteSubCategory(subcategory);;
			resp = new ResponseEntity<Object>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	
	@DeleteMapping("/v1/subcategories/{id}")
	public ResponseEntity<Object> deletesubcategories(@PathVariable long id) {
		ResponseEntity<Object> resp = null;
		try {
			HelperUtils.verifyAuthorization();
			subcategoriesService.deleteSubCategoryBy(id);
			resp = new ResponseEntity<Object>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(Assets.LOGIN_MSG, e);
			resp = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	@GetMapping("/v1/subcategories")
	public ResponseEntity<Object> listsubcategories(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "", name = Assets.SEARCH) String search) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search,false,0,0);
		return new ResponseEntity<Object>(subcategoriesService.listSubCategories(configs), HttpStatus.OK);
	}
	
	@GetMapping("/v1/subcategories/search/{search}/page/{page}")
	public ResponseEntity<Object> listsubcategoriesByUrl(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable String search, @PathVariable int page) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search, false,0,0);
		return new ResponseEntity<Object>(subcategoriesService.listSubCategories(configs), HttpStatus.OK);
	}

	@GetMapping("/v1/subcategories/{id}")
	public ResponseEntity<Object> listsubcategoryById(@PathVariable Long id) {
		return new ResponseEntity<Object>(subcategoriesService.findSubCategorieByID(id), HttpStatus.OK);
	}

}
