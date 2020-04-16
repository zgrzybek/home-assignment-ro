package pl.org.grzybek.har.product.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.org.grzybek.har.AbstractIntegrationSpecification
import pl.org.grzybek.har.product.dto.ProductCreateDto
import pl.org.grzybek.har.product.dto.ProductDto
import pl.org.grzybek.har.product.infrastructure.ProductRepository
import spock.lang.Shared
import spock.lang.Stepwise

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
class ProductControllerIntegrationTest extends AbstractIntegrationSpecification {

    @Autowired
    private MockMvc mockMvc
    @Autowired
    private ProductRepository productRepository
    @Shared
    UUID createdId

    def 'step1: should create product'() {
        given:
        def dto = new ProductCreateDto(
                name: 'testName',
                price: BigDecimal.valueOf(13)
        )

        when:
        def createResult = mockMvc.perform(post("/api/v1/products")
                .contentType(APPLICATION_JSON_VALUE)
                .content(toJson(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        ProductDto createdProduct = toObject(createResult.andReturn(), ProductDto)
        createdId = createdProduct.getSku()

        then:
        createdProduct.name == 'testName'
        createdProduct.price == 13
        createdProduct.sku != null
        createdProduct.creationTime != null
    }

    def 'step2: should get product'() {
        when:
        def getResult = mockMvc.perform(get("/api/v1/products/" + createdId)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        def getProduct = toObject(getResult.andReturn(), ProductDto)

        then:
        getProduct.name == 'testName'
        getProduct.price == 13
        getProduct.sku != null
        getProduct.creationTime != null
    }

    def 'step3: should find page of products'() {
        when:
        def getResult = mockMvc.perform(get("/api/v1/products")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        def page = toObject(getResult.andReturn(), SerializableProductPageDto)

        then:
        page.content.size() > 0
        page.content*.sku.contains(createdId)
    }

    def 'step4: should update product'() {
        given:
        def dto = new ProductDto(
                name: 'updatedName',
                price: BigDecimal.valueOf(14)
        )

        when:
        mockMvc.perform(put("/api/v1/products/" + createdId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(toJson(dto)))
                .andExpect(status().isOk())
        def updatedProduct = productRepository.findById(createdId).get()

        then:
        updatedProduct.name == 'updatedName'
        updatedProduct.price == 14
    }

    def 'step5: should soft delete product'() {
        when:
        mockMvc.perform(delete("/api/v1/products/" + createdId)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
        def product = productRepository.findById(createdId)
        def activeProduct = productRepository.findActiveById(createdId)

        then: 'can retrieve object from the db'
        product.isPresent()

        and: 'but active object should not be retrievable'
        !activeProduct.isPresent()
        product.get().isDeleted()
    }
}
