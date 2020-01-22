package br.com.apiProducts.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "subcategories")
@Entity
public class SubCategories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String img;
	@Column(name = "category_id", nullable=true)
	private Long categoryId;
	
}