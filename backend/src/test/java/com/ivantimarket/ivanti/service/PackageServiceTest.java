package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.model.SystemRequirements;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.model.Version;
import com.ivantimarket.ivanti.repo.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PackageServiceTest {
    @MockBean
    private PackageRepository packageRepository;
    Version version;
    SystemRequirements systemRequirements;
    @InjectMocks
    private PackageService packageService;
    @BeforeEach
    void setUp() {
//        version = new Version();
//        LocalDateTime localDateTime = LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay();
//        version.setDateAdded(localDateTime);
//        version.setUrl("urlrlrl");
//        version.setName("first");
//        version.setReadme("readme");
//        version.setSize(1234);
//        //system requirements
//        systemRequirements = new SystemRequirements("Intem core i5","4gb","RX 560");
//        packageService.addNewPackage(new NewPackageDTO(1,"Title",1,"Cool intro"),version,systemRequirements);
    }
    @Test
    @Disabled
    void getPackageById(){
        Package aPackage = null;
        Mockito.when(packageRepository.findById(1)).thenReturn(aPackage);

        Package result = packageService.getPackage(1);

        assertEquals(result,aPackage);
    }


}
