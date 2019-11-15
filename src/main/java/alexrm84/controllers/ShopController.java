package alexrm84.controllers;

import alexrm84.entities.Product;
import alexrm84.services.CategoryService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

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
            @CookieValue(value = "pageSize", required = false) Integer pageSize,
            @CookieValue(value = "viewsHistory", required = false) String viewsHistory){
        ProductsFilter productsFilter = new ProductsFilter(request);
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize==null){
            pageSize = 10;
            response.addCookie(new Cookie("pageSize", String.valueOf(pageSize)));
        }
        if (viewsHistory!=null){
            List<Long> viewHistoryIds = Arrays.stream(viewsHistory.split("z"))
                    .map(Long::valueOf).collect(Collectors.toList());
            List<Product> viewHistoryProducts = productService.findAllById(viewHistoryIds);
            model.addAttribute("viewHistoryProducts", viewHistoryProducts);
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("filters", productsFilter.getFiltersString());
        model.addAttribute("categories", categoryService.findAll());
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

    @GetMapping("/details")
    public String showProductDetails(Model model, @RequestParam(name = "id") Long id,
                                     HttpServletResponse response,
                                     @CookieValue(value = "viewsHistory", required = false) String viewsHistory){
        Product product = productService.findById(id).get();
        model.addAttribute("product", product);
        if (viewsHistory==null){
            viewsHistory = String.valueOf(product.getId());
        } else {
            viewsHistory = viewsHistory + "z" + product.getId();
        }
        response.addCookie(new Cookie("viewsHistory", viewsHistory));
        return "productDetails";
    }
}
