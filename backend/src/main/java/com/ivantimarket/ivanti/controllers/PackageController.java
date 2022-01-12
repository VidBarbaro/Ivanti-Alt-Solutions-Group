package com.ivantimarket.ivanti.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivantimarket.ivanti.dto.FileData;
import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.dto.packages.PackageOverviewDTO;
import com.ivantimarket.ivanti.dto.user.NewUserDTO;
import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.exception.TitleExistsException;
import com.ivantimarket.ivanti.model.*;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.service.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/packages")
public class PackageController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final PackageService packageService;
    private final FileService fileService;

    //So we can take UserDTO and assign a package
    private final SequenceGeneratorService sequenceGeneratorService;

    public PackageController(PackageService packageService, SequenceGeneratorService sequenceGeneratorService,
                             FileService fileService) {
        this.packageService = packageService;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.fileService = fileService;

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<PackageOverviewDTO> getAllPackages() {
        LOG.info("Getting all packages.");
        return packageService.getAllPackages();
    }

    @RequestMapping(value = "/get/{packageId}", method = RequestMethod.GET)
    public Package getPackage(@PathVariable int packageId) {
        LOG.info("Getting package with ID: {}.", packageId);
        return packageService.getPackage(packageId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Package addNewPackage(@RequestPart String rawPackage,@RequestPart String rawVersion, @RequestPart MultipartFile file, @RequestPart String rawRequirements) {
        NewPackageDTO newPackage = getNewPackageDTO(rawPackage);
        Version version = getVersion(rawVersion);
        SystemRequirements requirements = getSystemRequirements(rawRequirements);

        newPackage.setId(sequenceGeneratorService.generateSequence(Package.SEQUENCE_NAMEE));

        String fileName = newPackage.getTitle() + "-" + version.getName() + ".zip";
        FileData fileData =pathToFileData(fileService.save(file, fileName));
        String url = fileData.getUrl();
        version.setUrl(url);
        version.setDateAdded(LocalDateTime.now());
        version.setSize(fileData.getSize());

        return packageService.addNewPackage(newPackage, version, requirements);
    }
    @PostMapping("/add-new-package")
    public Package addNewPackage(@RequestBody Package newPackage) throws TitleExistsException {
        return this.packageService.addTestPackage(newPackage);
    }

    @PostMapping("/update-package")
    public ResponseEntity<Package> updatePackage(@RequestParam("id") Long id,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("intro") String intro,
                                                 @RequestParam("processorType") String processorType,
                                                 @RequestParam("ram") String ram,
                                                 @RequestParam("graphicsCard") String graphicsCard) throws TitleExistsException {
        //add exception for unique title
        Package updatedPackage = packageService.updatePackage(id, title, description, intro, processorType, ram, graphicsCard);

        return new ResponseEntity<>(updatedPackage, OK);
    }


//    @PostMapping(value = "/add-version/{packageId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Package addVersion(@PathVariable long packageId, @RequestPart String versionString, @RequestPart MultipartFile file){
//
//        Version version = getVersion(versionString);
//
//        String packageName = packageService.getPackage(packageId).getTitle();
//        String fileName = packageName + "-" + version.getName() + ".zip";
//        FileData fileData = pathToFileData(fileService.save(file, fileName));
//        String url = fileData.getUrl();
//        version.setUrl(url);
//        version.setSize(fileData.getSize());
//        version.setDateAdded(LocalDateTime.now());
//        return packageService.addVersion(packageId, version);
//    }

    @RequestMapping(value = "/first3packages", method = RequestMethod.GET)
    public List<PackageOverviewDTO> getFirst3Packages() {
        LOG.info("Getting first 3 packages.");
        return packageService.getFirst3Packages();
    }

    private SystemRequirements getSystemRequirements(String util){
        SystemRequirements utilJson = new SystemRequirements();

        try{
            ObjectMapper mapper = new ObjectMapper();
            utilJson = mapper.readValue(util, SystemRequirements.class);
        } catch(IOException err){
            System.out.printf("Error ", err.toString());
        }
        return utilJson;
    }

    private NewPackageDTO getNewPackageDTO(String util){
        NewPackageDTO utilJson = new NewPackageDTO();

        try{
            ObjectMapper mapper = new ObjectMapper();
            utilJson = mapper.readValue(util, NewPackageDTO.class);
        } catch(IOException err){
            System.out.printf("Error ", err.toString());
        }
        return utilJson;
    }

    private Version getVersion(String util){
        Version utilJson = new Version();

        try{
            ObjectMapper mapper = new ObjectMapper();
            utilJson = mapper.readValue(util, Version.class);
        } catch(IOException err){
            System.out.printf("Error ", err.toString());
        }
        return utilJson;
    }

    @RequestMapping(value = "/delete/{packageId}", method = RequestMethod.DELETE)
    public void deletePackage(@PathVariable int packageId) {
        LOG.info("Deleting package.");
        packageService.deletePackage(packageId);
    }

    @GetMapping("/uploaded/{userId}")
    public List<Package> getUploadedPackagesOfUser(@PathVariable long userId)
    {
        return packageService.getPackagesUploadedByUser(userId);
    }

    private FileData pathToFileData(Path path) {
        FileData fileData = new FileData();
        String filename = path.getFileName()
                .toString();
        fileData.setFilename(filename);
        fileData.setUrl(MvcUriComponentsBuilder.fromMethodName(PackageController.class, "getFile", filename)
                .build()
                .toString());
        try {
            fileData.setSize(Files.size(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e.getMessage());
        }

        return fileData;
    }

    @PostMapping("/favourites/add")
    public Package addPackageToFavourites(@RequestParam String userId, @RequestParam String packageId)
    {
        Package mPackage = packageService.getPackage(Integer.parseInt(packageId));
        LOG.info("{}",mPackage.getId());
        if(mPackage!=null){
            packageService.addPackageToFavouritePackages(Integer.parseInt(userId),Integer.parseInt(packageId));
            return mPackage;

        }
        return null;
    }

    @GetMapping("/favourites/check")
    public Package isPackageAddedToFavourites(@RequestParam Long userId, @RequestParam Long packageId)
    {
        return packageService.isPackageAddedToFavourites(userId, packageId);
    }

    @DeleteMapping("/favourites/remove")
    public boolean removePackageToFavourites(@RequestParam long userId, @RequestParam long packageId)
    {
        Package mPackage = packageService.getPackage(packageId);
        if(mPackage!=null){
            if(packageService.removePackageToFavouritePackages(userId,packageId)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/add-version/{packageId}")
    public Package addVersionToPackage(@PathVariable long packageId, @RequestBody Version newVersion)
    {
        return packageService.addVersion(packageId, newVersion);
    }

    @GetMapping("/favouriteOfUser/{userId}")
    public List<Package> getFavouritePackagesOfUser(@PathVariable long userId)
    {
        return packageService.getFavouritePackagesOfUser(userId);
    }

    @GetMapping("/downloaded/{userId}")
    public List<Package> getDownloadedPackagesOfUser(@PathVariable long userId)
    {
        return packageService.getDownloadedPackagesByUser(userId);
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@RequestParam String filename) {
        Resource file = fileService.load(filename);
        //here we may call add to downloaded packages
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}