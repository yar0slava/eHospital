package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.Specialization;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.AuthorityRepository;
import com.example.demo.core.database.repository.HospitalRepository;
import com.example.demo.core.database.repository.SpecializationRepository;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.mapper.AddUserMapper;
import com.example.demo.core.mapper.UpdateUserMapper;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final SpecializationRepository specializationRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final AddUserMapper addUserMapper;
    private final UpdateUserMapper updateUserMapper;

    public UserService(HospitalRepository hospitalRepository, UserRepository userRepository,
                       SpecializationRepository specializationRepository, AuthorityRepository authorityRepository,
                       UserMapper userMapper, AddUserMapper addUserMapper, UpdateUserMapper updateUserMapper){
        this.hospitalRepository = hospitalRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.specializationRepository = specializationRepository;
        this.authorityRepository = authorityRepository;
        this.addUserMapper = addUserMapper;
        this.updateUserMapper = updateUserMapper;
    }

    public PageDto<UserDto> getAll(Integer page, Integer size){

        PageDto<UserDto> pageDto = new PageDto<>();
        List<UserDto> users;

        if (page == null && size == null) {
            users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            users = StreamSupport.stream(userRepository.findAll(PageRequest.of(page != null ? page : 0, size != null ? size : 10)).spliterator(), false)
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        }

        pageDto.setElements(users);
        pageDto.setSize(users.size());

        return pageDto;
    }

    public List<UserDto> getUsersGrouped(String field, String order) throws BadHttpRequest {

        switch (order){
            case "asc": return StreamSupport.stream(userRepository.findAll(Sort.by(Sort.Order.asc(field))).spliterator(), false)
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
            case "desc": return StreamSupport.stream(userRepository.findAll(Sort.by(Sort.Order.desc("age"))).spliterator(), false)
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        }
        throw new BadHttpRequest();
    }

    public UserDto getUser(Long id) throws NotFoundException {
        Optional<UserDto> user = userRepository.findById(id).map(userMapper::toDto);
        return user.orElseThrow(() -> new NotFoundException(String.format("User not found with id %s",id)));
    }

    public UserDto addUser(AddUserDto user) throws WrongHospitalCodeException {

        UserEntity userEntity = addUserMapper.toEntity(user);

        //setting authorities
        Set<Authority> authorities = new HashSet<>();

        for (String s: user.getAuthority()) {
            System.out.println(s);
            authorities.add(authorityRepository.findByNameEquals(s).get());
        }
        userEntity.setAuthority(authorities);

        //setting specializations
        Set<Specialization> specializations = new HashSet<>();

        for (String s: user.getSpecializations()) {
            System.out.println(s);
            specializations.add(specializationRepository.findByNameEquals(s).get());
        }

        userEntity.setSpecialization(specializations);

        if(user.getHospitalCode() != ""){
            Optional<HospitalEntity> hospitalEntity = hospitalRepository.findByCodeHospital(user.getHospitalCode());
            if(hospitalEntity.isPresent()){

                userEntity.setHospital(hospitalEntity.get());

                UserEntity addUser = userRepository.save(userEntity);
                return userMapper.toDto(addUser);
            }else {
                throw new WrongHospitalCodeException("Wrong hospital code.");
            }
        }

            UserEntity addUser = userRepository.save(userEntity);
            return userMapper.toDto(addUser);

    }

    public List<Specialization> getSpezialization(){
        return specializationRepository.findAll();
    }

    public UserDto updateUser(UserDto user){
        UserEntity updateUser = userRepository.save(updateUserMapper.toEntity(user));
        return userMapper.toDto(updateUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return userMapper.toModel(userEntity);
    }

    public List<UserEntity> getAllDoctorsOfHospital(long id) {
        List<UserEntity> list = new ArrayList<>();
        Optional<HospitalEntity> hospitalEntity = hospitalRepository.findById(id);
        if(hospitalEntity.isPresent()){
            list = userRepository.findByHospital(hospitalEntity.get());
            return  list;
        }
        return list;
    }

    public List<UserEntity> getAllDoctors() {
        return userRepository.findAllDoctors();

    }

    public Set<Specialization> getSpezialization(List<UserEntity> userEntities) {
        Set<Specialization> specializations = new HashSet<>();

        for(int i=0; i< userEntities.size();i++){
            Set<Specialization> sp = userEntities.get(i).getSpecialization();

            for (Iterator<Specialization> it = sp.iterator(); it.hasNext(); ) {
                Specialization f = it.next();
                specializations.add(f);
            }
        }

        return specializations;

    }

    public Set<String> getTowns(List<UserEntity> userEntities) {
        Set<String> towns = new HashSet<>();
        for(int i=0; i< userEntities.size();i++){
            towns.add(userEntities.get(i).getHospital().getTown());
        }
        return towns;
    }

    public Set<String> getRgions(List<UserEntity> userEntities) {
        Set<String> rgions = new HashSet<>();
        for(int i=0; i< userEntities.size();i++){
            rgions.add(userEntities.get(i).getHospital().getRegion());
        }
        return rgions;
    }

    public Set<UserEntity> hlpr(List<String> spialization){
        Set<UserEntity> rsult= new HashSet<>();

        for(int i=0; i<spialization.size();i++){
            Optional<Specialization> specialization = specializationRepository.findSpecializationByName(spialization.get(i).toLowerCase());
            List<UserEntity> userEntities = userRepository.findBySpecializationContains(specialization.get());
            for(int j=0; j<userEntities.size();j++){
                rsult.add(userEntities.get(j));
            }
        }
        return rsult;
    }

    public List<UserEntity> findDoctors(List<String> spialization, String town, String rgion) {
        List<UserEntity> rsult = new ArrayList();
        List<UserEntity> sp = new ArrayList<>();
        if (spialization != null){
            sp = new ArrayList<>(hlpr(spialization));
        }
        if (spialization == null && town == null && rgion == null) {
            return getAllDoctors();
        }
        if (spialization == null  && town == null)  {
            return userRepository.findAllWhereRgionLike(rgion);
        }
        if (spialization == null  && rgion == null)  {
            return userRepository.findAllWhereTownLike(town);
        }

        if (town == null && rgion == null)  {
            return sp;
        }

        if (spialization == null)  {
            return userRepository.findAllWhereTownAndRegionLike(town,rgion);
        }

        if (town == null)  {
            for(int i=0; i<sp.size();i++){
                if(sp.get(i).getHospital().getRegion().equals(rgion)){
                    rsult.add(sp.get(i));
                }
            }
            return rsult;
        }

        if (rgion == null )  {
            for(int i=0; i<sp.size();i++){
                if(sp.get(i).getHospital().getTown().equals(town)){
                    rsult.add(sp.get(i));
                }
            }
            return rsult;
        }

        //якщо усі
        for(int i=0; i<sp.size();i++){
            if(sp.get(i).getHospital().getTown().equals(town) && sp.get(i).getHospital().getRegion().equals(rgion)){
                rsult.add(sp.get(i));
            }
        }
        return rsult;
    }

    public Set<UserEntity> findDoctorsBySpecializationTownRegion(String search) {
        if(search == ""){
            return new HashSet<>(getAllDoctors());
        }
        List<UserEntity> userEntities = userRepository.findAllWhereTownOrRegionLike(search.toLowerCase());

        Optional<Specialization> specialization = specializationRepository.findSpecializationByName(search);
        List<UserEntity> userEntitiesBySp = new ArrayList<>();
        if(specialization.isPresent()){
            userEntitiesBySp = userRepository.findBySpecializationContains(specialization.get());
        }

        Set<UserEntity> rsult = new HashSet<>(userEntities);
        for(int i=0; i<userEntitiesBySp.size();i++){
            rsult.add(userEntitiesBySp.get(i));
        }
        return rsult;
    }

    public class WrongHospitalCodeException extends Throwable {
        public WrongHospitalCodeException(String s) {
            super(s);
        }
    }
}
