package com.oldandsea.pcb.domain.repository;

import com.oldandsea.pcb.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardBoardId(Long boardId);
}