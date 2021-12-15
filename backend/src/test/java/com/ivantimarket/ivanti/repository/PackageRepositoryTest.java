package com.ivantimarket.ivanti.repository;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
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
    public void GetPackageById(){
        //version I saw it can be with normal constructor
//        version = new Version();
//        LocalDateTime localDateTime = LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay();
//        version.setDateAdded(localDateTime);
//        version.setUrl("urlrlrl");
//        version.setName("first");
//        version.setReadme("readme");
//        version.setSize(1234);
//        //system requirements
//        systemRequirements = new SystemRequirements("Intem core i5","4gb","RX 560");
//        //
//        packageService.addNewPackage(new NewPackageDTO(1,"Title",1,"Cool intro"),version,systemRequirements);
        packageService.getPackage(1);
        verify(packageRepository).findById(1);
    }
    @Test
    public void DeleteById(){
        packageService.deletePackage(1);
        verify(packageRepository).deleteById(1);
    }

}
