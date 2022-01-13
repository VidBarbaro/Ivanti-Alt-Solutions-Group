package com.ivantimarket.ivanti.repository;

import com.ivantimarket.ivanti.model.Download;
import com.ivantimarket.ivanti.repo.DownloadRepository;
import com.ivantimarket.ivanti.service.DownloadService;
import com.ivantimarket.ivanti.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class DownloadRepositoryTest {


    @Mock
    DownloadRepository downloadRepository;

    private AutoCloseable autoCloseable;

    @InjectMocks
    DownloadService downloadService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        downloadService = new DownloadService(downloadRepository);
    }

    @Test
    public void isNotEmpty() {
        //arrange
        Download download = new Download(1L,1L,1L,"Version");
        //act
        Mockito.when(downloadRepository.save(download)).thenReturn(download);
        //assert
        Assert.assertEquals(download,downloadRepository.save(download));
    }
}
