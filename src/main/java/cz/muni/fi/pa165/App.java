package cz.muni.fi.pa165;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("development");

        emf.close();
    }
}
