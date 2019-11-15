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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestControllerTest {

//    http://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    private ProductService productService;
//
//    @Test
//    public void getAllProductsTest() throws Exception {
//        List<Product> allProducts = Arrays.asList(
//                new Product(1L, "Milk", new BigDecimal(90)),
//                new Product(2L, "Bread", new BigDecimal(25)),
//                new Product(3L, "Cheese", new BigDecimal(320))
//        );
//
//        given(productService.findAll()).willReturn(allProducts);
//
//        mockMvc.perform(get("/api/v1/products/")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(3)))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].title", is(allProducts.get(0).getTitle())));
//    }


    @Test
    public void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].price", is(15800.0)));
    }

    @Test
    public void getOneProductTest() throws Exception {
        mockMvc.perform(get("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Смартфон Samsung Galaxy A50")));
    }

    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setTitle("phone");
        product.setPrice(new BigDecimal(15000));
//        Category category = this.restTemplate.getForEntity("/api/v1/products", Product.class).getBody().getCategory();
        Category category = new Category();
        category.setTitle("phones");
        category.setId(1L);
        product.setCategory(category);
        ResponseEntity<Product> response = this.restTemplate.postForEntity("/api/v1/products/", product, Product.class);

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
