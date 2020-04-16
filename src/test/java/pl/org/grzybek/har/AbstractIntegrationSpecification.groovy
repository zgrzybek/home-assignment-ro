package pl.org.grzybek.har

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(initializers = MongoDbInitializer)
abstract class AbstractIntegrationSpecification extends Specification {

    @Autowired
    ObjectMapper objectMapper

    @Shared
    static MongoDbContainer mongoContainer = new MongoDbContainer()

    def setupSpec() {
        mongoContainer.start()
    }

    static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            println 'Overriding Spring Properties for mongodb'

            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoContainer.getPort()

            )
            values.applyTo(configurableApplicationContext)
        }
    }

    protected String toJson(Object object) {
        return objectMapper.writeValueAsString(object)
    }

    protected <T> T toObject(MvcResult result, Class<T> clazz) {
        String resultString = result.getResponse().getContentAsString()
        return objectMapper.readValue(resultString, clazz)
    }

}
