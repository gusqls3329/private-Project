package com.example.beenproject.board.model;

import com.example.beenproject.common.exception.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Range;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {
    /*
    type :  1:제목 , 2: 제목+내용, 3:닉네임
    search : 검색어
    sort : 0:최신순 ,1:좋아요순, 2:조회수순
     */
    @JsonIgnore
    private long loginIuser;

    private Integer type;

    private String search;

    private int sort;

    @JsonIgnore
    private String status;

    @Range(min = 1, message = ErrorMessage.ILLEGAL_RANGE_EX_MESSAGE)
    private int page;

    @JsonIgnore
    private int startIdx;

    @JsonIgnore
    private int rowCount = 12;

    public void setPage(int page) {
        this.startIdx = (page-1) * this.rowCount;
    }

}

