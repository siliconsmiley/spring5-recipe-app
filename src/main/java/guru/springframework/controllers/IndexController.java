package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage() {
        System.out.println("text to trigger Spring Developer Tools rebuild. This should automatically rebuild.");
        return "index";
    }
}
