package cz.muni.fi.pa165.service;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.service.interfaces.BeanMappingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Vojtech Krajnansky
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper dozer;

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mappedClass) {
        List<T> mapped = new ArrayList<>();

        for (Object o : objects) {
            mapped.add(dozer.map(o, mappedClass));
        }
        return mapped;
    }

    public <T> T mapTo(Object object, Class<T> mappedClass) {
        return dozer.map(object, mappedClass);
    }

    public Mapper getMapper() {
        return dozer;
    }
}
