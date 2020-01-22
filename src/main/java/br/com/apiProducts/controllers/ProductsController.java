package br.com.apiProducts.controllers;

import static br.com.apiProducts.domain.Assets.setConfigs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Products;
import br.com.apiProducts.services.ProductsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsController {

	private ProductsService productsService;

	@GetMapping("/")
	public String about() {
		return " Api feita por Rafael F.S.";
	}

	@GetMapping("/health/check")
	public ResponseEntity<Object> healthCheck() {
		return new ResponseEntity<Object>("OK", HttpStatus.OK);
	}

	@GetMapping("/products/test")
	public Products testProducts() {
		return new Products();
	}

	@GetMapping("/v1/products/all")
	public ResponseEntity<Object> listAllProducts() {
		return new ResponseEntity<Object>(productsService.listProducts(), HttpStatus.OK);
	}

	@GetMapping("/v1/products")
	public ResponseEntity<?> listProducts(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "", name = Assets.SEARCH) String search) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search, false, 0, 0);
		try {
			List<Products> res = productsService.listProductsBySearch(configs).getContent();
			return new ResponseEntity<List<Products>>(res, HttpStatus.OK);

		} catch (Exception e) {
			log.error(Assets.ERROR_MSG, e);
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/v1/products/search/{search}/page/{page}")
	public ResponseEntity<?> listProductsBySearchPage(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable String search, @PathVariable int page) {
		return listProducts(limit, page, isAll, orderBy, search);
	}

	@GetMapping("/v1/products/search/{search}")
	public ResponseEntity<?> listProductsBySearch(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@PathVariable String search) {
		return listProducts(limit, page, isAll, orderBy, search);
	}

	@GetMapping("/products/lambda/category/{categoryId}/search/{search}")
	public ResponseEntity<?> listProductsLambdaBySearchCategory(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@PathVariable String search, @PathVariable long categoryId) {

		try {
			List<Products> prds = (List<Products>) listProducts(limit, page, isAll, orderBy, search).getBody();
			List<Products> produtosFilter = prds.parallelStream().filter(p -> categoryId == p.getCategory().getId())
					.collect(Collectors.toList());
			return new ResponseEntity<>(produtosFilter, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/v1/products/categories/{"+Assets.CATEGORY_ID+"}")
	public ResponseEntity<?> listProductsByCategories(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable long categoryId
			) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, null, false, categoryId, 0);
		try {
			List<Products> res = productsService.listProductsByCategoryId(configs).getContent();
			return new ResponseEntity<List<Products>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(Assets.ERROR_MSG, e);
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/v1/products/categories/{"+Assets.CATEGORY_ID+"}/subcategories/{"+Assets.SUBCATEGORY_ID+"}")
	public ResponseEntity<?> listProductsByCategoriesSubCat(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable long categoryId,
			@PathVariable long subcategoryId
			) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, null, false, categoryId, subcategoryId);
		try {
			List<Products> res = productsService.listProductsByCategorySubCategories(configs).getContent();
			return new ResponseEntity<List<Products>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(Assets.ERROR_MSG, e);
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/v1/products/categories/{categoryId}/search/{search}")
	public ResponseEntity<?> listProductsBySearchCategories(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable long categoryId,
			@PathVariable String search) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search, false, categoryId, 0);
		try {

			List<Products> res = productsService.listProductsBySearchCategory(configs).getContent();
			return new ResponseEntity<List<Products>>(res, HttpStatus.OK);

		} catch (Exception e) {
			log.error(Assets.ERROR_MSG, e);
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/v1/products/categories/{"+Assets.CATEGORY_ID+"}/subcategories/{"+Assets.SUBCATEGORY_ID+"}/search/{search}")
	public ResponseEntity<?> listProductsBySearchCategories(
			@RequestParam(required = false, defaultValue = "10", name = Assets.LIMIT) int limit,
			@RequestParam(required = false, defaultValue = "0", name = Assets.PAGE) int page,
			@RequestParam(required = false, defaultValue = "false", name = Assets.IS_ALL) boolean isAll,
			@RequestParam(required = false, defaultValue = "id DESC", name = Assets.ORDER_BY) String orderBy,
			@PathVariable long categoryId,
			@PathVariable long subcategoryId,
			@PathVariable String search) {
		Map<String, Object> configs = setConfigs(limit, page, isAll, orderBy, search, false, categoryId, subcategoryId);
		try {

			List<Products> res = productsService.listProductsBySearchCategorySubCat(configs).getContent();
			return new ResponseEntity<List<Products>>(res, HttpStatus.OK);

		} catch (Exception e) {
			log.error(Assets.ERROR_MSG, e);
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/v1/products")
	public void addProducts(@RequestBody Products product) {
		productsService.addProduct(product);
	}

	@DeleteMapping("/v1/products")
	public void deleteProducts(@RequestBody Products product) {
		productsService.deleteProduct(product);
	}

	@PutMapping("/v1/products")
	public void updateProducts(@RequestBody Products product) {
		productsService.updateProduct(product);
	}
}
