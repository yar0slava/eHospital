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

    public UserDto addUser(AddUserDto user){

        UserEntity userEntity = addUserMapper.toEntity(user);

        //setting authorities
        Set<Authority> authorities = new TreeSet<>();

        for (String s: user.getAuthority()) {
            authorities.add(authorityRepository.findByNameEquals(s).get());
        }
        userEntity.setAuthority(authorities);

        //setting specializations
        Set<Specialization> specializations = new TreeSet<>();

        for (String s: user.getSpecializations()) {
            authorities.add(authorityRepository.findByNameEquals(s).get());
        }

        userEntity.setSpecialization(specializations);

        if(user.getHospitalCode() != ""){
            Optional<HospitalEntity> hospitalEntity = hospitalRepository.findByCodeHospital(user.getHospitalCode());
            if(hospitalEntity.isPresent()){

                userEntity.setHospital(hospitalEntity.get());

                UserEntity addUser = userRepository.save(userEntity);
                return userMapper.toDto(addUser);
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
}
