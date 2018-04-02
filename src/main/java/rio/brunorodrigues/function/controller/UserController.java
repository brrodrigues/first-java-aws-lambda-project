package rio.brunorodrigues.function.controller;

import org.springframework.web.bind.annotation.*;
import rio.brunorodrigues.function.data.UserData;
import rio.brunorodrigues.function.domain.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public User saveUser(@RequestBody User user){
        User newUser = user;
        newUser.setId(UUID.randomUUID().toString());
        return newUser;
    }

    @GetMapping
    public Collection listUser(@RequestParam("limit") Optional<Integer> limit, Principal principal){
        int queryLimit = 10;

        if (limit.isPresent()){
            queryLimit = limit.get();
        }

        Collection<User> users = new ArrayList<>();

        for (int i = 0; i < queryLimit; i++) {
            User userData = User.builder().id(UUID.randomUUID().toString()).name(UserData.getRandomName()).build();
            users.add(userData);
        }

        return users;
    }

}
