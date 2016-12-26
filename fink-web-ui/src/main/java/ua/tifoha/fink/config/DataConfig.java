package ua.tifoha.fink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ua.tifoha.fink.repositories")
@PropertySource("classpath:application.properties")
public class DataConfig {

    @Value("${datasource.driver}")
    private String driver;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.packagesToScan}")
    private String packagesToScan;

    @Bean
//	@Profile ("MySql")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    @Profile("Derby")
    public DataSource dataSourceDerby() {
//     no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.DERBY) //.H2 or .DERBY
//            .addScript("db/sql/tables_derby.sql")
//            .addScript("db/sql/insert-data.sql")
                .build();
    }

    @Bean
    @Profile("H2")
    public DataSource dataSourceH2(@Value("${datasource.H2.queryPath}") String queryPath) {
//     no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .addScript(queryPath)
//            .addScript("db/sql/insert-data.sql")
                .build();
    }

//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		return new DataSourceTransactionManager(dataSource());
//	}

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.generate_statistics", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.connection.provider_class", "org.hibernate.c3p0.internal.C3P0ConnectionProvider");

        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(packagesToScan);
        factory.setDataSource(dataSource());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }
}
