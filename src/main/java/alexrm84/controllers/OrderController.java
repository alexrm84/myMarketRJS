package alexrm84.controllers;

import alexrm84.entities.Order;
import alexrm84.entities.User;
import alexrm84.services.MailService;
import alexrm84.services.OrderService;
import alexrm84.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/orders")
//@SessionAttributes(value = "order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private MailService mailService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/createOrder")
    public  String createOrder(Model model, Principal principal){
        User user = null;
        if (principal != null){
            user = userService.findByPhone(principal.getName());
        }
        model.addAttribute("user", user);
        return "order";
    }

    @GetMapping("/confirmOrder")
    public  String confirmOrder(@RequestParam Map<String, String> params, Principal principal, HttpSession session){
        User user;
        if (principal != null){
            user = userService.findByPhone(principal.getName());
        }else {
            user = userService.save(params);
        }
        Order order = orderService.createOrder(user, params);
//        if (user.getEmail() != null) {
//            mailService.sendOrderMail(order);
//        }
        if (params.get("paymentType").equals("paypal")){
            session.setAttribute("orderId", order.getId());
            return "redirect:/paypal/buy";
        }
        return "redirect:/shop";
    }
}
