package com.ivantimarket.ivanti.dto._mapper;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.model.Package;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    Package toPackage(NewPackageDTO packageDTO);
    //one of these functions has to be commented or uncommented every time
    NewPackageDTO toPackageDTO(Package p);
//    Package fakeMethod(NewPackageDTO fake);
}
