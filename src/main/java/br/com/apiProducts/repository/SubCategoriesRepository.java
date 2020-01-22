package br.com.apiProducts.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.apiProducts.domain.SubCategories;

public interface SubCategoriesRepository extends PagingAndSortingRepository<SubCategories,Long>  {

	public Page<SubCategories> findAll(Pageable pageable);
	
	@Query("select u from SubCategories u where u.name  like %:input% OR u.description like %:input% ")
	public Page<SubCategories> findAllContaining(Pageable pageable, @Param("input") String input);
	
	@Query("select u from SubCategories u where u.name  like %:name% OR u.description like %:name% ")
	public List<SubCategories> findByName(@Param("name")  String name);
}

