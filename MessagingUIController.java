package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * Handles requests for the message page
 */
@Controller
public class MessagingUIController
{
	/*
	 * Displays the messages page when a request is received
	 * @param message Requests messages between users from the database
	 * @return ModelAndView object containing the messages between users
	 * as well as the messages page to be displayed
	 */
	@RequestMapping("messages")
	public ModelAndView messages(@RequestParam("messages") String message)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("messages",message);
		mv.setViewName("message");
		return mv;
	}
}

