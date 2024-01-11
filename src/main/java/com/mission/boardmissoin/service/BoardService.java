package com.mission.boardmissoin.service;

import com.mission.boardmissoin.entity.Board;
import com.mission.boardmissoin.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //게시글 작성
    public void write(Board board) {
        boardRepository.save(board);

    }

    // 게시글 리스트 처리
    public Page<Board> List(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public Board view(Integer id) {
        return boardRepository.findById(id).get();
    }

    // 게시글 삭제

    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

}