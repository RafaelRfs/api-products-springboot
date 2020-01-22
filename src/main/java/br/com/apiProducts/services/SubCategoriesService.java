package br.com.apiProducts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.SubCategories;
import br.com.apiProducts.repository.SubCategoriesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class SubCategoriesService {
	private SubCategoriesRepository subcategoriesRepository;
	
	public List<?> listSubCategories(Map<String, Object> configs) {
		List<SubCategories> subcategories = new ArrayList<>();
		if ((boolean) configs.get(Assets.IS_ALL)) {
			recoverAllSubCategories(configs, subcategories);
		} else {
			recoverSubCategoriesByPageLimit(configs, subcategories);
		}
		return subcategories;
	}
	
	public void recoverSubCategoriesByPageLimit(Map<String, Object> configs, List<SubCategories> subcategories) {
		if ("".equals(configs.get(Assets.SEARCH))) {
			subcategoriesRepository.findAll(Assets.getPaginator(configs, false)).forEach(subcategories::add);
		} else {
			findSubCategoriesByName(configs, subcategories);
		}
	}
	
	public void recoverAllSubCategories(Map<String, Object> configs, List<SubCategories> subcategories) {
		if ("".equals(configs.get(Assets.SEARCH))) {
			subcategoriesRepository.findAll(Assets.getSort(configs)).forEach(subcategories::add);
		} else {
			findSubCategoriesByName(configs, subcategories);
		}
	}
	private void findSubCategoriesByName(Map<String, Object> configs, List<SubCategories> subcategories ) {
		subcategoriesRepository
				.findAllContaining(Assets.getPaginator(configs, (boolean)configs.get(Assets.IS_ALL)), (String) configs.get(Assets.SEARCH))
				.forEach(subcategories::add);
	}
	public Optional<SubCategories> findSubCategorieByID(Long id) {
		return  subcategoriesRepository.findById(id);		
	}
	public List<SubCategories> findSubCategorieByName(String name) {
		return  subcategoriesRepository.findByName(name);		
	}
	public Long addSubCategory(SubCategories subcategory) {
		return subcategoriesRepository.save(subcategory).getId();
	}
	public boolean updateSubCategory(SubCategories subcategory) {
		return subcategoriesRepository.save(subcategory) != null;
	}
	public void deleteSubCategory(SubCategories subcategory) {
		 subcategoriesRepository.delete(subcategory);
	}
	
	public void deleteSubCategoryBy(Long id) {
		subcategoriesRepository.deleteById(id);
	}
	
}
