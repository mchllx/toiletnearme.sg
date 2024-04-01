package vttp.batch4.csf.toiletnearme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  @Transactional(rollbackFor=InsertUserException.class)
  public void insertNewUser(User user) throws InsertUserException {

    if (userRepo.insertNewUser(user) == false) {
      System.out.println(">>>unsuccessful");
      throw new InsertUserException("invalid request");
    }
      System.out.println(">>>successfully inserted");
      // returning user inserts records into mySQL twice
  }

  public User selectByEmail(String email) {
    return userRepo.selectByEmail(email);
  }

}