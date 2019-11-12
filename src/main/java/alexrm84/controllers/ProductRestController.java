package alexrm84.controllers;

import alexrm84.entities.Product;
import alexrm84.errorHandlers.ProductException;
import alexrm84.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> findAll(){
        return productService.findAll();
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
