package com.generic.service.filter;

import org.springframework.util.StringUtils;

public enum SearchOperation {
    CONTAINS, EQUAL, NOT_EQUAL, NUL, NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL,
    ANY, ALL, GREATER_THAN_EQUAL_DATE;

    public static final String[] SEARCH_OPERATION_SET = {"cn", "eq", "ne", "nu", "nn", "gt", "ge", "lt", "le", "gte"};

    public static SearchOperation getSearchOperation(final String input) {
        if(StringUtils.hasText(input)){
            return switch (input) {
                case "cn" -> CONTAINS;
                case "eq" -> EQUAL;
                case "ne" -> NOT_EQUAL;
                case "nu" -> NUL;
                case "nn" -> NOT_NULL;
                case "gt" -> GREATER_THAN;
                case "ge" -> GREATER_THAN_EQUAL;
                case "lt" -> LESS_THAN;
                case "le" -> LESS_THAN_EQUAL;
                case "gte" -> GREATER_THAN_EQUAL_DATE;
                default -> null;
            };
        }
       return null;
    }
}
