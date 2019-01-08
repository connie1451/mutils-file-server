package cn.minsin.file.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.minsin.file.model.FileModel;

public interface FileRepository extends MongoRepository<FileModel,String> {

}
