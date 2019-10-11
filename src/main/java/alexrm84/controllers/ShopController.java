package alexrm84.controllers;

import alexrm84.entities.Product;
import alexrm84.services.ProductService;
import alexrm84.utils.ProductsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String showShop(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue(value = "pageSize", required = false) Integer pageSize){
        ProductsFilter productsFilter = new ProductsFilter(request);
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize==null){
            pageSize = 10;
            response.addCookie(new Cookie("pageSize", String.valueOf(pageSize)));
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("filters", productsFilter.getFiltersString());
        Page<Product> page = productService.findAllByPagingAndFiltering(productsFilter.getSpecification(), PageRequest.of(currentPage - 1, 10, Sort.Direction.ASC, "id"));
        model.addAttribute("page", page);
        return "shop";
    }

    @GetMapping("/edit")
    public String showEditProductForm(
            @RequestParam(name = "id", required = false) Long id,
            Model model){
        Product product = new Product();
        if (id!=null){
            product = productService.findById(id).get();
        }
        model.addAttribute("product", product);
        return "editProductForm";
    }

    @PostMapping("/edit")
    public String saveModifiedProduct(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }
}
