package dev.alexgiou.service.impl;

import dev.alexgiou.config.JwtProvider;
import dev.alexgiou.model.user.User;
import dev.alexgiou.repository.UserRepository;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public User findUserByToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found");
        }
        return user;
    }
}
