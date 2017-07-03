package org.exampledriven.springboottestingworkshop.solution.repository;

import org.exampledriven.springboottestingworkshop.domain.Person;
import org.exampledriven.springboottestingworkshop.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Peter Szanto on 7/2/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void findPersonByFirstNameAndLastName() throws Exception {

        Person expected = new Person();
        expected.setId(1);
        expected.setFirstName("John");
        expected.setLastName("Doe");

        assertEquals(expected, personRepository.findPersonByFirstNameAndLastName(expected.getFirstName(), expected.getLastName()) );
    }

}