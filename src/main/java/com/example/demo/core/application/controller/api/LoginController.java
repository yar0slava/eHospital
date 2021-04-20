package com.example.demo.core.application.controller.api;

import com.example.demo.config.JwtTokenGenerator;
import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.LoginRequestDto;
import com.example.demo.core.application.dto.LoginResponseDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenGenerator jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getSignIn(Model model) {
//        model.addAttribute("user", new User());
        model.addAttribute("error", null);
        return "login";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignUp() {
//        model.addAttribute("user", new User());
        return "signup";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws  Exception{
        System.out.println(loginRequestDto.getEmail());
        authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        System.out.println("TOKN" + token);
        return new LoginResponseDto(token);
    }

    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@RequestBody AddUserDto userDto) throws UserService.WrongHospitalCodeException {

        return userService.addUser(userDto);
    }

    private void authenticate(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
