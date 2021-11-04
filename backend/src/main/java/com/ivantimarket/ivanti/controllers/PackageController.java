package com.ivantimarket.ivanti.controllers;

import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.repo.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/packages")
public class PackageController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final PackageRepository packageRepository;

    public PackageController(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Package> getAllPackages() {
        LOG.info("Getting all packages.");
        return packageRepository.findAll();
    }
    @RequestMapping(value = "/{packageId}", method = RequestMethod.GET)
    public Package getPackage(@PathVariable int packageId) {
        LOG.info("Getting package with ID: {}.", packageId);
        return packageRepository.findById(packageId);
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Package addNewPackage(@RequestBody Package newPackage) {
        LOG.info("Saving package.");
        return packageRepository.save(newPackage);
    }
    @RequestMapping(value = "/delete/{packageId}", method = RequestMethod.DELETE)
    public void deletePackage(int id) {
        LOG.info("Deleting package.");
        packageRepository.deleteById(id);
    }
}
