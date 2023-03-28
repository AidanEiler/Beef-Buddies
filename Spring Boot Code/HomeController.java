package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * Handles requests for the home page
 */
@Controller
public class HomeController
{
	/*
	 * Displays the home page when a request is received
	 * @param myName Takes the name of a user for display on the home page
	 * @return A ModelAndView object containing the user's name and the home page to be displayed
	 */
	@RequestMapping("home")
	public ModelAndView home(@RequestParam("name") String myName)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("name",myName);
		mv.setViewName("home");
		return mv;
	}
}
