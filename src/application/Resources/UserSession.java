package application.Resources;

public class UserSession {
    // Static variable to hold the userID globally
    private static Integer userID =  null;

    // Getter for userID
    public static Integer getUserID() {
        return userID;
    }

    // Setter for userID
    public static void setUserID(Integer userID) {
        UserSession.userID = userID;
    }

    // Optionally, you can clear the userID when the session ends
    public static void clearUserID() {
        userID =  null;
    }
}
