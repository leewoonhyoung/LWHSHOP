package com.shop.service;


import com.shop.dto.CommentsDto;
import com.shop.entity.Member;
import com.shop.entity.NotificationEmail;
import com.shop.entity.Post;
import com.shop.exception.PostNotFoundException;
import com.shop.exception.SpringRedditException;
import com.shop.repository.CommentRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


//comment service 추가해보기
@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, Member member) {
        mailService.sendMail(new NotificationEmail(member.getUsername() + " Commented on your post", member.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String name) {
       Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        return commentRepository.findAllByMember(member)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }
        return false;
    }
}
