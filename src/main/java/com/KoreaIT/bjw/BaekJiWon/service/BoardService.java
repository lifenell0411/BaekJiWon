package com.KoreaIT.bjw.BaekJiWon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw.BaekJiWon.repository.BoardRepository;
import com.KoreaIT.bjw.BaekJiWon.vo.Board;
@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	
	// 특정 게시물 정보를 가져옴, 게시글번호를 인자로 넘겨줌
	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}

}