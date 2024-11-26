package com.ssafy.living_spot.board.repository.mybatis;

import com.ssafy.living_spot.board.dto.BoardDto;
import com.ssafy.living_spot.board.dto.InsertDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardDto> searchAllArticles();
    BoardDto searchOneArticle(@Param("articleNo") int articleNo);
    void deleteArticle(@Param("articleNo") int articleNo);
//    void updateArticle(@Param("articleNo") int articleNo, @Param("title") String title,@Param("content") String content,@Param("type") String type);
    void updateArticle(BoardDto boardDto);
    void updateHit(@Param("articleNo") int articleNo);
    void writeArticle(InsertDto insertDto);

}
