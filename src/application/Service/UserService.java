package application.Service;

import application.Model.User;
import application.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public boolean registerUser(String email, String password, String firstName, String lastName) throws NoSuchAlgorithmException {
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        String hashedPassword = hashPassword(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedAt(new Date());
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
    public User getCurrentUser() {
    	return userRepository.getCurrentUser();
    }
    public boolean checkPassword(String password, String Hash) throws NoSuchAlgorithmException {
    	return hashPassword(password).equals(Hash);
    }
    public boolean changePassword(String newPassword) throws NoSuchAlgorithmException {
    	
    	return userRepository.updatePassword(hashPassword(newPassword));
    }
    public boolean deleteUser() {
    	return userRepository.deleteUser();
    }
    
}