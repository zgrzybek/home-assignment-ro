package pl.org.grzybek.har.order.domain

import pl.org.grzybek.har.order.domain.model.OrderedProduct
import spock.lang.Specification

class PriceCalculatorTest extends Specification {

    def priceCalculator = new PriceCalculator()

    def 'should return 0 for no products'() {
        when:
        def result = priceCalculator.calculateTotal([])

        then:
        result == 0
    }

    def 'should calculate total'() {
        given:
        def a = new OrderedProduct(quantity: 2, price: 2)
        def b = new OrderedProduct(quantity: 2, price: 3)

        when:
        def result = priceCalculator.calculateTotal([a, b])

        then:
        result == 10
    }
}
