package cz.muni.fi.pa165;

import cz.muni.fi.pa165.InMemoryDatabaseSpring;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Vojtech Krajnansky
 */
@Configuration
@Import(InMemoryDatabaseSpring.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.service")
public class ServiceConfiguration {

	@Bean
	public Mapper dozer() {
		return new DozerBeanMapper();
	}
}