package com.example.auth.fasada;


import com.example.auth.entity.*;
import com.example.auth.exceptions.UserDontExistException;
import com.example.auth.exceptions.UserExistingWithMail;
import com.example.auth.exceptions.UserExistingWithName;
import com.example.auth.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController{

    private final UserService userService;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public ResponseEntity<Response> addNewUser(@Valid @RequestBody UserRegisterDTO user){
        try{
            log.info("--START REGISTER USER");
            userService.register(user);
            log.info("--STOP REGISTER USER");
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        }catch (UserExistingWithName e){
            log.info("User dont exist in database");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Code.USER_EXIST_WITH_NAME));
        }catch (UserExistingWithMail existing){
            log.info("User dont exist in database with this mail");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Code.USER_EXIST_WITH_MAIL));
        }
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response){
        log.info("--TRY LOGIN USER");
        return userService.login(response,user);
    }

    @RequestMapping(path = "/auto-login",method = RequestMethod.GET)
    public ResponseEntity<?> autoLogin(HttpServletResponse response,HttpServletRequest request){
        log.info("--TRY AUTO-LOGIN USER");
        return userService.loginByToken(request,response);
    }

    @RequestMapping(path = "/logged-in",method = RequestMethod.GET)
    public ResponseEntity<?> loggedIn(HttpServletResponse response,HttpServletRequest request){
        log.info("--CHECK USER LOGGED-IN");
        return userService.loggedIn(request,response);
    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public ResponseEntity<?> logout( HttpServletResponse response,HttpServletRequest request){
        log.info("--TRY LOGOUT USER");
        return userService.logout(request, response);
    }

    @RequestMapping(path = "/validate",method = RequestMethod.GET)
    public ResponseEntity<Response> validateToken(HttpServletRequest request, HttpServletResponse response) {
        try{
            log.info("--START validateToken");
            userService.validateToken(request,response);
            log.info("--STOP validateToken");
            return ResponseEntity.ok(new Response(Code.PERMIT));
        }catch (IllegalArgumentException | ExpiredJwtException e){
            log.info("Token is not correct");
            return ResponseEntity.status(401).body(new Response(Code.TOKEN_NULL_OR_EXPIRED));
        }
    }
    @RequestMapping(path = "/authorize",method = RequestMethod.GET)
    public ResponseEntity<Response> authorize(HttpServletRequest request, HttpServletResponse response) {
        try{
            log.info("--START authorize");
            userService.validateToken(request,response);
            userService.authorize(request);
            log.info("--STOP authorize");
            return ResponseEntity.ok(new Response(Code.PERMIT));
        }catch (IllegalArgumentException | ExpiredJwtException e){
            log.info("Token is not correct");
            return ResponseEntity.status(401).body(new Response(Code.TOKEN_NULL_OR_EXPIRED));
        }catch (UserDontExistException e1){
            log.info("User dont exist");
            return ResponseEntity.status(401).body(new Response(Code.USER_NOT_EXIST_WITH_NAME_OR_ACCOUNT_NOT_ACTIVATED));
        }
    }

    @RequestMapping(path = "/activate",method = RequestMethod.GET)
    public ResponseEntity<Response> activateUser(@RequestParam String uid){
        try{
            log.info("--START activateUser");
            userService.activateUser(uid);
            log.info("--STOP activateUser");
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        }catch (UserDontExistException e){
            log.info("User dont exist in database");
            return ResponseEntity.status(400).body(new Response(Code.USER_NOT_EXIST));
        }
    }

    @RequestMapping(path = "/reset-password",method = RequestMethod.POST)
    public ResponseEntity<Response> sendMailRecovery(@RequestBody ResetPasswordData resetPasswordData){
        try{
            log.info("--START sendMailRecovery");
            userService.recoveryPassword(resetPasswordData.getEmail());
            log.info("--STOP sendMailRecovery");
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        }catch (UserDontExistException e){
            log.info("User dont exist in database");
            return ResponseEntity.status(400).body(new Response(Code.USER_NOT_EXIST));
        }
    }

    @RequestMapping(path = "/reset-password",method = RequestMethod.PATCH)
    public ResponseEntity<Response> recoveryMail(@RequestBody ChangePasswordData changePasswordData){
        try{
            log.info("--START recoveryMail");
            userService.restPassword(changePasswordData);
            log.info("--STOP recoveryMail");
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        }catch (UserDontExistException e){
            log.info("User dont exist in database");
            return ResponseEntity.status(400).body(new Response(Code.USER_NOT_EXIST));
        }
    }

    @PatchMapping(path = "/change-user-data")
    public ResponseEntity<Response> changeUserData(@RequestBody ChangeUserData changeUserData){
        try{
            userService.changeUserData(changeUserData);
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        } catch (UserDontExistException e){
            return ResponseEntity.status(400).body(new Response(Code.USER_NOT_EXIST));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationMessage handleValidationExceptions(
            MethodArgumentNotValidException ex
    ){
        return new ValidationMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
