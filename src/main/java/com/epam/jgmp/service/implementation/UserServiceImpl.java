package com.epam.jgmp.service.implementation;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.repository.UserRepository;
import com.epam.jgmp.repository.model.User;
import com.epam.jgmp.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);
  private final UserRepository userRepository;

  @Autowired
  private UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserById(long userId) {

    User user = userRepository.findById(userId);

    if (user == null) {
      LOGGER.error("User not found.");
      throw new ApplicationException("User not found", HttpStatus.NOT_FOUND);
    }

    LOGGER.info("User found: " + user.toString());

    return user;
  }

  public User getUserByEmail(String email) {

    for (User user : userRepository.findAll()) {
      if (user.getEmail().equals(email)) {
        LOGGER.info("User found: " + user.toString());
        return user;
      }
    }
    LOGGER.error("User not found.");
    throw new ApplicationException("User not found", HttpStatus.NOT_FOUND);
  }

  public List<User> getUsersByName(String name, int pageSize, int pageNum) {

    List<User> foundUsers = new ArrayList<>();

    for (User user : userRepository.findAll()) {
      if (user.getName().contains(name)) {
        foundUsers.add(user);
      }
    }

    LOGGER.info(String.format("%s user(s) found: ", foundUsers.size()));
    foundUsers.forEach(LOGGER::info);

    return foundUsers;
  }

  public User createUser(User user) {

    if (!isMailFree(user)) {
      LOGGER.error("User not created because of provided email address already used.");
      throw new ApplicationException("User not created", HttpStatus.BAD_REQUEST);
    }

    User savedUser = userRepository.save(user);
    LOGGER.info("User created successfully. User details: " + savedUser.toString());

    return userRepository.findById(savedUser.getId());
  }

  public User updateUser(User user) {

    if (userRepository.findById(user.getId()) == null || !isMailFree(user)) {
      LOGGER.error("User not updated because not found or provided email address already used.");
      throw new ApplicationException("User not updated", HttpStatus.NOT_FOUND);
    }

    userRepository.save(user);
    LOGGER.info("User updated successfully. User details: " + user.toString());

    return userRepository.findById(user.getId());
  }

  public boolean deleteUser(long userId) {

    boolean isUserDeleted = false;

    if (userRepository.findById(userId) != null) {
      userRepository.deleteById(userId);
      isUserDeleted = true;
    }
    LOGGER.info("User deleted: " + isUserDeleted);

    return isUserDeleted;
  }

  // private method for mail check
  private boolean isMailFree(User user) {

    boolean isMailFree = true;

    for (User storedUser : userRepository.findAll()) {
      if (storedUser.getId() != user.getId() && storedUser.getEmail().equals(user.getEmail()))
        isMailFree = false;
    }

    return isMailFree;
  }
}
