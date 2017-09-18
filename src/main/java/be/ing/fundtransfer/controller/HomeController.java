package be.ing.fundtransfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public String index() {
        System.out.println("Testing....");
        return "redirect:/test/index.html";
    }
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String login(HttpServletResponse response, @RequestParam("username") String userName) {
        System.out.println("Hi....."+userName);
        System.out.println("Login....");
        response.addHeader("STATUS", "SUCCESS");
        return "/success.html";
    }

}
