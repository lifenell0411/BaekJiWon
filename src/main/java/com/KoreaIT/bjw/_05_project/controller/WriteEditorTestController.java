package com.KoreaIT.bjw._05_project.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WriteEditorTestController {
	
	private String path = "c:/imageSaveStorage/";

	@RequestMapping(value = "/writeTest.do", method = RequestMethod.GET)
	public ModelAndView writeTestGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("toast_UI_writer3");
		return mv;
	}
	
	@RequestMapping(value = "/writeTest.do", method = RequestMethod.POST)
	public ModelAndView writeTestPost(@RequestParam("image") MultipartFile multi, HttpServletRequest request, HttpServletResponse response) {
		
		String url = null;
		ModelAndView mv = new ModelAndView();
		
		try {
			String uploadPath = path;
			String originFilename = multi.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
			long size = multi.getSize();
			FileNameModel fileNameModel = new FileNameModel();
			String saveFileName = fileNameModel.GenSaveFileName(extName);
			
			if(!multi.isEmpty()) {
				File file = new File(uploadPath, saveFileName);
				multi.transferTo(file);
				
				mv.addObject("filename", saveFileName);
				mv.addObject("uploadPath", file.getAbsolutePath());
				mv.addObject("url", uploadPath+saveFileName);
				System.out.println("url : " + uploadPath+saveFileName);
				
				mv.setViewName("image_Url_Json");
			} else {
				mv.setViewName("toast_UI_writer3");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[Error] " + e.getMessage());
		}
		return mv;
	}
}