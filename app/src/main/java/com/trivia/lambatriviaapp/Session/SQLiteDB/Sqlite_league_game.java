package com.trivia.lambatriviaapp.Session.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trivia.lambatriviaapp.Model_Class.League_game_model.League_Question_Model;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Question_practice;

import java.util.ArrayList;
import java.util.List;

public class Sqlite_league_game extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LeagueGame.db";

    // User table name
    private static final String TABLE_USER = "League_quiz";

    // User Table Columns names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_QUES_NO = ("ques_no");
    private static final String COLUMN_LEAGUE_ID = "league_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION_A= "option_a";
    private static final String COLUMN_OPTION_B = "option_b";
    private static final String COLUMN_OPTION_C = "option_c";
    private static final String COLUMN_PREDICTION = "prediction_description";
    private static final String COLUMN_COIN = "coin";
    private static final String COLUMN_NAIRA_CURRENCY = "naira_currency";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + COLUMN_LEAGUE_ID + " TEXT ,"
            + COLUMN_QUESTION + " TEXT ,"
            + COLUMN_OPTION_A + " TEXT ,"
            + COLUMN_OPTION_B + " TEXT ,"
            + COLUMN_OPTION_C + " TEXT ,"
            + COLUMN_PREDICTION + " TEXT ,"
            + COLUMN_TYPE + " TEXT ,"
            + COLUMN_COIN + " TEXT ,"
            + COLUMN_QUES_NO + " INTEGER ,"
            + COLUMN_NAIRA_CURRENCY + " TEXT)";


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public Sqlite_league_game(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param league_question_models
     */

    public void addLeague_quiz(List<League_Question_Model> league_question_models) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0; i<league_question_models.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, league_question_models.get(i).getId());
            values.put(COLUMN_LEAGUE_ID, league_question_models.get(i).getLeagueId());
            values.put(COLUMN_TYPE, league_question_models.get(i).getType());
            values.put(COLUMN_QUESTION, league_question_models.get(i).getQuestion());
            values.put(COLUMN_OPTION_A, league_question_models.get(i).getOptionA());
            values.put(COLUMN_OPTION_B, league_question_models.get(i).getOptionB());
            values.put(COLUMN_OPTION_C, league_question_models.get(i).getOptionC());
            values.put(COLUMN_PREDICTION, league_question_models.get(i).getPredictionDescription());
            values.put(COLUMN_COIN, league_question_models.get(i).getCoin());
            values.put(COLUMN_NAIRA_CURRENCY, league_question_models.get(i).getNairaCurrency());
            values.put(COLUMN_QUES_NO, league_question_models.get(i).getQues_num());
            // Inserting Row
            db.insert(TABLE_USER, null, values);

        }
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<League_Question_Model> getAllLeague_quiz() {
        // array of columns to fetch

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_LEAGUE_ID,
                COLUMN_QUESTION,
                COLUMN_TYPE,
                COLUMN_OPTION_A,
                COLUMN_OPTION_B,
                COLUMN_OPTION_C,
                COLUMN_PREDICTION,
                COLUMN_COIN,
                COLUMN_NAIRA_CURRENCY,
                COLUMN_QUES_NO
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_ID + " ASC";
        List<League_Question_Model> userList = new ArrayList<League_Question_Model>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                League_Question_Model leagueQuestionModel = new League_Question_Model();
                leagueQuestionModel.setId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                leagueQuestionModel.setLeagueId(cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_ID)));
                leagueQuestionModel.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                leagueQuestionModel.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                leagueQuestionModel.setOptionA(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_A)));
                leagueQuestionModel.setOptionB(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_B)));
                leagueQuestionModel.setOptionC(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_C)));
                leagueQuestionModel.setPredictionDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PREDICTION)));
                leagueQuestionModel.setCoin(cursor.getString(cursor.getColumnIndex(COLUMN_COIN)));
                leagueQuestionModel.setNairaCurrency(cursor.getString(cursor.getColumnIndex(COLUMN_NAIRA_CURRENCY)));
                leagueQuestionModel.setQues_num(cursor.getInt(cursor.getColumnIndex(COLUMN_QUES_NO)));
                // Adding user record to list
                userList.add(leagueQuestionModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
//    public void updateLeague_quiz(ChatUserModal user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_NAME, user.getUserName());
//        values.put(COLUMN_U_ID, user.getUserId());
//        values.put(COLUMN_USER_FCM, user.getFcmId());
//        values.put(COLUMN_USER_UDID, user.getUdid());
//        values.put(COLUMN_USER_FCM, user.getFcmId());
//
//        // updating row
//        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
//                new String[]{String.valueOf(user.getUserId())});
//        db.close();
//    }

    /**
     * This method is to delete user record
     *
     * //@param user
     */
    public void delete_league_quiz() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
//        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
//                new String[]{String.valueOf(user.getUserId())});

        //*********delete all table
        db.execSQL("delete from "+ TABLE_USER);
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
//    public boolean checkUser(String email) {
//
//        // array of columns to fetch
//        String[] columns = {
//                COLUMN_USER_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // selection criteria
//        String selection = COLUMN_U_ID + " = ?";
//
//        // selection argument
//        String[] selectionArgs = {email};
//
//        // query user table with condition
//        /**
//         * Here query function is used to fetch records from user table this function works like we use sql query.
//         * SQL query equivalent to this query function is
//         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
//         */
//        Cursor cursor = db.query(TABLE_USER, //Table to query
//                columns,                    //columns to return
//                selection,                  //columns for the WHERE clause
//                selectionArgs,              //The values for the WHERE clause
//                null,                       //group the rows
//                null,                      //filter by row groups
//                null);                      //The sort order
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//    }
}
