package br.com.apiProducts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static br.com.apiProducts.domain.Assets.getPaginator;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Products;
import br.com.apiProducts.repository.ProductsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class ProductsService {
	
	private ProductsRepository productsRepository;
	
	public Page<Products> listProductsByCategorySubCategories(Map<String, Object> configs){
		return productsRepository.findProductsByCategorySubCat(
				getPaginator(configs,true),
				(Long)configs.get(Assets.CATEGORY_ID),
				(Long)configs.get(Assets.SUBCATEGORY_ID)
				);
	}
	
	public Page<Products> listProductsByCategoryId(Map<String, Object> configs){
		return productsRepository.findProductsByCategory(
				getPaginator(configs,true),
				(Long)configs.get(Assets.CATEGORY_ID));
	}
	
	
	public Page<Products> listProductsBySearchCategory(Map<String, Object> configs){
		return productsRepository.findAllContainingCategory(
				getPaginator(configs,true),
				(String)configs.get(Assets.SEARCH), 
				(Long)configs.get(Assets.CATEGORY_ID));
	}
	
	public Page<Products> listProductsBySearchCategorySubCat(Map<String, Object> configs){
		return productsRepository.findProductsByCategorySubCat(
				getPaginator(configs,true),
				(Long)configs.get(Assets.CATEGORY_ID),
				(Long)configs.get(Assets.SUBCATEGORY_ID),
				(String)configs.get(Assets.SEARCH)
				);
	}
	
	public Page<Products> listProductsBySearch(Map<String, Object> configs){
		return productsRepository.findAllContaining(getPaginator(configs,true), (String)configs.get(Assets.SEARCH));
	}
	
	public List<Products> listProducts() {
		List<Products> list = new ArrayList<>();
		productsRepository.findAll().forEach(list::add);
		
		list.forEach(p -> { 
			p.getCategory().getName();
			p.getSubcategories().size();
		});
		
		return list;
	}

	public long addProduct(Products product) {
		return productsRepository.save(product).getId();
	}

	public boolean updateProduct(Products product) {
		return productsRepository.save(product) != null;
	}

	public void deleteProduct(Products product) {
		productsRepository.delete(product);
	}

}
