package com.ssafy.living_spot.board.service;

import com.ssafy.living_spot.board.dto.BoardDto;
import com.ssafy.living_spot.board.dto.InsertDto;
import com.ssafy.living_spot.board.repository.mybatis.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }

    @Override
    public List<BoardDto> searchAllArticles() {
        return boardMapper.searchAllArticles();
    }

    @Override
    public BoardDto searchOneArticle(int articleNo) {
        return boardMapper.searchOneArticle(articleNo);
    }

    @Override
    public void deleteArticle(int articleNo) {
        boardMapper.deleteArticle(articleNo);
    }

//    @Override
//    public void updateArticle(int articleNo, String title, String content, String type) {
//        boardMapper.updateArticle(articleNo,title,content,type);
//    }

    @Override
    public void updateArticle(BoardDto boardDto) {
        boardMapper.updateArticle(boardDto);
    }

    @Override
    public void updateHit(int articleNo) {
        boardMapper.updateHit(articleNo);
    }

    @Override
    public void writeArticle(InsertDto insertDto) {
        boardMapper.writeArticle(insertDto);
    }


}
