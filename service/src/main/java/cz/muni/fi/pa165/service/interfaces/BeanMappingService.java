package cz.muni.fi.pa165.service.interfaces;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Vojtech Krajnansky
 */

public interface BeanMappingService {
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mappedClass);

    public  <T> T mapTo(Object u, Class<T> mappedClass);

    public Mapper getMapper();
}