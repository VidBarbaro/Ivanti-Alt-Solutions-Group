package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.model.Download;
import com.ivantimarket.ivanti.repo.DownloadRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class DownloadService {


    private final DownloadRepository downloadRepository;

    public Download saveDownload(Download download) {
        return downloadRepository.save(download);
    }

    public Download getById(long id) {
        return downloadRepository.findById(id);
    }

    public List<Download> getByUserId(long userId) {
        List<Download> downloads = new ArrayList<>();
        List<Download> allDownloads = downloadRepository.findAll();
        for (Download x : allDownloads) {
            if (x.getUserId() == userId) {
                downloads.add(x);
            }
        }
        return downloads;
    }

    public List<Download> getByPackageId(long packageId) {
        List<Download> downloads = new ArrayList<>();
        List<Download> allDownloads = downloadRepository.findAll();
        for (Download x : allDownloads) {
            if (x.getPackageId() == packageId) {
                downloads.add(x);
            }
        }
        return downloads;
    }

    public boolean deleteDownload(long id) {
        Download download = downloadRepository.findById(id);
        if (download!=null) {
            downloadRepository.delete(download);
            return true;
        }
        return false;
    }

}
