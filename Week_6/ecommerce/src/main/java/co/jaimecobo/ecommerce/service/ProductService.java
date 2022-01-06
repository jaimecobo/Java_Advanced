package co.jaimecobo.ecommerce.service;

import co.jaimecobo.ecommerce.model.Product;
import co.jaimecobo.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product findById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<String> findDistinctCategories() {
        return productRepository.findDistinctCategories();
    }

    public List<String> findDistinctBrands() {
        return productRepository.findDistinctBrands();
    }

    public List<Product> findByBrandAndOrCategory(String brand, String category) {
        if(category == null && brand == null){
            return productRepository.findAll();
        } else if (category == null){
            return productRepository.findByBrand(brand);
        } else if(brand == null){
            return productRepository.findByCategory(category);
        } else { // this "else" is not mandatory but is there to make the logic of the code more detailed
            return productRepository.findByBrandAndCategory(brand, category);
        }
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(long id){
        productRepository.deleteById(id);
    }
}
