package br.com.apiProducts.domain;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import br.com.apiProducts.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Table(name = "products")
@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer code;
	private String name;
	private String description;
	private Integer qtd;
	private BigDecimal price;
	private String observation;
	private String ean;
	private Integer lote;
	private Status status;

	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "category_id", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = true)
	private Categories category;

	@NotFound(action = NotFoundAction.IGNORE)
	@JoinTable(name = "products_subcategories", joinColumns = @JoinColumn(name = "products_id"), inverseJoinColumns = @JoinColumn(name = "subcategories_id"))
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private List<SubCategories> subcategories;

}
