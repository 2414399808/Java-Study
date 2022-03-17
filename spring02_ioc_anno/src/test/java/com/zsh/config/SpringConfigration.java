package com.zsh.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

//标志该类是Spring的核心配置类
@Configuration
@ComponentScan("com")//    <context:component-scan base-package="com"/>
@Import(DataSourceConfiguration.class)//    <import resource="classpath:xxxx"/>
//@PropertySource("classpath:jdbc.properties")//<context:property-placeholder location="classpath:jdbc.properties"/>
public class SpringConfigration {
//    @Value("${jdbc.driver}")
//    private String driver;
//    @Value("${jdbc.url}")
//    private String url;
//    @Value("${jdbc.username}")
//    private String username;
//    @Value("${jdbc.password}")
//    private String password;
//
//
//
//
//    @Bean("dataSource")//Spring 会将当前方法的返回值以指定名称存储到Spring容器当中
//    public DataSource getDataSource() throws Exception {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(driver);
//        dataSource.setJdbcUrl(url);
//        dataSource.setUser(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }

}
