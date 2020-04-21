package com.cio.gidservice.models;

public final class UserProperties{

    private static UserProperties userProperties;

    private static boolean isAdmin;

    private static User user;

    private UserProperties() {
        isAdmin = false;
    }


    public static UserProperties getInstance() {
        if(userProperties == null) {
            userProperties = new UserProperties();
        }
        return userProperties;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setUsername(String username) {
        getUser().setName(username);
    }

    public static void setPassword(String password) {
        getUser().setPassword(password);
    }

    public static void setPhoneNumber(String number) {
        getUser().setPhoneNumber(number);
    }

    public static void setIsAdmin(boolean b) {
        isAdmin = b;
    }

    public static User getUser() {
        if(user == null)
            user = new User(null, null, null, null);
        return user;
    }
}
