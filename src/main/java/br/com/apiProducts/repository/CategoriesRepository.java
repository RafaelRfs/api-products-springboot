package br.com.apiProducts.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.apiProducts.domain.Categories;

public interface CategoriesRepository extends PagingAndSortingRepository<Categories,Long> {
	public Page<Categories> findAll(Pageable pageable);
	@Query("select u from Categories u where u.name  like %:input% OR u.description like %:input% ")
	public Page<Categories> findAllContaining(Pageable pageable, @Param("input") String input);
	@Query("select u from Categories u where u.name  like %:name% OR u.description like %:name% ")
	public List<Categories> findByName(@Param("name")  String name);
	@Query("select u from Categories u where u.id = :id AND u.name = :name ")
	public Optional<Categories> findByIdName(@Param("id")Long id, @Param("name")  String name);
}



