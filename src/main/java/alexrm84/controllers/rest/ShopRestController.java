package alexrm84.controllers.rest;

import alexrm84.entities.Category;
import alexrm84.entities.Product;
import alexrm84.errorHandlers.ProductException;
import alexrm84.services.CategoryService;
import alexrm84.services.ProductService;
import alexrm84.utils.ProductsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopRestController {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/page")
    public Page<Product> getPage(HttpServletRequest request,
                                 @RequestParam(value = "currentPage", required = false) Integer currentPage){
        ProductsFilter productsFilter = new ProductsFilter(request);
        System.out.println("currPage="+currentPage);
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page<Product> page = productService.findAllByPagingAndFiltering(productsFilter.getSpecification(), PageRequest.of(currentPage - 1, 10, Sort.Direction.ASC, "id"));
        return page;
    }

    @GetMapping("/cat")
    public List<Category> findAllCat(){
        List<Category> categories = categoryService.findAll();
        return categories;
    }

















    @GetMapping("/{id}")
    public Product findOne(@PathVariable(name = "id") Long id){
        return productService.findById(id).orElseThrow(()-> new ProductException("Product with id: " + id + " not found"));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product){
        if (product.getId()!=null){
            throw new ProductException("Product must not contain id");
        }
        return productService.save(product);
    }

    @PutMapping("")
    public Product updateProduct(@RequestBody Product newProduct){
        return productService.findById(newProduct.getId())
                .map(product -> { return productService.save(newProduct);})
                .orElseThrow(()-> new ProductException("Product with id: " + newProduct.getId() + " not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }
}
