package com.example.projetmobile_geststock;
// AuthManager.java
public class AuthManager {
    private static AuthManager instance;
    private boolean userLoggedIn = false;


    private AuthManager() {
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public boolean authenticate(String username, String password) throws AuthException {
        boolean authResult = username.equals("utilisateur") && password.equals("motdepasse");

        if (!authResult) {
            // Lancer une exception d'authentification en cas d'échec
            throw new AuthException("Nom d'utilisateur ou mot de passe incorrect");
        }

        userLoggedIn = true;
        return authResult;
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }
}
