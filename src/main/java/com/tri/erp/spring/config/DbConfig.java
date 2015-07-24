package com.tri.erp.spring.config;
 
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@PropertySource(value = "classpath:application-${spring.profiles.active}.properties")
@EnableTransactionManagement()
@EnableJpaRepositories("com.tri.erp.spring.repo")
@Configuration
public class DbConfig {

	@Autowired
	Environment env;
	
	@Bean
	public HikariDataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();

        ds.setMaximumPoolSize(50);
        ds.setConnectionTestQuery("SELECT 1");
        ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        ds.addDataSourceProperty("url", env.getRequiredProperty("db.url"));
        ds.addDataSourceProperty("user", env.getRequiredProperty("db.username"));
        ds.addDataSourceProperty("password", env.getRequiredProperty("db.password"));
        ds.addDataSourceProperty("cachePrepStmts", true);
        ds.addDataSourceProperty("prepStmtCacheSize", 250);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

		return ds;
	}
	
	@Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
	
	
	@Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(HikariDataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
        vendorAdapter.setDatabase(Database.MYSQL);
		
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.tri.erp.spring.model");
        factory.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        properties.setProperty("hibernate.cache.use_query_cache", "true");
        properties.setProperty("hibernate.generate_statistics", "true");
        properties.setProperty("net.sf.ehcache.configurationResourceName", "/firefly-ehcache.xml");

        factory.setJpaProperties(properties);
        
        factory.afterPropertiesSet();
 
        return factory.getObject(); 
	}
	
	@Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setJpaDialect(jpaDialect);
        
        return txManager; 
	 }
}
