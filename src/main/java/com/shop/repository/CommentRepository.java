package com.shop.repository;

import com.shop.entity.Member;
import com.shop.entity.Post;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Post, Long>{
 List<Post> findByPost(Post post);

 List<Member> findAllByMember(Post post);





}
