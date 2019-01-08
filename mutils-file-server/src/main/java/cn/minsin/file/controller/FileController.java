package cn.minsin.file.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
	public void file(@PathVariable("id") String id, HttpServletResponse resp) {
		try {

			InputStream in= this.getClass().getClassLoader().getResourceAsStream("404.png");
			String fileName ="404.png";
			boolean preview =true;
			// 读取指定路径下面的文件
			try {
				FileModel findById = fileService.findById(id);
				String fullPath = findById.getDiskPath();
				// 读取路径下面的文件
				File file = new File(fullPath);
				 preview = findById.isPreview();
				 in = new FileInputStream(file);
				 fileName = new String(file.getName().getBytes("UTF-8"), "ISO8859-1");
			}catch (Exception e) {}
			if(!preview) {
				resp.setCharacterEncoding("utf-8");
				resp.setContentType("application/x-msdownload; charset=utf-8");
				resp.setHeader("content-disposition", "attachment;filename=" + fileName);
			}
			OutputStream outputStream = new BufferedOutputStream(resp.getOutputStream());
			// 创建存放文件内容的数组
			byte[] buff = new byte[1024];
			// 所读取的内容使用n来接收
			int n;
			// 当没有读取完时,继续读取,循环
			while ((n = in.read(buff)) != -1) {
				// 将字节数组的数据全部写入到输出流中
				outputStream.write(buff, 0, n);
			}
			// 强制将缓存区的数据进行输出
			outputStream.flush();
			// 关流
			outputStream.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
