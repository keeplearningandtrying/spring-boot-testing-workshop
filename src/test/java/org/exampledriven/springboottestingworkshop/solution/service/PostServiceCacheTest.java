package org.exampledriven.springboottestingworkshop.solution.service;

import org.exampledriven.springboottestingworkshop.client.PostClient;
import org.exampledriven.springboottestingworkshop.domain.Person;
import org.exampledriven.springboottestingworkshop.domain.PersonAndPost;
import org.exampledriven.springboottestingworkshop.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Peter Szanto on 7/3/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PostServiceCacheTest {

    @Autowired
    private PostService postService;

    @SpyBean
    private PostClient postClient;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void evictAllCaches() {
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
    }

    @Test
    public void readPersonAndPost() throws Exception {
        Person expectedPerson = new Person();
        expectedPerson.setFirstName("John");
        expectedPerson.setLastName("Doe");

        PersonAndPost actual = postService.readPersonAndPost(expectedPerson.getFirstName(), expectedPerson.getLastName());

        assertEquals(expectedPerson.getFirstName(), actual.getPerson().getFirstName());
        assertEquals(expectedPerson.getLastName(), actual.getPerson().getLastName());

        assertEquals(10, actual.getPosts().size());

        PersonAndPost actual2 = postService.readPersonAndPost(expectedPerson.getFirstName(), expectedPerson.getLastName());

        verify(postClient, times(1)).readPosts(1);

    }

}