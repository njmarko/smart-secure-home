package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateUsersRealEstates {
    @NotNull(message = "List of real estates of interest is required.")
    private List<Integer> realEstates;
}
