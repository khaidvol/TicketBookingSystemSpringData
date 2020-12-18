package com.epam.jgmp.repository;

import com.epam.jgmp.repository.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  UserAccount save(UserAccount userAccount);

  UserAccount findById(long id);

  List<UserAccount> findAll();

  UserAccount deleteById(long id);
}
