package org.metabuild.grobot.server.config;

import javax.sql.DataSource;

import org.metabuild.grobot.domain.TargetHostCache;
import org.metabuild.grobot.domain.TargetHostCacheImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Production specific spring configuration
 * 
 * @author jburbridge
 * @since 11/11/2012
 */
@Configuration
@Profile("production")
public class ProductionAppConfig {

	
	@Bean(name="targetCache")
	public TargetHostCache getTargetCache() {
		return new TargetHostCacheImpl();
	}
	
	@Bean(name="dataSource")
	public DataSource getDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.build();
	}
}
