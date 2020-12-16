package com.epam.jgmp.repository;

import com.epam.jgmp.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  UserAccount save(UserAccount userAccount);

  UserAccount findById(long id);

  List<UserAccount> findAll();

  UserAccount deleteById(long id);
}
