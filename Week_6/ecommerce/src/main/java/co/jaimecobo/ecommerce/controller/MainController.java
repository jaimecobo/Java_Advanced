package co.jaimecobo.ecommerce.controller;

import co.jaimecobo.ecommerce.model.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Data
@Controller
@ControllerAdvice
public class MainController {

    //Here we are performing dependency injection
    @Autowired
    ProductService productService;

    // Whenever we return a string in a vanilla controller Class,
    // what happens is that we return a reference to template
    @GetMapping("/")
    public String main(){
        return "main";
    }

    @ModelAttribute("/products")
    public List<Product>products(){
        return productService.findAll();
    }

    @ModelAttribute("/cagtegories")
    public List<String> categories(){
        return productService.findDistinctCategories();
    }

    @ModelAttribute("/brands")
    public List<String> brands(){
        return productService.findDistinctBrands();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String category,
                         @RequestParam(required = false) String brand,
                         Model model){

        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered);
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
