package com.unicomlabs.das.virtualization.datasource;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.unicomlabs.das.virtualization.dao.content", sqlSessionTemplateRef  = "contentSqlSessionTemplate")
public class DataSource2Config {
	@Bean(name = "contentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.content")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "contentSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("contentDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis-mappers/CustomSqlMapper.xml"));//指定mapper.xml路径
        return bean.getObject();
    }

    @Bean(name = "contentTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("contentDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "contentSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("contentSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "contentJdbcTemplate")
    public JdbcTemplate contentJdbcTemplate(@Qualifier("contentDataSource")DataSource contentDataSource){
        return new JdbcTemplate(contentDataSource);
    }
}