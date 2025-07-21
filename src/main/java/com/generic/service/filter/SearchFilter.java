package com.generic.service.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFilter {

    private String sortBy;

    private String sortOrder;

    private List<SearchFilterCriteria> searchCriteria;

}
