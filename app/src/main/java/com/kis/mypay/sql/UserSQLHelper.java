package com.kis.mypay.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserSQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "DB";
    private static final String DB_NAME = "user.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static UserSQLHelper Helper = null; // 数据库帮助器的实例
    private SQLiteDatabase DB = null; // 数据库的实例
    public static final String TABLE_NAME = "user_info"; // 表的名称

    public UserSQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + "name VARCHAR NOT NULL,"
                        + "phone VARCHAR NOT NULL,"
                        + "token VARCHAR NOT NULL,"
                        + "autoLogin INT"
                        + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_info");
        onCreate(db);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserSQLHelper getInstance(Context context) {
        if (Helper == null) {
            Helper = new UserSQLHelper(context);
        }
        return Helper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (DB == null || !DB.isOpen()) {
            DB = Helper.getReadableDatabase();
        }
        return DB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (DB == null || !DB.isOpen()) {
            DB = Helper.getWritableDatabase();
        }
        return DB;
    }

    // 关闭数据库连接
    public SQLiteDatabase closeLink() {
        if (DB != null && DB.isOpen()) {
            DB.close();
        }
        return DB;
    }

    public int deletePhone(String phone) {
        String condition = String.format("phone='%s'", phone);
        return DB.delete(TABLE_NAME, condition, null);
    }

    // 删除该表的所有记录
    public int deleteAll() {
        return DB.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public boolean insert(UserInfo info) {
        if (!info.phone.isEmpty()) {
            String condition = String.format("phone='%s'", info.phone);
            if (!query(condition).isEmpty()) {
                return update(info, condition) >= 0;
            }
        }
        openWriteLink();
        // 不存在唯一性重复的记录，则插入新记录
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("phone", info.phone);
        cv.put("token", info.token);
        cv.put("autoLogin", info.autoLogin ? 1 : 0);
        // 执行插入记录动作，该语句返回插入记录的行号
        boolean result = DB.insert(TABLE_NAME, "", cv) >= 0;
        closeLink();
        return result;
    }

    // 根据条件更新指定的表记录
    public int update(@NonNull UserInfo info, String condition) {
        openWriteLink();
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("phone", info.phone);
        cv.put("token", info.token);
        cv.put("autoLogin", info.autoLogin ? 1 : 0);
        // 执行更新记录动作，该语句返回更新的记录数量
        int result = DB.update(TABLE_NAME, cv, condition, null);
        closeLink();
        return result;
    }

    public int update(UserInfo info) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(info, String.format("phone='%s'", info.phone));
    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<UserInfo> query(String condition) {
        String sql = String.format("select * from %s where %s;", TABLE_NAME, condition);

        List<UserInfo> infoList = new ArrayList<>();

        openReadLink();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = DB.rawQuery(sql, null);

        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            UserInfo info = new UserInfo();
            info.name = cursor.getString(1);
            info.phone = cursor.getString(2);
            info.token = cursor.getString(3);
            info.autoLogin = cursor.getInt(4) == 1;
            infoList.add(info);
        }

        // 查询完毕，关闭数据库游标
        cursor.close();
        closeLink();
        return infoList;
    }

    public List<UserInfo> queryPhone(String phone) {
        return query(String.format("phone='%s'", phone));
    }
}
