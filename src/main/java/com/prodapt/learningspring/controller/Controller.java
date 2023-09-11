package com.prodapt.learningspring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.service.SortingPosts;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private PostRepository postRepository;

    @Autowired
    private SortingPosts postService;

    @GetMapping("/forum")
    public String showForumPage(Model model, @RequestParam(value = "sortFilter", required = false) String sortFilter,
            @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "endDate", required = false) Date endDate) {
        List<Post> posts = (List<Post>) postRepository.findAll();

        if ("sortByLikes".equals(sortFilter)) {
            posts = postService.sortPostsByLikes(posts);
        } 
            else if ("sortByTimestamp".equals(sortFilter)) {
            posts = postService.sortPostsByTimestamp(posts);
        } 
            else if ("filterByDateRange".equals(sortFilter) && startDate != null && endDate != null) {
            posts = postService.filterPostsByDateRange(posts, startDate, endDate);
        }

        model.addAttribute("posts", posts);
        return "SortedPosts";
    }
}
