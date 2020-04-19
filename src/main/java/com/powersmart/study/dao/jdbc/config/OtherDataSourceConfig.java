package com.powersmart.study.dao.jdbc.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author 冰飞江南
 * @Title: 其他数据源配置
 * @history 2020年04月19日 冰飞江南 新建
 * @since JDK1.8
 */
@Configuration
@PropertySource(value = "classpath:jdbc/jdbc-other.properties")
public class OtherDataSourceConfig {

    /**
     * 创建数据源
     * @return
     */
    @Bean(name = "OtherDataSource")
    @Qualifier("OtherDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.other")
    DataSource createDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源的JDBC
     * @param otherDataSource
     * @return
     */
    @Bean(name = "OtherJdbcTemplate")
    JdbcTemplate createJdbcTemplate(@Qualifier("OtherDataSource")DataSource otherDataSource) {
        return new JdbcTemplate(otherDataSource);
    }

    /**
     * 数据源的JDBC
     * @param defaultDataSource
     * @return
     */
    @Bean(name = "OtherNamedParameterJdbcTemplate")
    NamedParameterJdbcTemplate createNamedParameterJdbcTemplate(@Qualifier("OtherDataSource")DataSource defaultDataSource) {
        return new NamedParameterJdbcTemplate(defaultDataSource);
    }

    /**
     * 定义数据源的J事务管理器
     * @param otherDataSource
     * @return
     */
    @Bean("OtherTransactionManager")
    public PlatformTransactionManager createTransactionManager(@Qualifier("OtherDataSource")DataSource otherDataSource) {
        return new DataSourceTransactionManager(otherDataSource);
    }

}
