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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@MapperScan(basePackages = "com.unicomlabs.das.virtualization.dao.base", sqlSessionTemplateRef  = "baseSqlSessionTemplate")
public class DataSource1Config {
	@Bean(name = "baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis-mappers/CustomColumnsMapper.xml"));//指定mapper.xml路径
        return bean.getObject();
    }

    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("baseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean(name = "baseJdbcTemplate")
    public JdbcTemplate baseJdbcTemplate(@Qualifier("baseDataSource")DataSource baseDataSource){
        return new JdbcTemplate(baseDataSource);
    }
}
