package cn.minsin.file.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.tools.StringUtil;
import cn.minsin.file.config.MutilsFileConfig;
import cn.minsin.file.model.FileModel;
import cn.minsin.file.repository.FileRepository;
import cn.minsin.file.util.FileUtil;

@Service
public class FileService {

	@Autowired
	private MutilsFileConfig mutilsFileConfig;

	@Autowired
	private FileRepository fileRepository;

	public String saveFile(MultipartFile file) throws MutilsErrorException {
		try {
			String property = System.getProperty("sun.desktop");
			Field field = MutilsFileConfig.class.getDeclaredField(property);
			field.setAccessible(true);
			Object object = field.get(mutilsFileConfig);// 文件保存地址
			String serverUrl = mutilsFileConfig.getServerUrl();
			String saveFile = FileUtil.saveFile(file, object.toString());
			String string = serverUrl + saveFile;
			String uuidForLength = StringUtil.getUUIDForLength(32).toLowerCase();
			FileModel f = new FileModel();
			f.setAddtime(new Date());
			f.setFullPath(string);
			f.setDiskPath(object + saveFile);
			f.setOs(property);
			f.set_id(uuidForLength);
			fileRepository.insert(f);
			return serverUrl+uuidForLength;
		} catch (Exception e) {
			throw new MutilsErrorException(e, "文件保存失败");
		}

	}
	
	//TODO 后期考虑采用缓存
	public FileModel findById(String id) {
		Optional<FileModel> findById = fileRepository.findById(id);
		boolean present = findById.isPresent();
		if (present) {
			return findById.get();
		}
		return null;
	}

}
