package alexrm84.controllers;

import alexrm84.entities.Product;
import alexrm84.entities.User;
import alexrm84.services.ProductService;
import alexrm84.services.UserService;
import alexrm84.services.UserServiceImpl;
import alexrm84.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
public class CartController {

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
    public void addProductToCart(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.addProduct(productService.findById(id).get());
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("")
    public String showCart(Model model){
        model.addAttribute("items", cart.getItems().values());
        return "cart";
    }

    @GetMapping("/reduce")
    public String reduceProductFromCart(@RequestParam("id") Long id) {
        cart.reduceProduct(productService.findById(id).get());
        return "redirect:/cart";
    }
}
