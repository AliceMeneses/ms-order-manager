package com.majella.ordermanager.helper;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerJavaUtil {

    private static final Faker FAKER = new Faker(new Locale("pt-BR"));

    public static Faker getFaker() {
        return FAKER;
    }

}
