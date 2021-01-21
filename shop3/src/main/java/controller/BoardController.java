package controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.BoardException;
import logic.Board;
import logic.ShopService;

@Controller
@RequestMapping("board")
public class BoardController {
	@Autowired
	private ShopService service;
	@RequestMapping("list")
	public ModelAndView list(Integer pageNum, String searchtype, String searchcontent) {
		ModelAndView mav = new ModelAndView();
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if(searchtype == null || searchcontent == null || searchtype.trim().equals("") || searchcontent.trim().equals("")) {
			searchtype = null;
			searchcontent = null;
		}
		int limit = 10;
		int listcount = service.boardcount(searchtype,searchcontent);
		List<Board> boardlist = service.boardlist(pageNum, limit,searchtype, searchcontent);
		int maxpage = (int)((double)listcount/limit + 0.95);
		int startpage = (int)((pageNum/10.0 + 0.9) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if(endpage> maxpage) {
			endpage = maxpage;
		}
		int boardno = listcount - (pageNum - 1) * limit;
		mav.addObject("pageNum",pageNum);
		mav.addObject("maxpage",maxpage);
		mav.addObject("startpage",startpage);
		mav.addObject("endpage",endpage);
		mav.addObject("listcount",listcount);
		mav.addObject("boardlist",boardlist);
		mav.addObject("boardno",boardno);
		mav.addObject("today",new SimpleDateFormat("yyyyMMdd").format(new Date()));
		return mav;
	}
	@RequestMapping("detail")
	public ModelAndView detail(Integer num) {
		ModelAndView mav = new ModelAndView();
//		service.readcntAdd(num);
//		Board board = service.boardDetail(num);
		Board board = service.getBoard(num,true);
		mav.addObject("board",board);
		return mav;
	}
	
	@GetMapping("*")
	public ModelAndView getBoard(Integer num) {
		ModelAndView mav = new ModelAndView();
		Board board = null;
		if(num == null) {
			board = new Board();
		}else {
			board = service.getBoard(num,false);
		}
		mav.addObject("board",board);
		return mav;
	}
	
//	@GetMapping("write")
//	public ModelAndView writeForm() {
//		ModelAndView mav = new ModelAndView();
//		Board board = new Board();
//		mav.addObject("board",board);
//		return mav;
//	}
	
	@PostMapping("write")
	/**
	 * DB에 board 객체 저장
	 * 최대 num 값 + 1 등록되는 게시물의 num 설정
	 * 등록 성공 : list.shop
	 * 등록 실패 : BoardException 예외 발생. write.shop 등록 화면
	 * @param board
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	public ModelAndView write(@Valid Board board, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
		
		int curNum = service.boardMaxNum();
		int curgrp = service.boardMaxGrp();
		board.setNum(++curNum);
		board.setGrp(++curgrp);
		
		if(service.boardWrite(board, request)>0) {
			mav.setViewName("redirect:/board/list.shop");
		}else {
			throw new BoardException("게시물 등록 실패","../board/write.shop");
		}
		return mav;
	}
	
	@PostMapping("delete")
	public ModelAndView delete(@Valid Board board, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		Board curBoard = service.getBoard(board.getNum(), false);
		if(bindingResult.hasFieldErrors("pass") || !curBoard.getPass().equals(board.getPass()) ) {
//			mav.getModel().putAll(bindingResult.getModel());
//			return mav;
			bindingResult.reject("error.login.password");
			return mav;
		}
		
		if(!curBoard.getPass().equals(board.getPass())) {
			throw new BoardException("비밀번호가 다릅니다","../board/delete.shop?num="+board.getNum());
		}else {
			if(service.boardDelete(board)) {
				mav.setViewName("redirect:/board/list.shop");
			}else {
				throw new BoardException("게시물 삭제 실패","../board/delete.shop?num="+board.getNum());
			}
		}
		return mav;
	}
	
	@PostMapping("update")
	public ModelAndView update(@Valid Board board, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
		Board curBoard = service.getBoard(board.getNum(), false);
		if(!curBoard.getPass().equals(board.getPass())) {
			throw new BoardException("비밀번호가 틀립니다","../board/update.shop?num="+board.getNum());
		}else {
			if(service.boardUpdate(board,request)>0) {
				mav.setViewName("redirect:/board/detail.shop?num="+board.getNum());
			}else {
				throw new BoardException("게시물 수정 실패","../board/update.shop?num="+board.getNum());
			}
		}
		return mav;
	}
	
	@PostMapping("reply")
	public ModelAndView reply(@Valid Board board, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()) {
			Board dbBoard = service.getBoard(board.getNum(),false);
			Map<String, Object> map = bindingResult.getModel();
			Board b = (Board)map.get("board");
			b.setSubject(dbBoard.getSubject());
//			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
		Board curBoard = service.getBoard(board.getNum(), false);
		board.setNum(service.boardMaxNum() + 1);
		board.setGrplevel(curBoard.getGrplevel() + 1);
		board.setGrpstep(service.boardMaxGrpStep(board) + 1);
		System.out.println(board);
		if(service.boardWrite(board, request)>0) {
			mav.setViewName("redirect:/board/list.shop");
		}else {
			throw new BoardException("게시물 등록 실패","../board/write.shop");
		}
		return mav;
	}
	
	@RequestMapping("imgupload")
	public String imgupload(MultipartFile upload, String CKEditorFuncNum, HttpServletRequest request, Model model) {
		String path = request.getServletContext().getRealPath("/") + "board/imgfile/";
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		if(!upload.isEmpty()) {
			File file = new File(path, upload.getOriginalFilename());
			try {
				upload.transferTo(file);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		String fileName = request.getContextPath() + "/board/imgfile/" + upload.getOriginalFilename();
		model.addAttribute("fileName",fileName);
		model.addAttribute("CKEditorFuncNum",CKEditorFuncNum);
		return "ckedit";
	}
	
}
