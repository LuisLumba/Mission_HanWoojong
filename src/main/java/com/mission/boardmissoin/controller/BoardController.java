package com.mission.boardmissoin.controller;

import com.mission.boardmissoin.entity.Board;
import com.mission.boardmissoin.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //    게시글 작성폼
    @GetMapping("/board/write")
    public String boardWriteFrom() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model) {
        boardService.write(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";

    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> list = boardService.List(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage; /*= Math.min(nowPage + 5, list.getTotalPages()); */
        if (nowPage <= 5) {
            endPage = Math.min(10, list.getTotalPages());
        } else {
            endPage = Math.min(nowPage + 5, list.getTotalPages());
        }


        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";

    }

    @GetMapping("/board/view") // localhost:8080/board/view?id= 불러오고싶은 번호
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.view(id));
        return "boardview";

    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String modify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.view(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable("id") Integer id, Board board) {
        Board boardTemp = boardService.view(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }

}
