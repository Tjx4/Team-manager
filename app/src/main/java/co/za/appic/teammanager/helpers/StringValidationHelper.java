package co.za.appic.teammanager.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidationHelper {

    public static boolean isValidName(String name){
        if(name == null)
            return false;

        return !name.isEmpty(); //evaluateRegex(name, "^[a-zA-Z\\\\s]+");
    }

    public static boolean isValidUsername(String username){
        return isValidCell(username) || isValidEmail(username) ;
    }

    public static boolean isValidSurname(String surname){
        if(surname == null)
            return false;

        return !surname.isEmpty(); //evaluateRegex(surname, "^[a-zA-Z\\\\s]+");
    }

    public static boolean isValidPassword(String password){
        if(password == null)
            return false;

        return evaluateRegex(password, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
    }

    public static boolean isValidNumberPassword(String password){
        if(password == null)
            return false;

        return password.length() > 3;
    }

    public static boolean isValidGender(String gender){
        if(gender == null)
            return false;

        return !gender.isEmpty();
    }

    public static boolean isValidCell(String cellNumber){
        if(cellNumber == null)
            return false;

        return evaluateRegex(cellNumber, "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
    }

    public static boolean isVaLidOTP(String otp){
        if(otp == null)
            return false;

        return otp.length() == 4;
    }

    public static boolean isValidEmail(String email){
        if(email == null)
            return false;

        return evaluateRegex(email, "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    }

    public static boolean isValidToke(String token){
        if(token == null)
            return false;

        return !token.isEmpty();
    }

    public static boolean isValideviceId(String deviceId){
        if(deviceId == null)
            return false;

        return !deviceId.isEmpty();
    }

    public static boolean isMatchPasswords(String password, String passwordConfirmation){
        if(password == null || passwordConfirmation == null)
            return false;

        return password.equals(passwordConfirmation);
    }

    protected static boolean isValidPasswordCreation(String password, String passwordConfirmation) {
        if(password == null || passwordConfirmation == null)
            return false;

        return isValidPassword(password) && isMatchPasswords(password, passwordConfirmation);
    }

    private static boolean evaluateRegex(String val, String regex){
        CharSequence inputStr = val.trim();
        Pattern pattern = Pattern.compile(new String(regex));
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
