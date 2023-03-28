package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * Handles requests for the profile page
 */
@Controller
public class ProfileController
{
	/*
	 * Gets and displays the profile page when a request comes through
	 * @param id User id that queries the database for the user
	 * @return ModelAndView object that contains the id of the user and the profile page
	 */
	@RequestMapping("profile")
	public ModelAndView profile(@RequestParam("id") long id)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("id",id);
		mv.setViewName("profile");
		return mv;
	}
}
