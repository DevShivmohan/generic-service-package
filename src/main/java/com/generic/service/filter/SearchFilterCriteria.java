package com.generic.service.filter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFilterCriteria {
    @NotNull
    @NotBlank
    private String filterKey;

    private List<Object> value = new ArrayList<>();

    @NotNull
    private String operation;
}
