package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.IvantiApplication;
import com.ivantimarket.ivanti.model.Download;
import com.ivantimarket.ivanti.repo.DownloadRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@EnableMongoRepositories
@SpringBootTest(classes = {DownloadService.class, IvantiApplication.class, DownloadRepository.class})
public class DownloadServiceTest {

    @MockBean
    DownloadRepository downloadRepository;

    @InjectMocks
    DownloadService downloadService;

    @Test
    public void saveDownloadTest() {
        //arrange
        Download download = new Download(1L,1L,1L,"Version");
        //act
        Mockito.when(downloadRepository.save(any(Download.class))).thenReturn(download);
        Download saved = downloadRepository.save(download);
        //assert
        Assert.assertEquals(download,saved);
    }

    @Test
    @Disabled
    public void getDownloadsByUserIdTest() {
        //arrange
        Download download1 = new Download(1L,1L,1L,"Version");
        Download download2 = new Download(2L,1L,2L,"Version");
        List<Download> downloads = new ArrayList<>(Arrays.asList(download1,download2));
        //act
        Mockito.when(downloadRepository.findAll()).thenReturn(downloads);
        List<Download> found = downloadService.getByUserId(1L);
        //assert
        Assert.assertEquals(downloads,found);
    }
}
