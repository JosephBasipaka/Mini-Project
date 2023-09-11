package com.prodapt.learningspring.service;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.Post;

@Service
@Controller
public interface IPopularPosts {
		public List<Post> sortPosts(List<Post> post); 
}
