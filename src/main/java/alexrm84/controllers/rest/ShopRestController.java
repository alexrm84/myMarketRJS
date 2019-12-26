package alexrm84.controllers.rest;

import alexrm84.DTOs.CategoryDTO;
import alexrm84.DTOs.ProductDTO;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("")
    public List<Product> findAll(){
        List<Product> products = productService.findAll();
//        List<ProductDTO> productDTOS = products.stream().map(p -> productService.convertProductToDTO(p)).collect(Collectors.toList());
        return products;//productDTOS;
    }

    @GetMapping("/page")
    public Page<Product> getPage(HttpServletRequest request,
                                 @RequestParam(value = "currentPage", required = false) Integer currentPage){
        System.out.println(request.getParameter("catId"));
        ProductsFilter productsFilter = new ProductsFilter(request);
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page<Product> page = productService.findAllByPagingAndFiltering(productsFilter.getSpecification(), PageRequest.of(currentPage - 1, 10, Sort.Direction.ASC, "id"));
        return page;
    }

    @GetMapping("/cat")
    public List<Category> findAllCat(){
        List<Category> categories = categoryService.findAll();
//        List<CategoryDTO> categoryDTOS = categories.stream().map(c -> categoryService.convertCategoryToDTO(c)).collect(Collectors.toList());
        return categories;//categoryDTOS;
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
