package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.Specialization;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.UserService;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public PageDto<UserDto> getAll(@RequestParam(value = "page", required = false) Integer page,
//                                   @RequestParam(value = "size", required = false) Integer size) {
//        return userService.getAll(page, size);
//    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userService.getUser(userId);
    }

//    @PreAuthorize("hasAnyAuthority('patient','doctor','admin')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDto getAuthenticatedUser() throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User authenticatedUser = (User) auth.getPrincipal();

        return userService.getUser(authenticatedUser.getId());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping("/group")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersGrouped(@RequestParam(value = "field", required = false, defaultValue = "name") String field,
                                         @RequestParam(value = "order", required = false, defaultValue = "asc") String order) throws BadHttpRequest {
        return userService.getUsersGrouped(field, order);
    }

    @GetMapping("/specializations")
    @ResponseStatus(HttpStatus.OK)
    public List<Specialization> getUsersGrouped(){
        return userService.getSpezialization();
    }
}
