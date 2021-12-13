package com.ivantimarket.ivanti.controllers;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.dto.user.NewUserDTO;
import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.model.Version;
import com.ivantimarket.ivanti.service.PackageService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import com.ivantimarket.ivanti.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/packages")
public class PackageController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private PackageService packageService;

    //So we can take UserDTO and assign a package
    private UserService userService;
    private final SequenceGeneratorService sequenceGeneratorService;

    public PackageController(PackageService packageService, SequenceGeneratorService sequenceGeneratorService,
                             UserService userService) {
        this.packageService = packageService;
        this.userService = userService;
        this.sequenceGeneratorService = sequenceGeneratorService;

        //if we dont have user with username mike create him if not created here we have to rerun the program
        //should be done in user and package service but no time for that now
        //anyway we should not have hardcoded things like that later
        if (userService.getUserDTO("mike") == null) {
            NewUserDTO userDTOMike = new NewUserDTO(-1, "Mike", "mike", "mike12345", "mike@gmail.com", "ROLE_CONTENT_CREATOR");
            userDTOMike.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
            userService.saveUser(userDTOMike);
        }
        //get user
        UserDTO userDTO = userService.getUserDTO("mike");
        log.info("userdto " + userDTO.toString());
        NewPackageDTO newPackageDTO = new NewPackageDTO(-1, "axios", userDTO,
                "The intro", new Version(
                "first version",
                "Read me before installing me",
                "http://localsomething"
        ));
        NewPackageDTO newPackageDTO1 = new NewPackageDTO(-1, "react router", userDTO,
                "Bad intro", new Version(
                "first version",
                "Read me before installing me",
                "http://localsomething"
        ));
        //changed the constructor for debugging purpose without success
        NewPackageDTO newPackageDTO2 = new NewPackageDTO(-1,"Cors config",userDTO,
                "Cors has no intro", new Version(
                "first version",
                "Read me before installing me",
                "http://localsomething"
        ));
        NewPackageDTO newPackageDTO3 = new NewPackageDTO(-1, "Newton", userDTO,
                "The intro", new Version(
                "first version",
                "Read me before installing me",
                "http://localsomething"
        ));
        newPackageDTO.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));
        newPackageDTO1.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));
        newPackageDTO2.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));
        newPackageDTO3.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));
        packageService.addNewPackage(newPackageDTO);
        packageService.addNewPackage(newPackageDTO1);
        packageService.addNewPackage(newPackageDTO2);
        packageService.addNewPackage(newPackageDTO3);

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Package> getAllPackages() {
        LOG.info("Getting all packages.");
        return packageService.getAllPackages();
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.GET)
    public Package getPackage(@PathVariable int packageId) {
        LOG.info("Getting package with ID: {}.", packageId);
        return packageService.getPackage(packageId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Package addNewPackage(@RequestBody NewPackageDTO newPackage) {
        LOG.info("Saving package.");
        newPackage.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));
        return packageService.addNewPackage(newPackage);
    }

    @RequestMapping(value = "/delete/{packageId}", method = RequestMethod.DELETE)
    public void deletePackage(@PathVariable int packageId) {
        LOG.info("Deleting package.");
        packageService.deletePackage(packageId);
    }
}