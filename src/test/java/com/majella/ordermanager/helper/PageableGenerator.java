package com.majella.ordermanager.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableGenerator {

    public static Pageable generate() {
        return  PageRequest.of(0,1);
    }


}
