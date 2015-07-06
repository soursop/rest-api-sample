package com.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SelectRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 * If you use this method, Please Check Entity class constructor.
	 * Selected fields of Entity class should be initialized by constructor.
	 * e.g.) 
	 * 	 stringsOfSelect = Arrays.as("id", "title");
	 * 	 Article(long id, String title) { this.id = id; this.title; }
	 * 
	 * @param stringsOfSelect : select fields
	 * @return List<T>
	 */
	List<T> findAllBySelectInputs(List<String> stringsOfSelect);

}
