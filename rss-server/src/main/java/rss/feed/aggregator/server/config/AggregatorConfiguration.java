package rss.feed.aggregator.server.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import rss.feed.aggregator.server.dao.FeedDao;
import rss.feed.aggregator.server.dao.FeedDaoImpl;
import rss.feed.aggregator.server.dao.FeedItemDao;
import rss.feed.aggregator.server.dao.FeedItemDaoImpl;
import rss.feed.aggregator.server.dao.UserDao;
import rss.feed.aggregator.server.dao.UserDaoImpl;
import rss.feed.aggregator.server.service.AggregatorSrvice;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@PropertySources({
	@PropertySource("classpath:/database.properties"),
	@PropertySource(value="${catalina.home}/conf/application.properties", ignoreResourceNotFound=true)
	})
@ComponentScan(basePackages = "rss.feed.aggregator.server")
public class AggregatorConfiguration extends WebMvcConfigurerAdapter {

	private static final Logger T = Logger.getLogger(AggregatorConfiguration.class);
	
	@Autowired
	private Environment env;

	@Autowired
	AggregatorSrvice aggregatorSrvice;
	
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }
	
	@Bean
	public UserDao userDao() {
		return new UserDaoImpl();
	}
	
	@Bean
	public FeedDao feedDao() {
		return new FeedDaoImpl();
	}
	
	@Bean
	public FeedItemDao feedItemDao() {
		return new FeedItemDaoImpl();
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		T.debug("init dataSource to: '" + dataSource.getUrl()+ "', '" + dataSource.getUsername() + "'");
		
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		Properties hibernateProperties = new Properties();
		
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("rss.feed.aggregator.server.entity");
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		
		return sessionFactoryBean;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}
	
	@Scheduled(fixedDelay=600000)
	public void updateFeeds() {
		aggregatorSrvice.updateFeeds();
	}
}
