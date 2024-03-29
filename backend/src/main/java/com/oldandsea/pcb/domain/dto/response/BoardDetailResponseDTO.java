package com.oldandsea.pcb.domain.dto.response;

import lombok.*;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDetailResponseDTO {
    private Long boardId;
    private String title;
    private String content;
    private Long createdAt;
    private List<String> boardTagList;
    private String nickname;
    @Builder
    public BoardDetailResponseDTO(Long boardId, String title, String content, Long createdAt, List<String> boardTagList, String nickname) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.boardTagList = boardTagList;
        this.nickname = nickname;
    }
}
