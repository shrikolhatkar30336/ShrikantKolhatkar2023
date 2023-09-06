package study.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import study.example.entity.UserInfo;
import study.example.repository.UserRepository;

//1.Implement CRUD API's for the user class (user can have name , I'd , age, city , email id).

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	// Create User
    @PostMapping
    public String createUser(@RequestBody UserInfo user) {
    	UserInfo obj=new UserInfo(user.getId(), user.getName(), user.getAge(), user.getCity(), user.getEmailId());
        repo.save(obj);
 		return "user inserted";
    }

    // Get All Users
    @GetMapping
    public List<UserInfo> getAllUsers() {
        return repo.findAll();
    }

    // Get User by ID
    @GetMapping("/{id}")
    public UserInfo getUserById(@PathVariable int id) {
        Optional<UserInfo> user= repo.findById(id);
        return user.orElse(null);
    }

    // Update User
    @PutMapping("/{id}")
    public UserInfo updateUser(@PathVariable int id, @RequestBody UserInfo updatedUser) {
        Optional<UserInfo> userOptional = repo.findById(id);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            user.setName(updatedUser.getName());
            user.setAge(updatedUser.getAge());
            user.setCity(updatedUser.getCity());
            user.setEmailId(updatedUser.getEmailId());
            return repo.save(user);
        }
        return null;
    }

    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        repo.deleteById(id);
        return "user deleted";
    }

}
