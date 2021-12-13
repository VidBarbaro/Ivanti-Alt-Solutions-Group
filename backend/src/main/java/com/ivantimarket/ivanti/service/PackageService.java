package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.dto._mapper.PackageMapper;
//import com.ivantimarket.ivanti.dto._mapper.UserMapper;
import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.PackageRepository;
import com.ivantimarket.ivanti.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PackageService {
    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;
    private final UserRepository userService;
//    private final UserMapper userMapper;
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public Package getPackage(int packageId) {
        return packageRepository.findById(packageId);
    }

    public Package addNewPackage(NewPackageDTO newPackageDTO) {

        User user = userService.findById(newPackageDTO.getCreator().getId());
        user.getDownloaded_packages_id().add(newPackageDTO.getId());
        log.info("new package: "+ newPackageDTO.toString());
        userService.save(user);
        Package newPackage = packageMapper.toPackage(newPackageDTO);
        newPackage.getVersions().add(newPackageDTO.getVersion());
        log.info("Id: {}, Title: {}, Creator: {}",String.valueOf(newPackage.getId()), newPackage.getTitle(), newPackage.getCreator());
        return packageRepository.save(newPackage);
    }

    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }
}
