package org.launchcode.closettracker.controllers;


import org.launchcode.closettracker.models.Item;
import org.launchcode.closettracker.models.User;
import org.launchcode.closettracker.models.dto.UserDTO;
import org.launchcode.closettracker.models.dto.LoginFormDTO;
import org.launchcode.closettracker.repositories.ItemRepository;
import org.launchcode.closettracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();

    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    //localhost:8080
    @GetMapping("")
    public String index (Model model){
        model.addAttribute("title", "Welcome to Closet Tracker");
        model.addAttribute(new LoginFormDTO());
        return "index";
    }

    @PostMapping("")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors,
                                   HttpServletRequest request, Model model){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Welcome to Closet Tracker");
            model.addAttribute("errorMsg", "Entry not valid!");
            return "index";
        }
        User theUser = userRepository.findByEmail(loginFormDTO.getEmail());

        if (theUser == null) {
            errors.rejectValue("email", "user.invalid", "Not a valid user");
            model.addAttribute("title", "Welcome to Closet Tracker");
            return "index";
        }
        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Welcome to Closet Tracker");
            return "index";
        }
        setUserInSession(request.getSession(), theUser);

        List<Item> result =(List<Item>) itemRepository.findAll();
        List<Item> result1  = result.stream().filter(c -> (c.getUser_Id() !=null && c.getUser_Id() == theUser.getId())).collect(Collectors.toList());
        if(result1.size() > 0) {
            model.addAttribute("items", result1);
        }else
        {
            model.addAttribute("itemSearch", "Your closet is empty! Please add items..");
        }
        return "items/closet";
    }

    //localhost:8080/create
    @GetMapping("create")
    public String displayCreateAccountForm(Model model) {
        model.addAttribute(new UserDTO());
        model.addAttribute("title", "Create User Account");
        return "create";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:";
    }


}
