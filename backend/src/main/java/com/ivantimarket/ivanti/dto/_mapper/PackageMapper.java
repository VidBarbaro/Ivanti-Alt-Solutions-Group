package com.ivantimarket.ivanti.dto._mapper;

import com.ivantimarket.ivanti.dto.packages.NewPackageDTO;
import com.ivantimarket.ivanti.model.Package;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    Package toPackage(NewPackageDTO packageDTO);
    //Package fakeMethod(NewPackageDTO fake);
}
