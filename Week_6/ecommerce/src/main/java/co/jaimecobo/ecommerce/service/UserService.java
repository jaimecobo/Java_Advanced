package co.jaimecobo.ecommerce.service;

import co.jaimecobo.ecommerce.model.Product;
import co.jaimecobo.ecommerce.model.User;
import co.jaimecobo.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveNew(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveExisting(User user){
        userRepository.save(user);
    }

    public User getLogInUser(){
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    //This method allows us to get a user set his cart to a new value
    //and save him back in to the database
    public void updateCart(Map<Product, Integer> cart){
        User user = getLogInUser();
        user.setCart(cart);
        saveExisting(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("Username not found.");
        return user;
    }

}
