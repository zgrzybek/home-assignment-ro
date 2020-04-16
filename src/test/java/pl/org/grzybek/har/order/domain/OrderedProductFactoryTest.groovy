package pl.org.grzybek.har.order.domain

import pl.org.grzybek.har.order.dto.OrderCreateDto
import pl.org.grzybek.har.order.dto.OrderProductCreateDto
import pl.org.grzybek.har.product.domain.model.Product
import pl.org.grzybek.har.product.infrastructure.ProductRepository
import spock.lang.Specification

class OrderedProductFactoryTest extends Specification {

    def productRepository = Mock(ProductRepository)
    def factory = new OrderedProductFactory(productRepository)

    def 'should should create products'() {
        given:
        def sku = UUID.randomUUID()
        def productDto = new OrderProductCreateDto(
                productSku: sku,
                quantity: 2
        )
        def dto = new OrderCreateDto(products: [productDto])
        def product = new Product(
                price: 13
        )
        productRepository.findActiveById(sku) >> Optional.of(product)

        when:
        def result = factory.createOrderedProducts(dto)
        def created = result.first()

        then:
        result.size() == 1
        created.price == product.price
        created.productSku == productDto.productSku
        created.quantity == productDto.quantity
    }
}
