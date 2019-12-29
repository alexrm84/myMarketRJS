package alexrm84.controllers.rest;

import alexrm84.DTOs.OrderItemDTO;
import alexrm84.entities.OrderItem;
import alexrm84.entities.Product;
import alexrm84.services.ProductService;
import alexrm84.services.UserService;
import alexrm84.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRestController {

    private ProductService productService;
    private UserService userService;
    private Cart cart;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @GetMapping("/add")
    public void addProductToCart(@RequestParam("id") Long id){
        cart.addProduct(id);
    }

    @GetMapping("")
    public List<OrderItemDTO> showCart(){
        return cart.getItemsDTO();
    }



//    @GetMapping("/reduce")
//    public String reduceProductFromCart(@RequestParam("id") Long id) {
//        cart.reduceProduct(productService.findById(id).get());
//        return "redirect:/cart";
//    }
}
