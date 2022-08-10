package com.example.usermanagment.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.usermanagment.dao.AddressDao;
import com.example.usermanagment.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.usermanagment.dao.EmailDao;
import com.example.usermanagment.dao.UserDao;
import com.example.usermanagment.model.User;
import com.example.usermanagment.utils.EmailDetails;

@Controller
public class HomeController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private AddressDao addressDao1;

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	EmailDao emailDao;
	
	@GetMapping("/signin")
	public String login() {
		return "login.html";
	}
	
	@GetMapping("/signup")
	public ModelAndView register() {
		ModelAndView model=new ModelAndView();
		model.addObject(new User());
		model.setViewName("register.html");
		return model;
	}
	
	
	 @PostMapping("/registration")
	    public ModelAndView registerUserAccount(@Valid @ModelAttribute("user")  User user, HttpServletRequest request,
												BindingResult result) {
		 //int count1=Integer.parseInt(request1.getParameter("count"));

		 //model1.addAttribute("userA",addr);
		 System.out.println("username=="+user.getUsername());

		 if(user.getUsername().isEmpty()) {
			 result.rejectValue("username", null, "Please enter username");
		 }

			if(user.getName().isEmpty()) {
	            result.rejectValue("name", null, "Please enter name");
	        }
			
			if(user.getEmail().isEmpty()) {
	            result.rejectValue("email", null, "Please enter email");
	        }
			
			if(user.getMobileNo().isEmpty()) {
	            result.rejectValue("mobileNo", null, "Please enter Mobile Number");
	        }
			
//			if(user.getDob()==null ) {
//	            result.rejectValue("dob", null, "Please enter Date of birth");
//	        }
//
			if(user.getGender()==null || user.getGender().isEmpty()) {
	            result.rejectValue("gender", null, "Please enter gender");
	        }
			
			if(user.getPassword().isEmpty()) {
	            result.rejectValue("password", null, "Please enter password");
	        }
			
			if(user.getConfirmPassword().isEmpty()) {
	            result.rejectValue("confirmPassword", null, "Please enter confirm password");
	        }
		 
	        if(!user.getPassword().equals(user.getConfirmPassword())) {
	            result.rejectValue("confirmPassword", null, "Password not matched");
	        }
//		 if(address1.getLine1().isEmpty()) {
//			 result.rejectValue("Line1", null, "Password not matched");
//		 }

	        
	        ModelAndView model=new ModelAndView();
	        model.setViewName("register");
	        if (result.hasErrors()) {
	            return model;
	        }


//		 HttpSession session = request1.getSession();
//		 ModelAndView model=new ModelAndView();
//		 model.setViewName("address");
//		        if (result.hasErrors()) {
//		        	model.addObject("addressList", addressDao.getUserAddress(session.getAttribute("username").toString()));
//		            return model;
//		        }
	        user.setRole("NORMAL");
	    	 user.setPassword(passwordEncoder.encode(user.getPassword()));
	        User user1= userDao.saveUser(user);
int count=Integer.parseInt(request.getParameter("count"));
		 for(int i=1;i<=count;i++) {
			 Address address1=new Address();
			 address1.setUser(user);
			 address1.setLine1(request.getParameter("line1"+i));
			 address1.setLine2(request.getParameter("line2"+i));
			 address1.setState(request.getParameter("state"+i));
			 address1.setCity(request.getParameter("city"+i));
			 address1.setPincode(request.getParameter("pincode"+i));
			 addressDao1.saveAddress(address1);

		 }
//		 model.addObject("addressList1", addressDao1.getUserAddress(session.getAttribute("username").toString()));
//		 model.addObject("success", true);
	    	 
	    	
//		 Address address= new Address();
//		 address.setLine1("dd");
//		 address.setLine2("sda");
//		 address.setState("jhdb");
//		 address.setCity("sd");;
//		 address.setPincode("sdf");
//		 address.setId(50);
//user.setEmail("Shubha@gamil.com");
//user.setGender("Male");
//user.setPassword("hb");
//user.setPassword("hb");
//user.setUsername("shbhan");
//user.setUsername("shbh");
//user.setMobileNo("31122465");



//model1.addAttribute("user1",user1);
		 System.out.println(user1);

	    	 
	    	 model.setViewName("register.html");
	    	 model.addObject("success", true);
	    	 model.addObject(new User());
	    	 return model;
	        	 
	    }
	 
    @GetMapping("/")
    public String home(HttpServletRequest request) {
    	
    	StringBuilder menu=new StringBuilder();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	if(authentication.getAuthorities().stream()
    	          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
    		menu.append( "<li class='nav-item'><a class='nav-link' href='/users'>Show users</a></li>");
    	}
    	
    	
    	menu.append( "<li class='nav-item'><a class='nav-link' href='/users/profile'>Profile</a></li>");
    	//menu.append( "<li class='nav-item'><a class='nav-link' href='/users/address/'>View Address</a></li>");
    	menu.append( "<li class='nav-item'><a class='nav-link' href='/users/changePassword'>Change Password</a></li>");
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("menu",menu);
    	session.setAttribute("username", authentication.getName());
       return "home";
    }
    @GetMapping("/forgetPassword")
    public String forgetPassword() {

        return "forgetPassword";
    }
	
    @PostMapping("/forgetPassword")
    public String forgetPasswordAction(HttpServletRequest request,Model model) {
    	String username=request.getParameter("username");
    	User user=userDao.findUserByUsername(username);
    	if(user!=null) {
    	String charString="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    	StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(charString.charAt(random.nextInt(charString
                    .length())));
        }
        EmailDetails emailDetails=new EmailDetails();
        emailDetails.setSubject("Forget Password");
        emailDetails.setMsgBody(sb.toString());
        emailDetails.setRecipient(user.getEmail());
        boolean flag=emailDao.sendSimpleMail(emailDetails);
        if(flag) {
    	model.addAttribute("success", true);
    	user.setPassword(passwordEncoder.encode(sb.toString()));
    	userDao.saveUser(user);
        }
        else {
        	model.addAttribute("error", "something went wrong");
        }
    	}
    	else {
    		model.addAttribute("error", "username does not exist");
    	}
    	return "forgetPassword";
    }
}
