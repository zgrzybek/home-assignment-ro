package pl.org.grzybek.har.product.domain

import pl.org.grzybek.har.product.domain.model.Product
import pl.org.grzybek.har.product.dto.ProductCreateDto
import pl.org.grzybek.har.product.dto.ProductUpdateDto
import spock.lang.Specification

class ProductFactoryTest extends Specification {

    def productSkuProvider = Mock(ProductSkuProvider)
    def productFactory = new ProductFactory(productSkuProvider)

    def 'should create product'() {
        given:
        def uuid = UUID.randomUUID()
        def dto = new ProductCreateDto(
                name: 'testName',
                price: 13
        )
        productSkuProvider.provideNew() >> uuid

        when:
        def result = productFactory.create(dto)

        then:
        result.name == dto.name
        result.price == dto.price
        result.id == uuid
        result.creationTime == null
        !result.deleted
    }

    def 'should update product'() {
        given:
        def product = new Product(
                name: 'name',
                price: 13
        )
        def dto = new ProductUpdateDto(
                name: 'updatedName',
                price: 21
        )

        when:
        productFactory.update(product, dto)

        then:
        product.name == dto.name
        product.price == dto.price
    }
}
