package com.prodapt.learningspring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import jakarta.servlet.http.HttpServletRequest;

@org.springframework.stereotype.Controller
public class SortController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private LikeCountRepository likeCountRepository;

    @Autowired
    private SortingPosts postService;
	
	private String currentSortOrder = "asc"; 

    private List<Post> cachedPosts = null;
	@GetMapping("/forum")
	public String showForumPage(Model model, @RequestParam(value = "sortFilter", required = false) String sortFilter,
			@RequestParam(value = "startDate", required = false) String startDateStr,
			@RequestParam(value = "endDate", required = false) String endDateStr,
			@RequestParam(value = "minLikes", required = false) Integer minLikes,HttpServletRequest request) {
		
	boolean refreshCache = "refreshClicked".equals(request.getParameter("refresh"));
    if (cachedPosts == null || refreshCache) {
        cachedPosts = (List<Post>) postRepository.findAll();
    }

    if ("filterByDateRange".equals(sortFilter) && startDateStr != null && endDateStr != null) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            cachedPosts = postService.filterPostsByDateRange(cachedPosts, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    if ("filterByMinLikes".equals(sortFilter) && minLikes != null) {
        cachedPosts = postService.filterPostsByMinLikes(cachedPosts, minLikes);
    }

//    if ("sortByLikes".equals(sortFilter)) {
//        cachedPosts = postService.sortPostsByLikes(cachedPosts);
//    } else if ("sortByTimestamp".equals(sortFilter)) {
//        cachedPosts = postService.sortPostsByTimestamp(cachedPosts);
//    }
	if ("sortByLikes".equals(sortFilter)) {
            toggleSortingOrder(); // Toggle the sorting order
            cachedPosts = postService.sortPostsByLikes(cachedPosts, currentSortOrder);
        } else if ("sortByTimestamp".equals(sortFilter)) {
            toggleSortingOrder(); // Toggle the sorting order
            cachedPosts = postService.sortPostsByTimestamp(cachedPosts, currentSortOrder);
        }
    List<Integer> likeList = new ArrayList<>();
    for (Post post : cachedPosts) {
        int numLikes = likeCountRepository.countByPostId(post.getId());
        likeList.add(numLikes);
    }

    model.addAttribute("likeCount", likeList);
    model.addAttribute("posts", cachedPosts);
    return "SortedPosts";
}
private void toggleSortingOrder() {
       currentSortOrder = "asc".equals(currentSortOrder) ? "desc" : "asc";
    }

}
