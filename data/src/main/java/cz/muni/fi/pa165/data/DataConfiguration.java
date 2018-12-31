package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {DataLoaderFacadeImpl.class})
public class DataConfiguration {

    @Autowired
    private DataLoaderFacade dataLoaderFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        dataLoaderFacade.initData();
    }
}