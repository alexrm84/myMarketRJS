package alexrm84.controllers;

import alexrm84.entities.Order;
import alexrm84.entities.User;
import alexrm84.services.OrderService;
import alexrm84.services.ProductService;
import alexrm84.services.UserService;
import alexrm84.utils.Cart;
import alexrm84.utils.OrdersFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private OrderService orderService;
    private ProductService productService;
    private UserService userService;
    private Cart cart;

    @Autowired
    public ProfileController(OrderService orderService, ProductService productService, UserService userService, Cart cart) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.cart = cart;
    }

    @GetMapping("")
    public String showProfile(Model model, Principal principal){
        model.addAttribute("user", userService.findByPhone(principal.getName()));
        User user = userService.findByPhone(principal.getName());
        return "profile";
    }

    @GetMapping("/update")
    public String updateProfile(Model model, Principal principal){
        return "redirect:/profile";
    }

    @GetMapping("/newPass")
    public String newPassword(Model model, Principal principal){
        return "redirect:/profile";
    }

    @GetMapping("/history")
    public String showHistory(Model model, Principal principal,
                              @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                              @RequestParam(value = "dateBefore", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateBefore){
        User user = userService.findByPhone(principal.getName());
        OrdersFilter ordersFilter = new OrdersFilter(dateFrom, dateBefore, user.getId());
        List<Order> orders = orderService.findAllByFiltering(ordersFilter.getSpecification(), Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateBefore", dateBefore);
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "profile";
    }
}
