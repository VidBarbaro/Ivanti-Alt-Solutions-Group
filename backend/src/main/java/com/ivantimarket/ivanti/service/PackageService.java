package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.dto._mapper.PackageMapper;
//import com.ivantimarket.ivanti.dto._mapper.UserMapper;
import com.ivantimarket.ivanti.dto._mapper.UserMapper;
import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.dto.packages.PackageOverviewDTO;
import com.ivantimarket.ivanti.exception.TitleExistsException;
import com.ivantimarket.ivanti.model.*;
import com.ivantimarket.ivanti.model.Package;
import com.ivantimarket.ivanti.repo.DownloadRepository;
import com.ivantimarket.ivanti.repo.PackageRepository;
import com.ivantimarket.ivanti.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PackageService {
    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;
    private final UserRepository userService;
    private final DownloadRepository downloadRepository;
    private final UserMapper userMapper;

    public List<PackageOverviewDTO> getAllPackages() {

        List<Package> packages = packageRepository.findAll();
        List<PackageOverviewDTO> dtos = new ArrayList<>();
        for(Package p : packages){
            PackageOverviewDTO dto = packageMapper.toPackageOverviewDTO(p);
            List<Version> versions = p.getVersions();
            Version latestVersion = versions.get(versions.size() - 1);
            dto.setLatestVersion(latestVersion);
            dtos.add(dto);
        }
        return dtos;
    }

    public Package getPackage(long packageId) {
        return packageRepository.findById(packageId);
    }


    public Package addNewPackage(NewPackageDTO newPackageDTO, Version version, SystemRequirements requirements) {

        log.info(newPackageDTO.toString());
        User user = userService.findById(newPackageDTO.getCreatorId());
        user.getUploaded_packages_id().add(newPackageDTO.getId());
        userService.save(user);
        Package newPackage = packageMapper.toPackage(newPackageDTO);
        newPackage.setCreator(userMapper.toUserDto(user));
        newPackage.getVersions().add(version);
        newPackage.setSystemRequirements(requirements);
        log.info("Id: {}, Title: {}, Creator: {}",String.valueOf(newPackage.getId()), newPackage.getTitle(), newPackage.getCreator());
       return packageRepository.save(newPackage);
    }


    public Package addVersion(long packageId, Version version){
        Package pckg = packageRepository.findById(packageId);
        pckg.getVersions().add(version);
        return packageRepository.save(pckg);
    }

    public Package addTestPackage(Package newPackage) throws TitleExistsException {
        this.testTitleUnique("", newPackage.getTitle());
        packageRepository.save(newPackage);
        return newPackage;
    }

    public List<Package> getPackagesUploadedByUser(long userId)
    {
        List<Package> packages = new ArrayList<>();
        for(Package p : packageRepository.findAll())
        {
            if((p.getCreator().getId()==userId))
            {
                packages.add(p);
            }
        }
        return packages;
    }
    public Package updatePackage(Long id, String title, String description, String intro, String processorType, String ram, String graphicsCard) throws TitleExistsException {
        String currentTitle = this.packageRepository.findById(id).getTitle();
        this.testTitleUnique(currentTitle, title);
        Package updatedPackage = this.getPackage(id);
        updatedPackage.setTitle(title);
        updatedPackage.setDescription(description);
        updatedPackage.setIntro(intro);
        updatedPackage.setSystemRequirements(new SystemRequirements(processorType, ram, graphicsCard));
        packageRepository.save(updatedPackage);
        return updatedPackage;
    }

    public List<PackageOverviewDTO> getFirst3Packages() {
        List<Package> packages = packageRepository.findAll();
        List<PackageOverviewDTO> dtos = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Package p = packages.get(i);
            PackageOverviewDTO dto = packageMapper.toPackageOverviewDTO(p);
            List<Version> versions = p.getVersions();
            Version latestVersion = versions.get(versions.size() - 1);
            dto.setLatestVersion(latestVersion);
            dtos.add(dto);
        }

        return dtos;
    }


    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }

    public boolean addPackageToFavouritePackages(long userId, long packageId){
        User user = userService.findById(userId);
        Package mPackage = getPackage(packageId);
        if(user != null && !user.getFavourite_packages_id().contains(packageId)){
            user.getFavourite_packages_id().add(packageId);
            userService.save(user);
            return true;
        }
        return false;
    }

    public Package isPackageAddedToFavourites(long userId, long packageId)
    {
        User user = this.userService.findById(userId);
        for(long id : user.getFavourite_packages_id())
        {
            if(id == packageId)
            {
                return packageRepository.findById(packageId);
            }
        }
        return null;
    }

    public boolean removePackageToFavouritePackages(long userId, long packageId){
        User user = userService.findById(userId);
        if(user != null){
            if(user.getFavourite_packages_id().remove(packageId)) {
                userService.save(user);
                return true;
            }
        }
        return false;
    }

    public PackageOverviewDTO findPackageByTitle(String title){
        for (PackageOverviewDTO p:
                this.getAllPackages()) {
            if(p.getTitle().equals(title)){
                return p;
            }
        }
        return null;
    }

    public List<Package> getFavouritePackagesOfUser(long userId)
    {
        List<Package> favouritePackages = new ArrayList<>();
        User user = this.userService.findById(userId);
        for(Long id : user.getFavourite_packages_id())
        {
            Package p = this.packageRepository.findById(id);
            favouritePackages.add(p);
        }
        return favouritePackages;
    }

    public List<Package> getDownloadedPackagesByUser(long userId)
    {
        List<Package> downloaded = new ArrayList<>();
        for(Download d : this.downloadRepository.findAll())
        {
            if(d.getUserId() == userId)
            {
                Package p = this.packageRepository.findById(d.getPackageId());
                downloaded.add(p);
            }
        }
        return downloaded;
    }

    private PackageOverviewDTO testTitleUnique(String currentTitle, String title) throws TitleExistsException {
        PackageOverviewDTO packageByTitle = findPackageByTitle(title);

        if(!currentTitle.equals("")) {
            PackageOverviewDTO currentPackage = findPackageByTitle(currentTitle);
            if(packageByTitle != null && currentPackage.getId() != packageByTitle.getId()) {
                throw new TitleExistsException("Title already exists. Add an unique one.");
            }
            return currentPackage;
        } else {
            if(packageByTitle != null) {
                throw new TitleExistsException("Title already exists. Add an unique one.");
            }
            return null;
        }
    }

}
