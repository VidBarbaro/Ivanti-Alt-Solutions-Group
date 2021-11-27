package com.ivantimarket.ivanti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Version {
    private String name;
    private String readme;
    private String path;
}
