package ua.softserveinc.tc.search;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ua.softserveinc.tc.entity.User;

/**
 * Created by edward on 5/14/16.
 */
@Repository
@Transactional
public class UserSearch {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<User> search(String text) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(User.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("firstName", "lastName", "email", "phoneNumber")
                        .matching(text)
                        .createQuery();

        javax.persistence.Query persistenceQuery =
                fullTextEntityManager.createFullTextQuery(query, User.class);

        @SuppressWarnings("unchecked")
        List<User> results = persistenceQuery.getResultList();

        return results;
    }

}
