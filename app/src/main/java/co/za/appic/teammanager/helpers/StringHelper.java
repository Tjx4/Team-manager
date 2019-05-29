package co.za.appic.teammanager.helpers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Random;
import java.util.UUID;

public class StringHelper {

    public static String getSha1String(String baseString)
    {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(baseString.getBytes("UTF-8"));
            return new BigInteger(1, crypt.digest()).toString(16);
        }
        catch (Exception ex){
            return baseString;
        }
    }

    public static String getSha1String2(String baseString)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(baseString.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String getUniqueString()
    {
        String uniqueString = UUID.randomUUID().toString();
        return uniqueString;
    }

    private static String getEmployeeId() {
        Random random = new Random(System.nanoTime());
        int number = random.nextInt(99999);
        return String.format("%06d", number);
    }

    public static String getWorkerEmployeeId() {
        String employeeId = getEmployeeId();
        employeeId = "GM"+employeeId;
        return employeeId;
    }

    public static String getSupervisorEmployeeId() {
        String employeeId = getEmployeeId();
        employeeId = "SP"+employeeId;
        return employeeId;
    }

}
