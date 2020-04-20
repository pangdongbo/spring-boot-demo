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
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;

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

    @Autowired
    @Qualifier("DefaultTransactionInterceptor")
    private TransactionInterceptor transactionInterceptor;

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
    @Bean(name = "DefaultTransactionManager")
    public PlatformTransactionManager createTransactionManager(@Qualifier("DefaultDataSource")DataSource defaultDataSource) {
        PlatformTransactionManager d = new DataSourceTransactionManager(defaultDataSource);
        return d;
    }

    /**
     * 定义事务拦截规则
     * @return
     */
    @Bean("DefaultTransactionInterceptor")
    public TransactionInterceptor txAdvice() {

        /**
         * 必须要存在事务
         */
        RuleBasedTransactionAttribute txRequiredReadonly = new RuleBasedTransactionAttribute();
        txRequiredReadonly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txRequiredReadonly.setReadOnly(false);
        txRequiredReadonly.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        /**
         * 支持事务
         */
        RuleBasedTransactionAttribute txSupports = new RuleBasedTransactionAttribute();
        txSupports.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        /**
         * 定义匹配事务的方法规则
         */
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", txRequiredReadonly);
        source.addTransactionalMethod("insert*", txRequiredReadonly);
        source.addTransactionalMethod("save*", txRequiredReadonly);
        source.addTransactionalMethod("delete*", txRequiredReadonly);
        source.addTransactionalMethod("del*", txRequiredReadonly);
        source.addTransactionalMethod("update*", txRequiredReadonly);
        source.addTransactionalMethod("init*", txRequiredReadonly);
        source.addTransactionalMethod("get*", txSupports);
        source.addTransactionalMethod("query*", txSupports);
        source.addTransactionalMethod("find*", txSupports);
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 创建事务订阅对象
     * @return
     */
    @Bean(name = "DefaultAdviceAdvisor")
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.powersmart..*.service..*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

}
