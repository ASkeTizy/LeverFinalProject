package o.e.service;

import o.e.dto.CommentDTO;
import o.e.entity.Comment;
import o.e.entity.SellerInformationDTO;
import o.e.exception.ResourceNotFoundException;
import o.e.repository.CommentRepository;
import o.e.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentQueue queue;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, CommentQueue queue) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.queue = queue;
    }


    public Comment findByUserIdAndCommentId(Long userId, Long commentId) {
        return commentRepository.findByAuthorIdAndId(userId, commentId).orElseThrow(() ->new ResourceNotFoundException("Comment not found"));
    }

    public Comment createComment(Comment comment) {

        if (!userRepository.existsById(comment.getAuthorId())) {

            throw new ResourceNotFoundException("No such user "+comment.getAuthorId());
        }
        return commentRepository.save(comment);
    }

    public boolean addCommentToCheck(Long userId, CommentDTO commentDTO) {
        if (!userRepository.existsById(userId)) {
            return false;
        }
        queue.addComment(userId, commentDTO);
        return true;
    }

    public List<Comment> findAllByUserId(Long authorId) {
       List<Comment> comments = commentRepository.findAllByAuthorId(authorId);
        if( comments.isEmpty()){
            throw new ResourceNotFoundException("Not found comments by authorID" + authorId);
        }
        return comments;
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = findByUserIdAndCommentId(userId,commentId);
        commentRepository.delete(comment);
    }

    public Comment updateComment(Long userId, Long commentId, CommentDTO commentDTO) {
        Comment comment = findByUserIdAndCommentId(userId,commentId);
        comment.setRate(commentDTO.rate());
        comment.setMessage(commentDTO.message());
        return commentRepository.save(comment);
    }

    public double calculateSellerRating(Long userId) {
        List<Comment> comments = findAllByUserId(userId);
        return comments.stream().mapToDouble(Comment::getRate).average().orElse(0);
    }

    public List<SellerInformationDTO> setSellerRate(List<SellerInformationDTO> users) {

        return users.stream().peek(el -> el.setRate(calculateSellerRating(el.getId()))).toList();
    }

    public List<SellerInformationDTO> getTopSellers() {
        var users = userRepository.findUsersWithComments();

        return setSellerRate(users).stream()
                .sorted(Comparator.comparingDouble(SellerInformationDTO::getRate)
                        .reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}
