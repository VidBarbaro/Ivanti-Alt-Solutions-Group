package com.ivantimarket.ivanti.repository;

import com.ivantimarket.ivanti.exception.TitleExistsException;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.model.SystemRequirements;
import com.ivantimarket.ivanti.model.Version;
import com.ivantimarket.ivanti.repo.PackageRepository;
import com.ivantimarket.ivanti.service.PackageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.verify;

public class PackageRepositoryTest {
    @Mock
    private PackageRepository packageRepository;
    private AutoCloseable autoCloseable;
    Version version;
    SystemRequirements systemRequirements;
    @InjectMocks
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    public void GetAllPackages(){
        packageService.getAllPackages();
        verify(packageRepository).findAll();
    }
    @Test
    public void GetPackageById(){;
        packageService.getPackage(1);
        verify(packageRepository).findById(1);
    }
    @Test
    public void DeleteById(){
        packageService.deletePackage(1);
        verify(packageRepository).deleteById(1);
    }
    @Test
    public void AddPackage() throws TitleExistsException {
        Package mPackage = new Package();
        packageService.addTestPackage(mPackage);
        verify(packageRepository).save(mPackage);
    }
//    @Test
//    public void UpdatePackage() throws TitleExistsException {
//        packageService.updatePackage(1L,"sss","ppp","intel i5", "4GB", "RX 570");
//        verify(packageRepository).save(packageRepository.findById(1L));
//    }

}
