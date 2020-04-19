package com.powersmart.study.dao.jdbc.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * @author 冰飞江南
 * @Title: 主数据源配置
 * @history 2020年04月19日 冰飞江南 新建
 * @since JDK1.8
 */
@Aspect
@Configuration
@PropertySource(value = "classpath:jdbc/jdbc-default.properties")
public class DefaultDataSourceConfig {

    @Autowired
    @Qualifier("DefaultTransactionManager")
    private PlatformTransactionManager transactionManager;

    /**
     * 创建DataSource
     * @return
     */
    @Bean(name = "DefaultDataSource")
    @Qualifier("DefaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.default")
    DataSource createDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源的JDBC
     * @param defaultDataSource
     * @return
     */
    @Bean(name = "DefaultJdbcTemplate")
    JdbcTemplate createJdbcTemplate(@Qualifier("DefaultDataSource")DataSource defaultDataSource) {
        return new JdbcTemplate(defaultDataSource);
    }

    /**
     * 数据源的JDBC
     * @param defaultDataSource
     * @return
     */
    @Bean(name = "DefaultNamedParameterJdbcTemplate")
    NamedParameterJdbcTemplate createNamedParameterJdbcTemplate(@Qualifier("DefaultDataSource")DataSource defaultDataSource) {
        return new NamedParameterJdbcTemplate(defaultDataSource);
    }

    /**
     * 定义数据源的J事务管理器
     * @param defaultDataSource
     * @return
     */
    @Bean("DefaultTransactionManager")
    public PlatformTransactionManager createTransactionManager(@Qualifier("DefaultDataSource")DataSource defaultDataSource) {
        PlatformTransactionManager d = new DataSourceTransactionManager(defaultDataSource);
        return d;
    }

    /**
     * 定义事务拦截规则
     * @return
     */
    @Bean
    public TransactionInterceptor txAdvice() {
        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED_READONLY.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("insert*", txAttr_REQUIRED);
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        source.addTransactionalMethod("set*", txAttr_REQUIRED);
        source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 创建事务订阅对象
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.***.service.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
