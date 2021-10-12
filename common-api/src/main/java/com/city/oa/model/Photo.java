package com.city.oa.model;

import lombok.Data;

@Data
public class Photo {
    private Integer commoditySaleId;
    private String photoName;
    private String photoType;
    private byte[] photo;
}
