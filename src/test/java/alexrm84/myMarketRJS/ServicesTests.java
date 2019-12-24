package alexrm84.myMarketRJS;

import alexrm84.services.OrderService;
import alexrm84.services.ProductService;
import alexrm84.utils.Cart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesTests {
    private ProductService productService;
    private OrderService orderService;
    private Cart cart;

    @Autowired
    public ServicesTests(ProductService productService, OrderService orderService, Cart cart) {
        this.productService = productService;
        this.orderService = orderService;
        this.cart = cart;
    }

    @Test
    public void allProductTest() {
        Assert.assertEquals(3, productService.findAll().size());
    }

    @Test
    public void productByIdtTest() {
        Assert.assertEquals("Смартфон Samsung Galaxy A10", productService.findById(2L).get().getTitle());
    }

    @Test
    public void ordersByUserTest() {
        Assert.assertEquals(0, orderService.findAllByUser(1L).size());
    }
}
