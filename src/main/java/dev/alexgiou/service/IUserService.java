package dev.alexgiou.service;

import dev.alexgiou.model.user.User;

public interface IUserService {

    User findUserByToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
