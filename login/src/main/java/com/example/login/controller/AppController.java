package com.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.login.repository.UserRepository;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
	
	@GetMapping("/login")
		public String login() {
    return "login";
  	}
	
	@GetMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}
	
	@GetMapping("/restricted")
	public String restrictedPage() {
		return "restricted";
	}
	
	@GetMapping("/error")//not doing error handling so just return them to home
	public String redirectErrorToHome() {
		return "index";
	}
	
	@GetMapping("/*")//redirect errant urls to index
	public String redirectToHome() {
		return "index";
	}
}
