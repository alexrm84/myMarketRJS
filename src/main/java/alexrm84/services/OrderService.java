package alexrm84.services;

import alexrm84.entities.Order;
import alexrm84.entities.User;
import alexrm84.repositories.OrderRepository;
import alexrm84.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private Cart cart;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order createOrder(User user, Map<String, String> params){
        Order order = new Order(user, params.get("phone"), params.get("address"));
        cart.getItems().values().stream().forEach(item -> order.addItem(item));
        cart.clear();
        order.setStatus(Order.Status.CREATED);
        return orderRepository.save(order);
    }
}
