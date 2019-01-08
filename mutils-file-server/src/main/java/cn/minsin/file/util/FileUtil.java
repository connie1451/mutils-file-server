package cn.minsin.file.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.tools.DateUtil;

/**
 * 提供给 mutils-file-server的服务端文件帮助类
 * 
 * @author minsin
 *
 */
public class FileUtil {
	
	
	public static void getFile(String path, HttpServletResponse resp) throws MutilsErrorException {
		try {
			// 读取路径下面的文件
			File file = new File(path);
			String fileName = new String(file.getName().getBytes("UTF-8"), "ISO8859-1");
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/x-msdownload; charset=utf-8");
			resp.setHeader("content-disposition", "attachment;filename=" + fileName);
			// 读取指定路径下面的文件
			InputStream in = new FileInputStream(file);
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
			throw new MutilsErrorException(e, "文件读取失败");
		}

	}

	/**
	 * 保存单个文件
	 * 
	 * @param file 预保存文件
	 * @return
	 */
	public static String saveFile(MultipartFile file,String disk) throws MutilsErrorException {
		try {
			String fileName = file.getOriginalFilename().replace(",", "");
			String gName = fileName;
			String savePath = DateUtil.date2String(new Date(), "yyyyMMdd/");
			String path = disk+ savePath;
			// 定义上传路径
			checkPath(path);
			int count = 0;
			while (true) {
				String fileUrl = path + gName;
				boolean exists = new File(fileUrl).exists();
				if (exists) {
					int index = fileName.lastIndexOf(".");
					String extension = "";
					if (index > 0) {
						extension = fileName.substring(index, fileName.length());
					}
					count++;
					gName = fileName.replace(extension, "") + "-副本(" + count + ")" + extension;
					continue;
				}
				// 写入文件
				OutputStream out = new FileOutputStream(fileUrl);
				out.write(file.getBytes());
				out.flush();
				out.close();
				return savePath + gName;
			}
		} catch (Exception e) {
			throw new MutilsErrorException(e, "保存文件失败");
		}
	}

	/**
	 * path中不能出现\
	 * 
	 * @param path 2018年9月8日
	 */
	public static void checkPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
