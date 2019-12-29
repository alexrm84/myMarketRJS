package alexrm84.utils;

import alexrm84.DTOs.OrderItemDTO;
import alexrm84.entities.OrderItem;
import alexrm84.entities.Product;
import alexrm84.entities.User;
import alexrm84.services.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {

    private ProductService productService;
    private Map<Long, OrderItem> items;
    private BigDecimal totalPrice;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init(){
        items = new LinkedHashMap<>();
    }

    public void addProduct(Long id){
        Product product = productService.findById(id).get();
        System.out.println(product.toString());
        OrderItem item = items.get(product.getId());
        if (item==null) {
            item = new OrderItem();
            item.setProduct(product);
            item.setItemPrice(product.getPrice());
            item.setQuantity(0);
        }
        item.setQuantity(item.getQuantity()+1);
        item.setTotalPrice(item.getItemPrice().multiply(new BigDecimal(item.getQuantity())));
        items.put(product.getId(), item);
        recalculate();
        items.values().stream().forEach(System.out::println);
    }

    public void reduceProduct(Product product){
        OrderItem item = items.get(product.getId());
        if (item.getQuantity()==1){
            items.remove(product.getId());
        }else {
            item.setQuantity(item.getQuantity()-1);
            item.setTotalPrice(item.getItemPrice().multiply(new BigDecimal(item.getQuantity())));
            items.put(product.getId(), item);
        }
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = new BigDecimal(0);
    }

    private void recalculate() {
        totalPrice = new BigDecimal(0);
        items.values().stream().forEach(oi -> totalPrice = totalPrice.add(oi.getTotalPrice()));
    }

    public List<OrderItemDTO> getItemsDTO(){
        List<OrderItemDTO> list = items.values().stream()
                                    .map(item -> convertOrderItemToDTO(item))
                                    .collect(Collectors.toList());
        list.forEach(System.out::println);
        return list;
    }

    private OrderItemDTO convertOrderItemToDTO(OrderItem orderItem){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProduct(orderItem.getProduct());
        orderItemDTO.setOrderId(orderItem.getOrder().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setItemPrice(orderItem.getItemPrice());
        orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDTO;
    }

}
