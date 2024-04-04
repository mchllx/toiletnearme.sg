package vttp.batch4.csf.toiletnearme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.exceptions.NoAccessException;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  @Transactional(rollbackFor={InsertUserException.class, NoAccessException.class})
  public void updateUserRoleByEmail(User user, String approverEmail) throws InsertUserException, NoAccessException {

    if (userRepo.selectUserByEmail(approverEmail).getRole() == "USER") {
      System.out.printf(">>>Unsuccessful: User do not have access");
      throw new NoAccessException("Invalid access");
    }

    if (userRepo.selectUserByEmail(user.getEmail()) == null) {
      System.out.printf(">>>Unsuccessful: User not found\n");
      throw new InsertUserException("Invalid request");
    }

    switch (user.getRole()) {
      case "USER":
        user.setRole("ADMIN");
        userRepo.updateUserRoleByEmail(user); 
        break;

      case "ADMIN":
        user.setRole("USER");
        userRepo.updateUserRoleByEmail(user); 
      break;
    
      default: System.out.printf(">>>Unsuccessful: Server error"); 
        break;
    }
    // returning user inserts records into mySQL twice
    System.out.printf(">>>Successful: %s updated", user.getEmail()); 
  }

  public User selectUserByEmail(String email) {
    return userRepo.selectUserByEmail(email);
  }

  public User selectUserById(String userId) {
    return userRepo.selectUserById(userId);
  }

  @Transactional(rollbackFor=InsertUserException.class)
  public void insertUser(User user) throws InsertUserException {

    if (userRepo.insertUser(user) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", user.getEmail());
      throw new InsertUserException("Invalid request");
    }
    // returning user inserts records into mySQL twice
    System.out.printf(">>>Successful: %s inserted", user.getEmail()); 
  }

}