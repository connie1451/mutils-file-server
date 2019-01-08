package cn.minsin.file.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.web.Result;
import cn.minsin.file.model.FileModel;
import cn.minsin.file.service.FileService;
import cn.minsin.file.util.FileUtil;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public Result upload(@RequestParam(name = "file") MultipartFile file) {
		try {
			String saveFile = fileService.saveFile(file);
			return Result.builderSuccess().data("url", saveFile);
		} catch (MutilsErrorException e) {
			e.printStackTrace();
			return Result.builderFail();
		}

	}

	@RequestMapping("/{id}")
	public void file(@PathVariable("id") String id, HttpServletResponse response) {
		try {
			FileModel findById = fileService.findById(id);
			String fullPath = findById.getDiskPath();
			FileUtil.getFile(fullPath, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
