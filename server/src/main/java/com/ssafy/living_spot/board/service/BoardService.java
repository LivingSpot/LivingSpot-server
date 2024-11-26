package com.ssafy.living_spot.board.service;

import com.ssafy.living_spot.board.dto.BoardDto;
import com.ssafy.living_spot.board.dto.InsertDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.math.BigInteger;
import java.util.List;

public interface BoardService {
    List<BoardDto> searchAllArticles();
    BoardDto searchOneArticle(@Param("articleNo") int articleNo);
    void deleteArticle(@Param("articleNo") int articleNo);
    void updateArticle(BoardDto boardDto);
//    void updateArticle(@Param("articleNo") int articleNo, @Param("title") String title,@Param("content") String content,@Param("type") String type);
    void updateHit(@Param("articleNo") int articleNo);
    void writeArticle(InsertDto insertDto);
}
