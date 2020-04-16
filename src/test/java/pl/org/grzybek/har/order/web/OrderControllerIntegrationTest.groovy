package pl.org.grzybek.har.order.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.org.grzybek.har.AbstractIntegrationSpecification
import pl.org.grzybek.har.order.dto.OrderBuyerCreateDto
import pl.org.grzybek.har.order.dto.OrderCreateDto
import pl.org.grzybek.har.order.dto.OrderDto
import pl.org.grzybek.har.order.dto.OrderProductCreateDto
import pl.org.grzybek.har.product.dto.ProductCreateDto
import pl.org.grzybek.har.product.infrastructure.ProductRepository
import pl.org.grzybek.har.product.web.ProductController
import spock.lang.Shared
import spock.lang.Stepwise

import java.time.LocalDateTime

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
class OrderControllerIntegrationTest extends AbstractIntegrationSpecification {

    @Autowired
    private MockMvc mockMvc
    @Autowired
    private ProductRepository productRepository
    @Autowired
    private ProductController productController
    @Shared
    UUID createdProductId
    @Shared
    UUID createdOrderId

    def 'step0: should create product'() {
        given:
        def dto = new ProductCreateDto(
                name: 'testName',
                price: BigDecimal.valueOf(13)
        )

        when:
        def created = productController.create(dto)
        createdProductId = created.getSku()

        then:
        created != null
    }

    def 'step1: should create order'() {
        given:
        def dto = new OrderCreateDto(
                buyer: new OrderBuyerCreateDto(email: 'test@Email'),
                products: [new OrderProductCreateDto(
                        productSku: createdProductId,
                        quantity: 7
                )]
        )

        when:
        def createResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(APPLICATION_JSON_VALUE)
                .content(toJson(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        OrderDto createdOrder = toObject(createResult.andReturn(), OrderDto)
        createdOrderId = createdOrder.getId()

        then:
        createdOrder.totalPrice == 91
        createdOrder.buyer.email == 'test@Email'
        createdOrder.creationTime != null
        createdOrder.products[0].productSku == createdProductId
        createdOrder.products[0].quantity == 7
        createdOrder.products[0].price == 13
    }

    def 'step2: should get order'() {
        when:
        def getResult = mockMvc.perform(get("/api/v1/orders/" + createdOrderId)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        def order = toObject(getResult.andReturn(), OrderDto)

        then:
        order.totalPrice == 91
        order.buyer.email == 'test@Email'
        order.creationTime != null
        order.products[0].productSku == createdProductId
        order.products[0].quantity == 7
        order.products[0].price == 13
    }

    def 'step3: should find page of products'() {
        given:
        def from = toJson(LocalDateTime.now().minusHours(1)).replaceAll('"', '')
        def to = toJson(LocalDateTime.now().plusHours(1)).replaceAll('"', '')
        println from

        when:
        def getResult = mockMvc.perform(get("/api/v1/orders")
                .param("from", from)
                .param("to", to)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
        def page = toObject(getResult.andReturn(), SerializableOrderPageDto)

        then:
        page.content.size() > 0
        page.content*.id.contains(createdOrderId)
    }
}
