package ua.softserveinc.tc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by melancholiya on 05.12.2016.
 */

/**
 * annotation @Configuration - says that it is a Spring configuration;
 * annotation @EnableTransactionManagement - turn on TransactionManagement to work with DB;
 * annotation @ComponentScan - tell Spring where it should need to find Entity, DAO, Service etc;
 * <p>
 * TODO: 1. Is this a good idea to create a test-hibernate.properties file and then write there all properties
 * TODO:    If so, I need to use an Environment variable.
 * TODO:    This code is duplicated with AppConfig.java;
 * <p>
 * TODO: 2. Add comments for all methods;
 * <p>
 * TODO: 3. Think about in memory db; (more efficient, nobody can see it)
 * <p>
 * TODO: 4. Remove all comments; Add javadocs;
 */

@Configuration
@EnableTransactionManagement
@ComponentScan("ua.softserveinc.tc")
public class TestBaseConfigClass {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/test_kidsroom";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "root";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "melancholiya20";

    // loading empty file;
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES = "import.sql";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "true";
    private static final String PROPERTY_NAME_ENTITY_MANAGER_PACKAGES_TO_SCAN = "ua.softserveinc.tc.entity";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "create";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();

        managerDataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        managerDataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
        managerDataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        managerDataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

        return managerDataSource;
    }

    // Building SessionFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITY_MANAGER_PACKAGES_TO_SCAN);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", PROPERTY_NAME_HIBERNATE_DIALECT);
        properties.put("hibernate.show_sql", PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        properties.put("hibernate.hbm2ddl.auto", PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO);
        properties.put("hibernate.hbm2ddl.import_files", PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES); //https://dzone.com/articles/easy-integration-testing

        return properties;
    }

}
