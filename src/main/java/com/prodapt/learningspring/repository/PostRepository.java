package com.prodapt.learningspring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.prodapt.learningspring.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	@Query("Select EXTRACT(EPOCH from p.createdAt) from Post p where p.id = :post_id")
	Long sortPostsByCreatedAt(@Param("post_id")Integer post_id);
	
	List<Post> findByCreatedAtBetween(Date startDate, Date endDate);

}