package cz.muni.fi.pa165.service;

import java.io.Serializable;
import java.lang.reflect.Field;

final class TestUtils {

    /*
     * It's better to use reflection to set Id to test-related entities, than to make an id mutable.
     */
    static void setId(Serializable entity, long id) throws IllegalAccessException, NoSuchFieldException {
        Class<? extends Serializable> clh = entity.getClass();
        Field idField = null;
        try {
            idField = clh.getSuperclass().getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            idField = clh.getDeclaredField("id");
        }
        idField.setAccessible(true);
        idField.set(entity, id);
        idField.setAccessible(false);
    }
}
