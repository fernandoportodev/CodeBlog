package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeBlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class CodeBlogController {

    @Autowired
    private CodeBlogService codeBlogService;

    @GetMapping
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = codeBlogService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getPostDetails(@PathVariable long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = codeBlogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @GetMapping("/newpost")
    public String getPostForm() {
        return "postForm";
    }

    @PostMapping("/newpost")
    public String savePost(@Valid Post post, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Check that the required fields have been filled in");
            return "redirect:/posts/newpost";
        }
        post.setDate(LocalDate.now());
        codeBlogService.save(post);
        return "redirect:/posts";
    }
}
