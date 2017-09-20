package karvytin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import karvytin.model.User;
import karvytin.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> list=(List<GrantedAuthority>) auth.getAuthorities();
        if (list.get(0).getAuthority().equalsIgnoreCase("Admin")) {
            return "redirect:/admin/home";
        }
        else if (list.get(0).getAuthority().equalsIgnoreCase("State")) {
            return "redirect:/state/home";
        }
        else if (list.get(0).getAuthority().equalsIgnoreCase("Branch")) {
            return "redirect:/branch/home";
        }
        
        return "redirect:/login";
    }
	
	@RequestMapping(value="/error", method={RequestMethod.GET})
	public ModelAndView getError()
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("error");
		return modelAndView;
	}
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/contact", method = RequestMethod.GET)
	public ModelAndView contact(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contact");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName()+" "+user.getLastName());
		User newuser = new User();
		modelAndView.addObject("user", newuser);
		modelAndView.setViewName("admin/registration");
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user1 = userService.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", "Welcome " + user1.getName() + " "+user1.getLastName() );
			modelAndView.setViewName("admin/registration");
		} else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user1 = userService.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", "Welcome " + user1.getName() + " "+user1.getLastName());
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("admin/registration");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName()+". ");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
}
