package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*
 * Handles login page requests as well as sign up and login requests
 */
@RestController
@RequestMapping("/api/users")
public class LoginController {
	
    @Autowired
    private AuthenticationManager auth;

    /*
	 * Displays the login page when a request is received
	 * @param myName Username of the user
	 * @param myPass Password of the user
	 * @return A ModelAndView object containing the username, password, and login page
	 */
	@RequestMapping("login")
	public ModelAndView loginPage(@RequestParam("usrname") String myName, @RequestParam("password") String myPass)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("username", myName);
		mv.addObject("password", myPass);
		mv.setViewName("login");
		return mv;
	}
    
	//refer to the AuthenticationManager methods for documentation
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return auth.signup(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return auth.login(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return auth.getUserById(id);
    }
}
