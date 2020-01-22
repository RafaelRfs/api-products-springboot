package br.com.apiProducts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Categories;
import br.com.apiProducts.exception.AppException;
import br.com.apiProducts.repository.CategoriesRepository;
import static br.com.apiProducts.domain.Assets.getPaginator; 
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class CategoriesService {
	private CategoriesRepository categoriesRepository;
	
	public List<?> listCategories(Map<String, Object> configs) {
		List<Categories> categories = new ArrayList<>();
		if ((boolean) configs.get(Assets.IS_ALL)) {
			recoverAllCategories(configs, categories);
		} else {
			recoverCategoriesByPageLimit(configs, categories);
		}
		
		if((boolean) configs.get(Assets.GETSUBCATEGORIES)) {
			categories.forEach(c -> c.getSubcategories().size());
		}
		
		return categories;
	}
	
	public void recoverCategoriesByPageLimit(Map<String, Object> configs, List<Categories> categories) {
		if ("".equals(configs.get(Assets.SEARCH))) {
			categoriesRepository.findAll(getPaginator(configs, false)).forEach(categories::add);
		} else {
			findCategoriesByName(configs, categories);
		}
	}
	public void recoverAllCategories(Map<String, Object> configs, List<Categories> categories) {
		if ("".equals(configs.get(Assets.SEARCH))) {
			categoriesRepository.findAll(Assets.getSort(configs)).forEach(categories::add);
		} else {
			findCategoriesByName(configs, categories);
		}
	}
	private void findCategoriesByName(Map<String, Object> configs, List<Categories> categories ) {
		categoriesRepository
				.findAllContaining(getPaginator(configs, (boolean)configs.get(Assets.IS_ALL)), (String) configs.get(Assets.SEARCH))
				.forEach(categories::add);
	}
	public Optional<Categories> findCategorieByID(Long id) {
		return  categoriesRepository.findById(id);		
	}
	public List<Categories> findCategorieByName(String name) {
		return  categoriesRepository.findByName(name);		
	}
	public Categories findCategorieSubByName(Long id,String name) throws AppException {
		try {
			return categoriesRepository.findByIdName(id,name).get();
		}catch(Exception e) {
			throw new AppException("Categoria não encontrada ...");
		}
	}
	
	public Long addCategory(Categories category) {
		return categoriesRepository.save(category).getId();
	}
	public boolean updateCategory(Categories category) {
		return categoriesRepository.save(category) != null;
	}
	public void deleteCategory(Categories category) {
		 categoriesRepository.delete(category);
	}
	
}
