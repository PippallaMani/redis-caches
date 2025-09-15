package com.mani.rest;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mani.binding.User;

@RestController
public class UserRestController {
	private HashOperations<String, Integer, User> hashOps;

	public UserRestController(RedisTemplate<String, User> redisTemplate) {
		hashOps = redisTemplate.opsForHash();
	}

	@PostMapping("/user")
	public String UserData(@RequestBody User  user) {
		hashOps.put("Persons", user.getUid(),user);

		return "Success";

	}

	@GetMapping("/user/{uid}")
	public User GetData(@PathVariable Integer uid) {
		User Value = (User) hashOps.get("Persons", uid);
		return Value;
	}
	@GetMapping("/users")
	public List<User> getAllUsers() {

		return hashOps.values("Persons");

	}
	@DeleteMapping("/user/{uid}")
	public String deleteUserId(@PathVariable Integer uid) {
		
		 hashOps.delete("Persons", uid);
		return "User Deleted";
	}
}

