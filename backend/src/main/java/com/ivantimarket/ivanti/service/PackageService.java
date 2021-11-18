package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.repo.PackageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PackageService {
    private final PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public Package getPackage(int packageId) {
        return packageRepository.findById(packageId);
    }
    public Package addNewPackage(Package newPackage) {
        return packageRepository.save(newPackage);
    }
    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }
}
