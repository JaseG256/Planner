package com.msa.jrg.userservice.resources;

import com.msa.jrg.core.payload.ApiResponse;
import com.msa.jrg.userservice.client.DBFileRestClient;
import com.msa.jrg.userservice.exception.UserNotFoundException;
import com.msa.jrg.userservice.model.User;
import com.msa.jrg.userservice.payload.UserIdentityAvailability;
import com.msa.jrg.userservice.payload.UploadFileResponse;
import com.msa.jrg.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@RequestMapping("/users")
public class UserRestController {

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Qualifier("userServicer")
    private final UserService userService;

    @Qualifier("dbFileRestClient")
    private final DBFileRestClient dbFileRestClient;

    public UserRestController(UserService userService, DBFileRestClient dbFileRestClient) {
        this.userService = userService;
        this.dbFileRestClient = dbFileRestClient;
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
        logger.info(userService.propertyConfig().getFind_by_id_message(), id);
        return userService.getById(id).orElseThrow(() -> {
            String msg = String.format(userService.propertyConfig().getUser_exception_message(), id);
            logger.debug(msg);
            return new UserNotFoundException(msg,
                    userService.propertyConfig().getUser_resource_name(),
                    userService.propertyConfig().getUser_field_id(), id);
        });
    }

    @PostMapping(path = "/dbfile/{id}/uploadFile")
    public ApiResponse uploadImage(@PathVariable("id") Long userID, MultipartFile file) {
        User user = userService.getById(userID).orElseThrow(() -> {
            String msg = String.format(userService.propertyConfig().getUser_exception_message(), userID);
            logger.debug(msg);
            return new UserNotFoundException(msg,
                    userService.propertyConfig().getUser_resource_name(),
                    userService.propertyConfig().getUser_field_id(), userID);
        });
        UploadFileResponse uploadFileResponse = dbFileRestClient.uploadImage(file).getBody();
        assert uploadFileResponse != null;
        logger.info("File created location: {}", uploadFileResponse.getUploadFileUri());
        user.setImageFileName(uploadFileResponse.getFileName());
        logger.info(
                userService.propertyConfig().getUpload_image_success_message(), user.getId(), user.getImageFileName()
        );
        return new ApiResponse(true, userService.propertyConfig().getUpload_image_success_message());
    }

    @GetMapping(path = "/dbfile/downloadFile/{fileName}")
    public ResponseEntity<Resource> findByFileName(@PathVariable String fileName) {
        return dbFileRestClient.findByFileName(fileName);
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
