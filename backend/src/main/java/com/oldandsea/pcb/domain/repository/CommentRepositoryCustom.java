package com.oldandsea.pcb.domain.repository;

import com.oldandsea.pcb.domain.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {
    public void updateAfter(Comment comment);
}
