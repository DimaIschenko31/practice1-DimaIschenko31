package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.User;
import ua.opnu.practice1_template.repository.UserRepository;

import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Autowired
    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Пошук користувача в базі даних
        Optional<User> userOptional = userRepository.findByEmail(email);

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // Створення нового користувача якщо не існує
            user = new User();
            user.setEmail(email);
            user.setUsername(email); // Використовуємо email як username
            user.setPassword(""); // Пароль не потрібен для OAuth2
            user.setRole(User.Role.USER);
            user.setEnabled(true);
            user = userRepository.save(user);
        }

        return new CustomOAuth2User(oauth2User, user);
    }
}

// Користувацький клас для OAuth2User
class CustomOAuth2User implements OAuth2User {
    private OAuth2User oauth2User;
    private User user;

    public CustomOAuth2User(OAuth2User oauth2User, User user) {
        this.oauth2User = oauth2User;
        this.user = user;
    }

    @Override
    public java.util.Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public User getUser() {
        return user;
    }
}