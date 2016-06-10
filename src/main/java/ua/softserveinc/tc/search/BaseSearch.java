package ua.softserveinc.tc.search;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by edward on 5/16/16.
 */
public abstract class BaseSearch <T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected String[] searchFields;

    abstract Class<T> getTClass();

    @Transactional
    public List<T> search(String text) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(getTClass()).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields(searchFields)
                        .matching(text)
                        .createQuery();

        javax.persistence.Query persistenceQuery =
                fullTextEntityManager.createFullTextQuery(query, getTClass());

        return persistenceQuery.getResultList();
    }

}
