package com.prodapt.learningspring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.repository.LikeCountRepository;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.service.SortingPosts;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private LikeCountRepository likeCountRepository;

    @Autowired
    private SortingPosts postService;

    @GetMapping("/forum")
    public String showForumPage(Model model, @RequestParam(value = "sortFilter", required = false) String sortFilter,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
@RequestParam(value = "minLikes", required = false) Integer minLikes) {
        List<Post> posts = (List<Post>) postRepository.findAll();

        if ("sortByLikes".equals(sortFilter)) {
            posts = postService.sortPostsByLikes(posts);
        } 
            else if ("sortByTimestamp".equals(sortFilter)) {
            posts = postService.sortPostsByTimestamp(posts);
        } 
            else if ("filterByDateRange".equals(sortFilter) && startDateStr != null && endDateStr != null) {
            try {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            	Date startDate = dateFormat.parse(startDateStr);
            	Date endDate = dateFormat.parse(endDateStr);
            	posts = postService.filterPostsByDateRange(posts, startDate, endDate);
            }
            catch (ParseException e) {
				e.printStackTrace();
			}
        }
        else if ("filterByMinLikes".equals(sortFilter) && minLikes != null) {
        posts = postService.filterPostsByMinLikes(posts, minLikes);
    }
        List<Integer> likeList = new ArrayList<>();
        for(Post post: posts) {
	        int numLikes = likeCountRepository.countByPostId(post.getId());
	        likeList.add(numLikes);
        }
        model.addAttribute("likeCount",likeList);
        model.addAttribute("posts", posts);
        return "SortedPosts";
    }
}
