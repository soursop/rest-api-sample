package com.demo.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

public class SelectRepositoryFactoryBean <T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {
    /**
     * Returns a {@link RepositoryFactorySupport}.
     * 
     * @param entityManager
     * @return
     */
    protected RepositoryFactorySupport createRepositoryFactory(
            EntityManager entityManager) {
 
        return new SelectRepositoryFactory<T, ID>(entityManager);
    }
}

class SelectRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
	
	private final EntityManager entityManager;

	public SelectRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		Assert.notNull(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		if (SelectRepository.class.isAssignableFrom(repositoryInterface)) {
			return SelectRepository.class;
		}
		return super.getRepositoryBaseClass(metadata);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		if (SelectRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
		      return new SelectRepositoryImpl<T, ID>((Class<T>) metadata.getDomainType(), entityManager);
		}
		return super.getTargetRepository(metadata);
	}
	
}

