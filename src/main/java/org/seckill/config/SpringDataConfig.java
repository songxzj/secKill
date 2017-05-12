package org.seckill.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.seckill.aspectpojo.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
/*@PropertySources({
        @PropertySource("classpath:config/jdbc.properties"),
        @PropertySource("classpath:config/redis.properties")
})*/

@Configuration
@PropertySource("classpath:config/jdbc.properties")
@EnableAspectJAutoProxy    // 启用 AspectJ 自动代理
public class SpringDataConfig {

    @Autowired
    Environment environment;

    /**
     * 数据库连接池配置
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        //初始化连接大小
        dataSource.setInitialSize(1);
        //连接池最大数量
        dataSource.setMaxActive(20);
        //连接池最小空闲
        dataSource.setMinIdle(1);
        //获取连接最大等待时间
        dataSource.setMaxWait(60000L);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000L);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000L);

        return dataSource;
    }

    /**
     * mybatis文件配置
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DruidDataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);

        return sqlSessionFactory;
    }


}
