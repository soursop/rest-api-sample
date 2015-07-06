package com.demo.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class SelectRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements SelectRepository<T, ID> {

	static Logger logger = Logger.getLogger(SelectRepositoryImpl.class);

	private final EntityManager em;

	public SelectRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
	}

	public SelectRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);
	}

	@Override
	public List<T> findAllBySelectInputs(List<String> stringsOfSelect) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());
		Root<T> c = query.from(getDomainClass());
		List<Selection<?>> inputs = new ArrayList<Selection<?>>();
		for (String select : stringsOfSelect) {
			inputs.add(c.get(select));
		}
		query.multiselect(inputs);
		List<T> results = em.createQuery(query).getResultList();
		return results;
	}
}
