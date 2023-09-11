package com.prodapt.learningspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.prodapt.learningspring.entity.Comment;


public interface CommentCRUDRepository extends CrudRepository<Comment, Long>{
    @Query(value = "select * from comments c where post_id = ?1", nativeQuery = true)
    List<Comment> findAllByPostId(Integer postId);
    
    @Query(value = "SELECT * FROM comments c WHERE c.post_id = :post_id ORDER BY c.createdAt DESC", nativeQuery = true)
    public Long findTop1ByPostIdOrderByDateDesc(@RequestParam("post_id") Integer post_id);
//    
//    public Long countByPostId(Integer post_id);

	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt DESC, c.id DESC")
	Comment findTopByPostIdOrderByCreatedAtDesc(@Param("postId") Integer postId);


    
}

