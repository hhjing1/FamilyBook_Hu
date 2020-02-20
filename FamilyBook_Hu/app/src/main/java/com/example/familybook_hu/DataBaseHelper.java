package com.example.familybook_hu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATE = "cost_date";
    public static final String COST_TITLE = "cost_title";
    public static final String ACCOUNT_COST = "account_cost" ;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "account_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists account_cost("+
                "id integer primary key,"+
                "cost_title varchar,"+
                "cost_date varchar,"+
                "cost_money varchar)");
    }
    //插入
    public void insertCost(CostBean costBean){
        SQLiteDatabase database=getWritableDatabase();//获取数据库对象
        ContentValues cv=new ContentValues();
        cv.put(COST_TITLE,costBean.costTitle);
        cv.put(COST_DATE,costBean.costDate);
        cv.put(COST_MONEY,costBean.costMoney);
        database.insert(ACCOUNT_COST,null,cv);
    }

    //查询
    public Cursor getAllCostData(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query(ACCOUNT_COST ,null,null,null,null,null,COST_DATE+" ASC");
    }

    //删除
    public void deleteAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("account_cost",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

