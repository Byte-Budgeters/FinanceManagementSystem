package application.Service;

import application.Model.User;
import application.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public boolean registerUser(String email, String password) throws NoSuchAlgorithmException {
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        String hashedPassword = hashPassword(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public boolean loginUser(String email, String password) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        return userRepository.validateUser(email, hashedPassword);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }
}