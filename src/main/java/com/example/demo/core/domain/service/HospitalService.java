package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.AddHospitalDto;
import com.example.demo.core.application.dto.HospitalDto;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.repository.HospitalRepository;
import com.example.demo.core.mapper.AddHospitalMapper;
import com.example.demo.core.mapper.HospitalMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    private final AddHospitalMapper addHospitalMapper;
    public HospitalService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper, AddHospitalMapper addHospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
        this.addHospitalMapper = addHospitalMapper;
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

    public Set<String> getUniqueTowns(List<HospitalEntity> hospitals) {
        Set<String> rsult = new HashSet<>();
        for(int i=0; i< hospitals.size();i++){
            rsult.add(hospitals.get(i).getTown());
        }
        return rsult;
    }

    public Set<String> getUniqueRgions(List<HospitalEntity> hospitals) {
        Set<String> rsult = new HashSet<>();
        for(int i=0; i< hospitals.size();i++){
            rsult.add(hospitals.get(i).getRegion());
        }
        return rsult;
    }

    public HospitalDto addHospital(AddHospitalDto hospitalDto) {
        HospitalEntity hospitalEntity = addHospitalMapper.toEntity(hospitalDto);

        UUID uniqueKey = UUID.randomUUID();

        hospitalEntity.setCodeHospital(uniqueKey.toString());

        System.out.println("hospitallghkgl");
        System.out.println(hospitalEntity.getCodeHospital());
        System.out.println(hospitalEntity.getTown());
        System.out.println(hospitalEntity.getRegion());
        System.out.println(hospitalEntity.getName());
        System.out.println(hospitalEntity.getId());

        HospitalEntity addHospital = hospitalRepository.save(hospitalEntity);
        System.out.println("hospitalll");
        System.out.println(addHospital.getCodeHospital());
        System.out.println(addHospital.getId());
        System.out.println(addHospital.getTown());
        System.out.println(addHospital.getRegion());
        System.out.println(addHospital.getName());
        return hospitalMapper.toDto(addHospital);

    }

//    public List<HospitalEntity> getHospitalsByIds(List<UserEntity> userEntities) {
//        List<HospitalEntity> rsult;
//        for (int i = 0; i < userEntities.size(); i++) {
//            rsult.add(getHospitalById(userEntities.get(i).))
//        }
//        return rsult;
//    }
}
