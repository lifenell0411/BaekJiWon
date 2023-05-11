package com.KoreaIT.bjw._05_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw._05_project.repository.BoardRepository;
import com.KoreaIT.bjw._05_project.repository.DesignRepository;
import com.KoreaIT.bjw._05_project.vo.Board;
@Service
public class DesignService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private DesignRepository designRepository;
	public DesignService(BoardRepository boardRepository) {
		this.designRepository = designRepository;
	}

	public Board getBoardById(int boardId) {
		return designRepository.getBoardById(boardId);
	}

}