package alexrm84.utils;

import alexrm84.entities.Product;
import alexrm84.repositories.specifications.ProductSpecifications;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;

public class ProductsFilter {
    private Specification<Product> specification;
    private StringBuilder filtersString;

    public Specification<Product> getSpecification() {
        return specification;
    }

    public StringBuilder getFiltersString() {
        return filtersString;
    }

    public ProductsFilter(HttpServletRequest request) {
        filtersString = new StringBuilder();
        specification = Specification.where(null);

        if (request.getParameter("word") != null && !request.getParameter("word").isEmpty()) {
            specification = specification.and(ProductSpecifications.titleContains(request.getParameter("word")));
            filtersString.append("&word=" + request.getParameter("word"));
        }

        if (request.getParameter("minPrice") != null && !request.getParameter("minPrice").isEmpty()) {
            specification = specification.and(ProductSpecifications.priceGreaterThanOrEq(Double.parseDouble(request.getParameter("minPrice"))));
            filtersString.append("&minPrice=" + request.getParameter("minPrice"));
        }

        if (request.getParameter("maxPrice") != null && !request.getParameter("maxPrice").isEmpty()) {
            specification = specification.and(ProductSpecifications.priceLesserThanOrEq(Double.parseDouble(request.getParameter("maxPrice"))));
            filtersString.append("&maxPrice=" + request.getParameter("maxPrice"));
        }

        if (request.getParameter("catId") != null && !request.getParameter("catId").isEmpty()) {
            specification = specification.and(ProductSpecifications.categoryId(Long.valueOf(request.getParameter("catId"))));
            filtersString.append("&catId=" + request.getParameter("catId"));
        }
    }
}
