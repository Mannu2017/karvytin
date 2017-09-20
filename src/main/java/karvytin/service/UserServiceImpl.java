package karvytin.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import karvytin.model.Role;
import karvytin.model.User;
import karvytin.repository.RoleRepository;
import karvytin.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(user.getRol());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	
//	@Override
//	public void savePan(User user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole(user.getRol());
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//		userRepository.save(user);
//	}
//	
//	@Override
//	public void savePran(User user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole(user.getRol());
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//		userRepository.save(user);
//	}
//	
//	@Override
//	public void saveUid(User user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole(user.getRol());
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//		userRepository.save(user);
//	}

}
