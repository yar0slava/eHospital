package com.example.demo.core.domain.service;

import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }


    public List<HospitalEntity> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<HospitalEntity> findHospitals(String town, String region) {
        if (town == "" && region == "") {
            return getAllHospitals();
        }
        if (town == "") {
            return hospitalRepository.findAllWhereRegionLike(region);
        }
        if (region == "") {
            return hospitalRepository.findAllWhereTownLike(town);
        }

        return hospitalRepository.findAllWhereTownLikeAndRegionLike(town, region);
    }

    public List<HospitalEntity> findHospitalsByNameTownRegion(String search) {
        return hospitalRepository.findAllWhereNameLikeOrTownLikeOrRegionLike(search);
    }


    public Optional<HospitalEntity> getHospitalById(long id) {
        return hospitalRepository.findById(id);
    }

//    public List<HospitalEntity> getHospitalsByIds(List<UserEntity> userEntities) {
//        List<HospitalEntity> rsult;
//        for (int i = 0; i < userEntities.size(); i++) {
//            rsult.add(getHospitalById(userEntities.get(i).))
//        }
//        return rsult;
//    }
}
