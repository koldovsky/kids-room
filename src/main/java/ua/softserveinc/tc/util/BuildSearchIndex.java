package ua.softserveinc.tc.util;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The only meaning for this class is to build the Lucene index at application
 * startup. This is needed in this example because the database is filled
 * before and each time the web application is started. In a normal web
 * application probably you don't need to do this.
 *
 * @author netgloo
 */
@Component
public class BuildSearchIndex
        implements ApplicationListener<ContextRefreshedEvent> {

    @Log
    private static Logger log;
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @PersistenceContext
    private EntityManager entityManager;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create an initial Lucene index for the data already present in the
     * database.
     * This method is called during Spring's startup.
     *
     * @param event Event raised when an ApplicationContext gets initialized or
     * refreshed.
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        }
        catch (InterruptedException e) {
            log.error(
                    "An error occurred trying to build the serach index: " +
                            e.toString());

            Thread.currentThread().interrupt();
        }
        return;
    }


}
