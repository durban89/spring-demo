package com.gowhich.springdemo;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

@Controller
public class SocialController {
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public SocialController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/connect/facebook", method = RequestMethod.GET)
    public ModelAndView connectFacebook(Model model) {
        ModelAndView mv = new ModelAndView("connect/connectFacebook");
        mv.addObject("email", "ddd@walkerfree.com");
        return mv;
    }

    @RequestMapping(value = "/connected/facebook", method = RequestMethod.GET)
    public String connectedFacebook(Model model) {
        Connection<Facebook> connection = this.connectionRepository.findPrimaryConnection(Facebook.class);

        if (connection == null) {
            return "redirect:/connect/facebook";
        }

        List<Reference> friends = facebook.friendOperations().getFriends();

        String[] fields = { "id", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);

        System.out.println(friends.size());
        model.addAttribute("friends", friends);
        model.addAttribute("friendsNum", friends.size());
        model.addAttribute("email", userProfile.getEmail());
        return "connect/connectedFacebook";
    }

}
