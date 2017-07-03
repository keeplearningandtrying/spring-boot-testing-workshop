package org.exampledriven.springboottestingworkshop.solution.service;

import org.exampledriven.springboottestingworkshop.client.PostClient;
import org.exampledriven.springboottestingworkshop.domain.Person;
import org.exampledriven.springboottestingworkshop.domain.PersonAndPost;
import org.exampledriven.springboottestingworkshop.domain.Post;
import org.exampledriven.springboottestingworkshop.service.PostService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Created by Peter_Szanto on 7/3/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PostServiceStubTestConfigurationTest {

    protected static final String STUBBED_TITLE = "stubbed";
    @Autowired
    private PostService postService;

    @Test
    public void readPersonAndPost() throws Exception {

        // setup postClient
        Person expectedPerson = new Person();
        expectedPerson.setFirstName("John");
        expectedPerson.setLastName("Doe");
        expectedPerson.setId(1);

        List<Post> expectedPosts = new LinkedList<>();
        Post post = new Post();
        post.setId(6);
        expectedPosts.add(post);

        // perform
        PersonAndPost actual = postService.readPersonAndPost(expectedPerson.getFirstName(), expectedPerson.getLastName());

        assertEquals(expectedPerson.getFirstName(), actual.getPerson().getFirstName());
        assertEquals(expectedPerson.getLastName(), actual.getPerson().getLastName());

        assertEquals(1, actual.getPosts().size());

        Post firtPost = actual.getPosts().get(0);
        assertEquals(STUBBED_TITLE, firtPost.getTitle());
        assertEquals(100, firtPost.getId());

    }

    @TestConfiguration
    public static class Config {

        @Bean
        public PostClient postClient() {
            return new PostClient(new RestTemplateBuilder()) {

                @Override
                public List<Post> readPosts(int userId) {
                    List<Post> stubResponse = new LinkedList<>();
                    Post post = new Post();
                    post.setId(100);
                    post.setTitle(STUBBED_TITLE);

                    stubResponse.add(post);

                    return stubResponse;
                }
            };
        }

      }

}