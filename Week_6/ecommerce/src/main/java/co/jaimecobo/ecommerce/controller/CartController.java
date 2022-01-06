package co.jaimecobo.ecommerce.controller;

import co.jaimecobo.ecommerce.model.Product;
import co.jaimecobo.ecommerce.model.User;
import co.jaimecobo.ecommerce.service.ProductService;
import co.jaimecobo.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
public class CartController {

    //Note that we were autowiring two different dependencies in this Class
    //while this will work effectively, it is not considered best practice when you hae multiple dependencies
//    @Autowired
    ProductService productService;

//    @Autowired
    UserService userService;

    //Instead of using @Autowired when having multiple dependencies
    // we should employ a constructor-based dependency injection (like shown bellow)
    //Spring will automatically find a candidate for the dependencies and inject them
    public CartController(ProductService productService, UserService userService){
        this.productService = productService;
        this.userService = userService;
    }

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        return userService.getLogInUser();
    }

    @ModelAttribute("cart")
    public Map<Product, Integer> cart(){
        User user = loggedInUser();
        if(user == null) return null;
        System.out.println("Getting cart");
        return user.getCart();
    }

    @ModelAttribute("list")
    public List<Double> list(){
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String showCart(){
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam long id){
        Product p = productService.findById(id);
        setQuantity(p, cart().getOrDefault(p, 0) + 1);
        return "cart";
    }
    @PatchMapping("/cart")
    public String updateQuantities(@RequestParam long[] id, @RequestParam int[] quantity){
        for (int i = 0; i < id.length; i++){
            Product p = productService.findById(id[i]);
            setQuantity(p, quantity[i]);
        }
        return "cart";
    }

    @DeleteMapping("/cart")
    public String removeFromCart(@RequestParam long id){
        Product p = productService.findById(id);
        setQuantity(p, 0);
        return "cart";
    }

    private void setQuantity(Product p, int quantity){
        if(quantity >0) {
            cart().put(p, quantity);
        } else {
            cart().remove(p);
        }
        userService.updateCart(cart());
    }


}
