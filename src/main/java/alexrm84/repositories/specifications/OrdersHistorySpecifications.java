package alexrm84.repositories.specifications;

import alexrm84.entities.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrdersHistorySpecifications {
    public static Specification<Order> userIdEqual(Long id) {
        return (Specification<Order>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), id);
    }

    public static Specification<Order> createdAtGreaterThanOrEq(LocalDateTime value) {
        return (Specification<Order>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), value);
    }

    public static Specification<Order> createdAtLesserThanOrEq(LocalDateTime value) {
        return (Specification<Order>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), value);
    }
}
