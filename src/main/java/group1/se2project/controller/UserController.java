package group1.se2project.controller;

import group1.se2project.GlobalData.GlobalData;
import group1.se2project.model.MainCategory;
import group1.se2project.model.Product;
import group1.se2project.model.SubCategory;
import group1.se2project.repository.MainCategoryRepository;
import group1.se2project.repository.ProductRepository;
import group1.se2project.repository.SubCategoryRepository;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    MainCategoryRepository mainCategoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {


        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m:
                mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        model.addAttribute("ListCat", ListCat);
        List<Product> products = new ArrayList<>();
        MainCategory mainCategory = mainCategoryRepository.findMainCategoryByName("laptop");
        List<SubCategory> subCategoryList = subCategoryRepository.findByMainCategoryEquals(mainCategory);
        for (SubCategory subCat:
                subCategoryList) {
            products.addAll(productRepository.findBySubCategoryEquals(subCat));
        }
        model.addAttribute("products", products);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }
    @GetMapping(value="success")
    public String successPage(){
        return "success";
    }
    @GetMapping(value="comingsoon")
    public String comingSoon(){
        return "comingsoon";
    }

    @GetMapping(value = "/shop")
    public String getCategoryAndProduct(Model model){
        List<Product> products = productRepository.findAll();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m:
              mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        model.addAttribute("products", products);
        model.addAttribute("ListCat", ListCat);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }
    @GetMapping(value = "/shop/maincategory/{name}")
    public String filterByMaincategory(Model model, @PathVariable(value = "name") String name){
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m:
                mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        model.addAttribute("ListCat", ListCat);
        List<Product> products = new ArrayList<>();
        MainCategory mainCategory = mainCategoryRepository.findMainCategoryByName(name);
        List<SubCategory> subCategoryList = subCategoryRepository.findByMainCategoryEquals(mainCategory);
        for (SubCategory subCat:
             subCategoryList) {
            products.addAll(productRepository.findBySubCategoryEquals(subCat));
        }
        model.addAttribute("products", products);
        return "shop";
    }
    @GetMapping(value = "/shop/subcategory/{id}")
    public String filterBySubcategory(Model model, @PathVariable(value = "id") Long id ){
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m:
                mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        model.addAttribute("ListCat", ListCat);
        List<Product> products = productRepository.findBySubCategoryEquals(subCategoryRepository.findSubCategoryById(id));

        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping(value = "/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id) {
        model.addAttribute("categories", mainCategoryRepository.findAll());
        model.addAttribute("product", productRepository.getById(id));
        return "productDetail";
    }
}
