package com.ivantimarket.ivanti.repo;

import com.ivantimarket.ivanti.model.Download;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Dictionary;

@Repository
public interface DownloadRepository extends MongoRepository<Download, Integer> {
    Download findById(long id);
}
