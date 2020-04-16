package pl.org.grzybek.har.order.domain

import pl.org.grzybek.har.order.domain.model.OrderedProduct
import pl.org.grzybek.har.order.dto.OrderBuyerCreateDto
import pl.org.grzybek.har.order.dto.OrderCreateDto
import pl.org.grzybek.har.order.dto.OrderProductCreateDto
import spock.lang.Specification

class OrderFactoryTest extends Specification {

    def priceCalculator = Mock(PriceCalculator)
    def orderedProductFactor = Mock(OrderedProductFactory)

    def orderFactory = new OrderFactory(priceCalculator, orderedProductFactor)

    def 'should create order'() {
        given:
        def total = 19
        def email = 'test@email'
        def product = new OrderProductCreateDto()
        def dto = new OrderCreateDto(
                products: [product],
                buyer: new OrderBuyerCreateDto(
                        email: email
                )
        )
        def orderedProducts = [Mock(OrderedProduct)]
        orderedProductFactor.createOrderedProducts(dto) >> orderedProducts
        priceCalculator.calculateTotal(orderedProducts) >> total

        when:
        def result = orderFactory.createOrder(dto)

        then:
        result.totalPrice == total
        result.buyer.email == email
        result.orderedProducts == orderedProducts
    }
}
