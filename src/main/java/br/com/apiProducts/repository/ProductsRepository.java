package br.com.apiProducts.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import br.com.apiProducts.domain.Assets;
import br.com.apiProducts.domain.Products;

public interface ProductsRepository extends PagingAndSortingRepository<Products, Integer>{
	
	@Query("select p from Products p where p.name  like %:"+Assets.SEARCH+"% OR p.description like %:"+Assets.SEARCH+"% ")
	public Page<Products> findAllContaining(Pageable pageable, @Param(Assets.SEARCH) String input);
	
	@Query("select p from Products p join Categories c on c.id = p.category.id"
			+ " where (p.name  like %:"+Assets.SEARCH+"% OR p.description like %:"+Assets.SEARCH+"%) and c.id = :categoryId  ")
	public Page<Products> findAllContainingCategory(Pageable pageable, @Param(Assets.SEARCH) String input,
			@Param("categoryId") long categoryId);
	
	@Query("select p from Products p join Categories c on c.id = p.category.id where c.id = :"+Assets.CATEGORY_ID)
	public Page<Products> findProductsByCategory(Pageable pageable, @Param(Assets.CATEGORY_ID) long categoryId);
	
	@Query("select p from Products p "
			+ " join p.category c "
			+ " join p.subcategories s "
			+ " where c.id = :"+Assets.CATEGORY_ID+" and s.id = :"+Assets.SUBCATEGORY_ID)
	public Page<Products> findProductsByCategorySubCat(Pageable pageable, @Param(Assets.CATEGORY_ID) long categoryId,
			@Param(Assets.SUBCATEGORY_ID) long subcategoryId);
	
	@Query("select p from Products p "
			+ " join p.category c "
			+ " join p.subcategories s "
			+ " where c.id = :"+Assets.CATEGORY_ID+" and s.id = :"+Assets.SUBCATEGORY_ID+" and "
			+ " (p.name  like %:"+Assets.SEARCH+"% OR p.description like %:"+Assets.SEARCH+"%) ")
	public Page<Products> findProductsByCategorySubCat(Pageable pageable,
			@Param(Assets.CATEGORY_ID) long categoryId,
			@Param(Assets.SUBCATEGORY_ID) long subcategoryId,
			@Param(Assets.SEARCH) String  search);
}
