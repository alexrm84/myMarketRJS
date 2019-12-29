package alexrm84.DTOs;

import alexrm84.entities.Order;
import alexrm84.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDTO implements Serializable {
    private static final long serialVersionUID = -5269633442454974197L;

    private Long id;
    private Product product;
    private Long orderId;
    private int quantity;
    private BigDecimal itemPrice;
    private BigDecimal totalPrice;
}
