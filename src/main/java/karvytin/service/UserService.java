package karvytin.service;

import karvytin.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
//	public void savePan(User user);
//	public void savePran(User user);
//	public void saveUid(User user);
}
