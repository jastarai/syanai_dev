package jp;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 
 * @author
 *
 */

@Configuration
public class AppContext {
	/**
	 * 
	 * @param dataSource
	 * @return
	 */
    @Autowired
    @Bean
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier("dataSource") DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(false);

        return dataSourceInitializer;
    }

    /**
     * 
     * @param dataSource
     * @return
     */
    @Autowired
    @Bean
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    /**
     * 
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Autowired
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(
                new DefaultResourceLoader());

        // MyBatis のコンフィグレーションファイル
        bean.setConfigLocation(resolver.getResource("classpath:static/config/mybatis-config.xml"));
        // MyBatis で使用する SQL ファイル群
        bean.setMapperLocations(resolver.getResources("classpath:sql/*.xml"));

        return new SqlSessionTemplate(bean.getObject());
    }
}
