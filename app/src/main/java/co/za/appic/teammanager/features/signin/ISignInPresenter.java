package co.za.appic.teammanager.features.signin;

public interface ISignInPresenter {
    void SignInUser(String username, String password);
    void showSignIndError(String title, String message);
}
