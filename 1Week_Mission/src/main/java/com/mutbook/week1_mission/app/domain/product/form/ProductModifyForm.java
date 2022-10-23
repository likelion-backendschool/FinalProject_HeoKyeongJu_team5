package com.mutbook.week1_mission.app.domain.product.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductModifyForm {
    @NotBlank
    private String subject;
    @NotNull
    private int price;
    @NotBlank
    private String productTagContents;
}
