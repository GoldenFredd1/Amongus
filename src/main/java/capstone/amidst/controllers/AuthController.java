package capstone.amidst.controllers;

import capstone.amidst.models.AppUser;
import capstone.amidst.security.AppUserService;
import capstone.amidst.security.JwtConverter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService service;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService service) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate (@RequestBody Map<String, String> credentials){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String jwtToken = converter.getTokenFromUser((User) authentication.getPrincipal());

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody AppUser appUser) {
        try {
            appUser.getRoles().add("USER");
            service.add(appUser);
        } catch (ValidationException ex) {
            ValidationErrorResult validationErrorResult = new ValidationErrorResult();
            validationErrorResult.addMessage(ex.getMessage());
            return new ResponseEntity<>(validationErrorResult, HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            ValidationErrorResult validationErrorResult = new ValidationErrorResult();
            validationErrorResult.addMessage("The provided username already exists");
            return new ResponseEntity<>(validationErrorResult, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("appUserId", String.valueOf(appUser.getAppUserId()));

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
