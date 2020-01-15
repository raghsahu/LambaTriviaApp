package com.trivia.lambatriviaapp.Session.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Question_practice;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.QuizDataCount;

import java.util.ArrayList;
import java.util.List;

public class Sqlitehelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PracticeQuiz.db";
    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names

    private static final String COLUMN_ID = "column_id";
    private static final String COLUMN_QUES_ID = "id";
    private static final String COLUMN_P_QUIZ = "p_quiz_eid";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION_A = "option_a";
    private static final String COLUMN_OPTION_B = "option_b";
    private static final String COLUMN_OPTION_C = "option_c";
    private static final String COLUMN_CORRECT_ANS = "correct_ans";
    private static final String COLUMN_COIN = "coin";
    private static final String COLUMN_NAIRA_CURRENCY = "naira_currency";
    private static final String COLUMN_DATE = "date";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_QUES_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_P_QUIZ + " TEXT,"
            + COLUMN_QUESTION + " TEXT,"
            + COLUMN_OPTION_A + " TEXT,"
            + COLUMN_OPTION_B + " TEXT,"
            + COLUMN_OPTION_C + " TEXT,"
            + COLUMN_CORRECT_ANS + " TEXT,"
            + COLUMN_COIN +" TEXT,"
            + COLUMN_NAIRA_CURRENCY + " TEXT,"
            + COLUMN_DATE + " TEXT)";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public Sqlitehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Sqlitehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
     * @param question_practice
     * @param question_practice
     */

    public void addPracticeQuistion(List<Question_practice> question_practice) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0; i<question_practice.size();i++){
            ContentValues values = new ContentValues();

            values.put(COLUMN_QUES_ID, question_practice.get(i).getId());
            values.put(COLUMN_P_QUIZ, question_practice.get(i).getPQuizEid());
            values.put(COLUMN_QUESTION, question_practice.get(i).getQuestion());
            values.put(COLUMN_OPTION_A, question_practice.get(i).getOptionA());
            values.put(COLUMN_OPTION_B, question_practice.get(i).getOptionB());
            values.put(COLUMN_OPTION_C, question_practice.get(i).getOptionC());
            values.put(COLUMN_CORRECT_ANS, question_practice.get(i).getCorrectAns());
            values.put(COLUMN_COIN, question_practice.get(i).getCoin());
            values.put(COLUMN_NAIRA_CURRENCY, question_practice.get(i).getNairaCurrency());
            values.put(COLUMN_DATE, question_practice.get(i).getDate());

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
    public List<Question_practice> getAllQuestion_practice() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_QUES_ID,
                COLUMN_P_QUIZ,
                COLUMN_QUESTION,
                COLUMN_OPTION_A,
                COLUMN_OPTION_B,
                COLUMN_OPTION_C,
                COLUMN_CORRECT_ANS,
                COLUMN_COIN,
                COLUMN_NAIRA_CURRENCY,
                COLUMN_DATE
        };
        // sorting orders
        String sortOrder =
                COLUMN_QUES_ID + " ASC";
        List<Question_practice> userList = new ArrayList<Question_practice>();

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
                Question_practice question_practice = new Question_practice();
                question_practice.setId(cursor.getString(cursor.getColumnIndex(COLUMN_QUES_ID)));
                question_practice.setPQuizEid(cursor.getString(cursor.getColumnIndex(COLUMN_P_QUIZ)));
                question_practice.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                question_practice.setOptionA(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_A)));
                question_practice.setOptionB(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_B)));
                question_practice.setOptionC(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_C)));
                question_practice.setCorrectAns(cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANS)));
                question_practice.setCoin(cursor.getString(cursor.getColumnIndex(COLUMN_COIN)));
                question_practice.setNairaCurrency(cursor.getString(cursor.getColumnIndex(COLUMN_NAIRA_CURRENCY)));
                question_practice.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                // Adding user record to list
                userList.add(question_practice);
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
     * @param questionPractice
     * @param questionPractice
     */
    public void updateQuestion_practice(List<Question_practice> questionPractice) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0; i<questionPractice.size();i++) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_QUES_ID, questionPractice.get(i).getId());
            values.put(COLUMN_P_QUIZ, questionPractice.get(i).getPQuizEid());
            values.put(COLUMN_QUESTION, questionPractice.get(i).getQuestion());
            values.put(COLUMN_OPTION_A, questionPractice.get(i).getOptionA());
            values.put(COLUMN_OPTION_B, questionPractice.get(i).getOptionB());
            values.put(COLUMN_OPTION_C, questionPractice.get(i).getOptionC());
            values.put(COLUMN_CORRECT_ANS, questionPractice.get(i).getCorrectAns());
            values.put(COLUMN_COIN, questionPractice.get(i).getCoin());
            values.put(COLUMN_NAIRA_CURRENCY, questionPractice.get(i).getNairaCurrency());
            values.put(COLUMN_DATE, questionPractice.get(i).getDate());

            // updating row
            db.update(TABLE_USER, values, COLUMN_QUES_ID + " = ?",
                    new String[]{String.valueOf(questionPractice.get(i).getId())});

        }
        db.close();
    }

    /**
     * This method is to delete user record
     *
     //* @param questions
     */
    public void deleteQuestion_practice() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
//        db.delete(TABLE_USER, COLUMN_QUES_ID + " = ?",
//                new String[]{String.valueOf(questions.getId())});

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
