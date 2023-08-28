package com.majella.ordermanager.entrypoint.api.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ApiError {

    private List<String> descriptions;

}
