package com.ivantimarket.ivanti.dto._mapper;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.model.Package;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    Package toPackage(NewPackageDTO packageDTO);
    NewPackageDTO toPackageDTO(Package p);

    //This function has to be commented or uncommented before running the app for the 1st time
    Package fakeMethod(NewPackageDTO fake);
}
