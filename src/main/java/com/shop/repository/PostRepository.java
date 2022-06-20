package com.shop.repository;

import com.shop.entity.Member;
import com.shop.entity.Post;
import com.shop.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(Member member);
}

