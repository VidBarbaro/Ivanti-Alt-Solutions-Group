package com.ivantimarket.ivanti.dto.packages;

import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.model.Version;
import lombok.Data;

@Data
public class NewPackageDTO {

    private long id;
    private String title;
    private UserDTO creator;
    private String intro;
    private Version version;
}
