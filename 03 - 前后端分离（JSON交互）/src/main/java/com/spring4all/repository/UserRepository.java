package com.spring4all.repository;

import com.spring4all.entity.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDO, Long> {

    UserDO findByUsername(String username);

}
