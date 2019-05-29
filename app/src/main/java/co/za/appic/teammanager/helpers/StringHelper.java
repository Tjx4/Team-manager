package co.za.appic.teammanager.helpers;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
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

private static void testForDups() {

    List workerIids = new ArrayList();
    List superviseorIds = new ArrayList();

    for(int i = 1; i < 100; ++i){
        String workerId = StringHelper.getWorkerEmployeeId();
        String superVisorId = StringHelper.getSupervisorEmployeeId();

        Log.i("SS", "worker "+i+") "+workerId);
        Log.i("SS", "Supervisor "+i+") "+superVisorId);

        workerIids.add(workerId);
        superviseorIds.add(superVisorId);


        if(workerIids.contains(workerId)){
            Log.i("SS", "----------------------> workerId Dup "+workerId+"/"+workerId);
        }

        if(superviseorIds.contains(superVisorId)){
            Log.i("SS", "----------------------> Supervisor Dup "+superVisorId+"/"+superVisorId);
        }

    }
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
