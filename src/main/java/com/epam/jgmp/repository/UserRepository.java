package com.epam.jgmp.repository;

import com.epam.jgmp.repository.model.User;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User save(User user);

  @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
  User findById(long id);

  List<User> findAll();

  User deleteById(long id);
}
