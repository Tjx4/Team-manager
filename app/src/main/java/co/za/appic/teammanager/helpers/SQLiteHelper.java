package co.za.appic.teammanager.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.models.UserModel;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "geartronix_db";
    private static final String TABLE_USERS = "users";
    private static final String EMPLOYEE_ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String GENDER = "gender";
    private static final String MOBILENUMBER = "mobilenumber";
    private static final String EMAIL = "email";
    private static final String MEMBERTYPE = "member_type";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + EMPLOYEE_ID + " TEXT PRIMARY KEY,"
                + NAME + " TEXT,"
                + SURNAME + " TEXT,"
                + GENDER + " TEXT,"
                + MOBILENUMBER + " TEXT,"
                + EMAIL + " TEXT,"
                + MEMBERTYPE + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void addUser(UserModel user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(EMPLOYEE_ID, user.getEmployeeId());
            values.put(NAME, user.getName());
            values.put(SURNAME, user.getSurname());
            values.put(GENDER, user.getGender().getId());
            values.put(MOBILENUMBER, user.getMobile());
            values.put(EMAIL, user.getEmail());
            values.put(MEMBERTYPE, user.getEmployeeType().getUserId());

            db.insert(TABLE_USERS, null, values);
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
            StatsHelper.sendErroReport(e, "failed to cache user");
        }
    }

    public UserModel getUser(String employeeId) {
        UserModel user = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_USERS, getFieldsArray(), EMPLOYEE_ID + "=?", new String[] { String.valueOf(employeeId) }, null, null, null, null);
            user = getUserFromCursor(cursor);
        }
        catch (Exception e){
            StatsHelper.sendErroReport(e, "failed to get cached user");
        }

        return user;
    }

    public UserModel getUserFromCursor(Cursor cursor) {
        UserModel user = new UserModel();

        if (cursor != null)
            cursor.moveToFirst();

        user.setEmployeeId(cursor.getString(0));
        user.setName(cursor.getString(1));
        user.setSurname(cursor.getString(2));
        user.setGender(UserGender.values()[cursor.getInt(3)]);
        user.setEmployeeType(EmployeeType.values()[cursor.getInt(4)]);

        return user;
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> userList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel user = getUserFromCursor(cursor);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public UserModel getFirst() {
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        UserModel user = null;

        if (cursor.moveToFirst()) {
            do {
                user = getUserFromCursor(cursor);
                break;
            } while (cursor.moveToNext());
        }

        return user;
    }

    public UserModel getLastUser() {
        List<UserModel> userList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel user = getUserFromCursor(cursor);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        int userId = userList.size() - 1;
        return userList.get(userId);
    }

    public int updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, user.getEmployeeId());
        values.put(NAME, user.getName());
        values.put(SURNAME, user.getSurname());
        values.put(GENDER, user.getGender().getId());
        values.put(MOBILENUMBER, user.getMobile());
        values.put(EMAIL, user.getEmail());
        values.put(MEMBERTYPE, user.getEmployeeType().getUserId());

        return db.update(TABLE_USERS, values, EMPLOYEE_ID + " = ?", new String[] { String.valueOf(user.getEmployeeId()) });
    }

    public void deleteUser(UserModel user) {
        deleteUserByid(user.getEmployeeId());
    }

    public void deleteUserByid(String employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, EMPLOYEE_ID + " = ?", new String[] { String.valueOf(employeeId) });
        db.close();
    }

    public void removeIfExist(UserModel user) {
        if(isExistUser(user))
            deleteUser(user);
    }

    private String[] getFieldsArray(){
        return new String[] {EMPLOYEE_ID, NAME, SURNAME, GENDER, MOBILENUMBER, EMAIL, MEMBERTYPE};
    }

    public boolean isExistUser(UserModel user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, getFieldsArray(), EMPLOYEE_ID + "=?", new String[] { user.getEmployeeId() }, null, null, null, null);
        return  cursor != null && cursor.getCount() > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}