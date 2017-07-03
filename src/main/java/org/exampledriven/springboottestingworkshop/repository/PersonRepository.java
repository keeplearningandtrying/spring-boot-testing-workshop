package org.exampledriven.springboottestingworkshop.repository;

import org.exampledriven.springboottestingworkshop.domain.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Peter Szanto on 7/2/2017.
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Person findPersonByFirstNameAndLastName(String firstName, String lastName);

}
