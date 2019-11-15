package alexrm84.myMarket;

import alexrm84.entities.Category;
import alexrm84.entities.Product;
import alexrm84.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestControllerTest2 {

//    http://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setTitle("phone");
        product.setPrice(new BigDecimal(15000));
//        Category category = new Category();
//        category.setTitle("phones");
//        product.setCategory(category);
        ResponseEntity<Product> response = this.restTemplate.postForEntity("/api/v1/products", product, Product.class);

        Assert.assertNull(product.getId());
        Assertions.assertThat(response.getBody().getId()).isGreaterThan(0);
        Assertions.assertThat(response.getBody().getTitle()).isEqualTo(product.getTitle());
        Assertions.assertThat(response.getBody().getPrice()).isEqualTo(product.getPrice());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        product.setId(4L);

        Assertions.assertThat(this.restTemplate.postForEntity("/api/v1/products", product, Product.class).getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
