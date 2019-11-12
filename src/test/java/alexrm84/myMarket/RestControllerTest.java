package alexrm84.myMarket;

import alexrm84.entities.Product;
import alexrm84.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {
    @Autowired
    private MockMvc mockMvc;
//    @MockBean
//    private ProductService productService;
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

}
