package br.com.apiProducts.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String img;
	private Integer codigo;
	@JsonInclude(Include.NON_EMPTY)
	@JoinColumn(name="category_id",  nullable=true)
	@OneToMany(fetch = FetchType.LAZY,  cascade=CascadeType.MERGE)
	private List<SubCategories> subcategories;
	
}
