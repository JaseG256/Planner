package com.msa.jrg.userservice.resources;

import com.msa.jrg.userservice.exception.UserNotFoundException;
import com.msa.jrg.userservice.model.User;
import com.msa.jrg.userservice.payload.UserIdentityAvailability;
import com.msa.jrg.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@RequestMapping("/users")
public class UserRestController {

    @Qualifier("userServicer")
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user/add")
    public User create(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //    @PreAuthorize("hasRole('USER')")
//    @Secured("ROLE_USER")
//    @GetMapping("/user/me")
//    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
//        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
//                currentUser.getAvatar().getData());
//        return userSummary;
//    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/user/{id}")
    public User findOne(@PathVariable("id") Long id){
        return userService.getById(id).orElseThrow(UserNotFoundException::new);
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping(path="/users/{username}")
    public User findByUserName(@PathVariable("username") String userName) {
        return userService.findByUsername(userName);
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "users/email/{email}")
    public User findByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

//    @PreAuthorize("hasRole('USER')")
    @PutMapping(path = "/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody User user){
        user.setId(id);
        return userService.saveOrUpdate(user);
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userService.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userService.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(path ="/user/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }


    //    @Secured("ROLE_USER")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/users")
    public List<User> findAll(){
        return userService.listAll();
    }
}
