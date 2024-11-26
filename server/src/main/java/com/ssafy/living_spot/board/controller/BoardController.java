package com.ssafy.living_spot.board.controller;

import com.ssafy.living_spot.board.dto.BoardDto;
import com.ssafy.living_spot.board.dto.InsertDto;
import com.ssafy.living_spot.board.service.BoardService;
import com.ssafy.living_spot.common.util.SecurityUtil;
import com.ssafy.living_spot.member.dto.request.MemberIdParam;
import com.ssafy.living_spot.member.dto.response.MemberInfo;
import com.ssafy.living_spot.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @Autowired
    public BoardController(BoardService boardService, MemberService memberService){
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> searchAllArticles(){
        List<BoardDto> searchedAllList =  boardService.searchAllArticles();
        return ResponseEntity.status(HttpStatus.OK).body(searchedAllList);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto> searchOneArticle(@RequestParam(name="articleNo") int articleNo){
        boardService.updateHit(articleNo);
        BoardDto searchedArticle =  boardService.searchOneArticle(articleNo);
        return ResponseEntity.status(HttpStatus.OK).body(searchedArticle);
    }

    @PostMapping("/write")
    public ResponseEntity<Void> writeArticle(@RequestParam(name="title") String title, @RequestParam(name="content") String content, @RequestParam(name="type") String type){
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        System.out.println("memberId"+memberId);

        InsertDto insertDto = new InsertDto();
        insertDto.setId(memberId);
        insertDto.setTitle(title);
        insertDto.setContent(content);
        insertDto.setType(type);
        boardService.writeArticle(insertDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteArticle(@RequestParam(name="articleNo") int articleNo){
        boardService.deleteArticle(articleNo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateArticle(@RequestParam(name="articleNo") int articleNo, @RequestParam(name="title") String title, @RequestParam(name="content") String content, @RequestParam(name="type") String type){

        BoardDto boardDto = boardService.searchOneArticle(articleNo);
        boardDto.setTitle(title);
        boardDto.setContent(content);
        boardDto.setType(type);
        boardService.updateArticle(boardDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/change")
    public ResponseEntity<String> changeToName(@RequestParam(name="id") Long id){
        String memberName = memberService.getMemberByJpa(new MemberIdParam(id)).getName();
        System.out.println(memberName);
        return ResponseEntity.status(HttpStatus.OK).body(memberName);
    }
}