package io.tenable

import io.tenable.service.ComponentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = Application.class)

class ApplicationTests extends Specification {

    @Autowired
    ComponentService personService;

    @Unroll
    def "given a movie #movie.title save it into the database"() {
        when: "A movie is created"
        def movieSaved = personService.save(movie)
        then: "Check the movie is in the database with the correct values"
        def foundMovie = movieRepository.findOne(movieSaved.getId())
        foundMovie.title == movie.title
        foundMovie.director == movie.director

        cleanup:
        movieRepository.delete(movie)

        where:
        movie << getMovie().take(10)
    }

}
