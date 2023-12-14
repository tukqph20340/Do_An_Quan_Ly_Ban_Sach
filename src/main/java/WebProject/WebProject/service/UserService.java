package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	List<User> getAllUser();

	Page<User> getAllUser(String user, Pageable pageable);

	User detail(String user);

	User saveUser(User user);

//	User getUserById(String loginname);

	User updateUser(User user);

	void deleteUserById(String id);
	
	User GetUserByEmail(String email);

	User findByIdAndRole(String id, String role);

	List<User> findAll();    
}
