package com.mercadolivre.desafioquality.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DistrictRepository {

    Map<String, Double> districts = new HashMap<>();

    public Map<String, Double> getDistricts() {
        districts.put("Moema", 8100.0);
        districts.put("Mandaqui", 5400.0);
        districts.put("Pinheiros", 9000.0);
        districts.put("Tatuapé", 4960.0);
        districts.put("Santana", 5190.0);

        return districts;
    }
}
