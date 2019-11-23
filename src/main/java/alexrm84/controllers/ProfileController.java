package alexrm84.controllers;

import alexrm84.configs.SecurityConfig;
import alexrm84.entities.Order;
import alexrm84.entities.User;
import alexrm84.services.OrderService;
import alexrm84.services.ProductService;
import alexrm84.services.UserService;
import alexrm84.utils.Cart;
import alexrm84.utils.NewPasswordDTO;
import alexrm84.utils.OrdersFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private OrderService orderService;
    private ProductService productService;
    private UserService userService;
    private Cart cart;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ProfileController(OrderService orderService, ProductService productService, UserService userService, Cart cart, BCryptPasswordEncoder passwordEncoder) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.cart = cart;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String showProfile(Model model, Principal principal){
        model.addAttribute("user", userService.findByPhone(principal.getName()));
        User user = userService.findByPhone(principal.getName());
        return "profile";
    }

    @GetMapping("/update")
    public String updateProfile(Model model, Principal principal){
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        return "editForm";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("user") User updateUser, BindingResult bindingResult, Principal principal, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }
        User user = userService.findByPhone(principal.getName());
        user.setPhone(updateUser.getPhone());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        userService.save(user);
        if (!user.getPhone().equals(principal.getName())){
            session.invalidate();
        }
        return "redirect:/profile";
    }

    @GetMapping("/newPass")
    public String newPassword(Model model){
        model.addAttribute("newPasswordDTO", new NewPasswordDTO());
        return "editPassForm";
    }

    @PostMapping("/newPass")
    public String confirmPassword(@Valid @ModelAttribute("newPasswordDTO") NewPasswordDTO newPasswordDTO,
                                  BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "editPassForm";
        }
        User user = userService.findByPhone(principal.getName());
        if (passwordEncoder.matches(newPasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPasswordDTO.getPassword()));
            userService.save(user);
        }
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
