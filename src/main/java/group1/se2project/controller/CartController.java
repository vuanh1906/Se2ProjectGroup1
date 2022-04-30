package group1.se2project.controller;

import group1.se2project.GlobalData.GlobalData;
import group1.se2project.model.Product;
import group1.se2project.repository.ProductRepository;
import group1.se2project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;

@Controller
public class CartController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable(value = "id") Long id,Model model ) {
        GlobalData.cart.add(productRepository.getById(id));
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("cartCount", GlobalData.cart.size());
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        model.addAttribute("total", formatter.format(amount));
        return "cart";
    }

    @GetMapping(value = "/cart")
    public String cart(Model model) {
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        model.addAttribute("total", formatter.format(amount));
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @RequestMapping(value = "/cart/removeItem/{index}")
    public String deleteFromCart(@PathVariable int index ){
        GlobalData.cart.remove(index);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/checkout")
    public String checkout (Model model) {
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        model.addAttribute("total", formatter.format(amount));
        return  "checkout";
    }
}
