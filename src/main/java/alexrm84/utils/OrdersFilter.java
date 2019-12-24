package alexrm84.utils;

import alexrm84.entities.Order;
import alexrm84.repositories.specifications.OrdersHistorySpecifications;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OrdersFilter {
    private Specification<Order> specification;

    public Specification<Order> getSpecification() {
        return specification;
    }

    public OrdersFilter(Date dateFrom, Date dateBefore, Long id) {
        specification = Specification.where(null);

        specification = specification.and(OrdersHistorySpecifications.userIdEqual(id));

        if (dateFrom != null) {
            specification = specification.and(OrdersHistorySpecifications.createdAtGreaterThanOrEq(convertDateToLTD(dateFrom)));
        }

        if (dateBefore != null) {
            specification = specification.and(OrdersHistorySpecifications.createdAtLesserThanOrEq(convertDateToLTD(dateBefore)));
        }
    }

    private LocalDateTime convertDateToLTD(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
