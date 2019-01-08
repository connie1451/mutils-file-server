package cn.minsin.file.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.tools.DateUtil;
import cn.minsin.file.model.FileSaveResult;

/**
 * 提供给 mutils-file-server的服务端文件帮助类
 * 
 * @author minsin
 *
 */
public class FileUtil {

	/**
	 * 保存单个文件
	 * 
	 * @param file 预保存文件
	 * @return
	 */
	public static FileSaveResult saveFile(MultipartFile file, String disk) throws MutilsErrorException {
		try {
			String fileName = file.getOriginalFilename().replace(",", "");
			String gName = fileName;
			String savePath = DateUtil.date2String(new Date(), "yyyyMMdd/");
			String path = disk + savePath;
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
				byte[] bytes = file.getBytes();
				byte[] header = new byte[28];
				for (int i = 0; i < 28; i++) {
					header[i] = bytes[i];
				}
				//判断文件头
				FileType type = getType(header);
				if(type==null) {
					out.close();
					throw new MutilsErrorException("非法类型，无法上传");
				}
				out.write(bytes);
				out.flush();
				out.close();
				FileSaveResult fileSaveResult = new FileSaveResult();
				fileSaveResult.setPreview(type.isCanPreview());
				fileSaveResult.setUrl(savePath + gName);
				return fileSaveResult;
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

	/** 判断文件类型 */
	public static FileType getType(byte[] src) throws IOException {
		// 获取文件头
		String fileHead = bytesToHex(src);
		if (fileHead != null && fileHead.length() > 0) {
			fileHead = fileHead.toUpperCase();
			FileType[] fileTypes = FileType.values();

			for (FileType type : fileTypes) {
				if (fileHead.startsWith(type.getValue())) {
					return type;
				}
			}
		}
		return null;
	}

	/** 将字节数组转换成16进制字符串 */
	public static String bytesToHex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
