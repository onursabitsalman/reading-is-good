package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
}
