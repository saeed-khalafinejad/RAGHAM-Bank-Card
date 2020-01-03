package org.kasabeh.raghamcardbank.db;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/06/2018.
 */
public class DBConn extends SQLiteOpenHelper{

    public static final String DB_NAME = "RaghamDB.db";
    private static final int DB_VER = 1;
    private static DBConn dbConn = null;

    private Context oldContext = null;

    private DBConn(Context context) {
        super(context, DirectoryMng.getDataDir()+ DB_NAME, null, DB_VER);
        oldContext = context;
    }

    public static DBConn createConn(Context newContext){
        if (dbConn==null){
            dbConn = new DBConn(newContext);
            return dbConn;
        }
        if (newContext!=dbConn.oldContext){
            dbConn.close();
            dbConn = new DBConn(newContext);
            return dbConn;
        }
        return dbConn;
    }

    public static void destroyConnection() {
        if (dbConn!=null){
            dbConn.close();
            dbConn = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Persons (_id integer primary key autoincrement," +
                " fullName nvarchar(60) not null)");
        db.execSQL("create unique index UIX_PersonName on Persons(fullName);");

        db.execSQL("create table Banks (_id integer primary key autoincrement,"+
                " bankName nvarchar(60) not null, " +
                " bankLogo nvarchar(60) not null)");
        db.execSQL("create unique index UIX_BankName on Banks(bankName);");

        db.execSQL("create table AccountNOs (_id integer primary key autoincrement,"+
                " personId integer not null, " +
                " bankId interger not null," +
                " num nvarchar(60) not null, " +
                " desc nvarchar(100)," +
                " FOREIGN KEY(personId) REFERENCES Persons(_id)," +
                " FOREIGN KEY(bankId) REFERENCES Banks(_id) )");

        db.execSQL("create table ShabaNOs (_id integer primary key autoincrement,"+
                " personId integer not null, " +
                " bankId interger not null," +
                " num nvarchar(60) not null, " +
                " desc nvarchar(100)," +
                " FOREIGN KEY(personId) REFERENCES Persons(_id)," +
                " FOREIGN KEY(bankId) REFERENCES Banks(_id) )");

        db.execSQL("create table CardNOs (_id integer primary key autoincrement,"+
                " personId integer not null, " +
                " bankId interger not null," +
                " num nvarchar(60) not null, " +
                " desc nvarchar(100)," +
                " cvv nvarchar(10)," +
                " expirationDate nvarchar(10)," +
                " FOREIGN KEY(personId) REFERENCES Persons(_id)," +
                " FOREIGN KEY(bankId) REFERENCES Banks(_id) )");

        db.execSQL("create trigger trg_del_bank after delete on Banks "+
            " begin "+
            " delete from AccountNOs where bankId = OLD._id;" +
            " delete from ShabaNOs where bankId = OLD._id;" +
            " delete from CardNOs where bankId = OLD._id;" +
            " end; ");

        db.execSQL("create trigger trg_del_person after delete on Persons "+
                " begin "+
                " delete from AccountNOs where bankId = OLD._id;" +
                " delete from ShabaNOs where bankId = OLD._id;" +
                " delete from CardNOs where bankId = OLD._id;" +
                " end; ");

        db.execSQL("create view vNumbers as " +
                "select a.*, '' as cvv, '' as expirationDate, b.fullName, c.bankName, c.bankLogo, " +
                NOsView.ROW_KIND_ACCOUNT_NO + " as rowKind " +
                "from AccountNos a " +
                "inner join Persons b on a.personId = b._id " +
                "inner join Banks c on a.bankId = c._id " +
                " union all " +
                "select a.*, '' as cvv, '' as expirationDate, b.fullName, c.bankName, c.bankLogo, " +
                NOsView.ROW_KIND_SHABA_NO + " as rowKind " +
                "from ShabaNos a " +
                "inner join Persons b on a.personId = b._id " +
                "inner join Banks c on a.bankId = c._id " +
                " union all " +
                "select a.*, b.fullName, c.bankName, c.bankLogo, " +
                NOsView.ROW_KIND_CARD_NO + " as rowKind " +
                "from CardNos a " +
                "inner join Persons b on a.personId = b._id " +
                "inner join Banks c on a.bankId = c._id ");

        //inserting banks
        insert_bank(R.string.bnk_melli, RaghamConst.BNK_MELLI, db);
        insert_bank(R.string.bnk_mellat, RaghamConst.BNK_MELLAT, db);
        insert_bank(R.string.bnk_shahr, RaghamConst.BNK_SHAHR, db);
        insert_bank(R.string.bnk_ansar, RaghamConst.BNK_ANSAR, db);
        insert_bank(R.string.bnk_ayandeh, RaghamConst.BNK_AYANDEH, db);
        insert_bank(R.string.bnk_def, RaghamConst.BNK_DEF, db);
        insert_bank(R.string.bnk_dey, RaghamConst.BNK_DEY, db);
        insert_bank(R.string.bnk_eghtesad_novin, RaghamConst.BNK_EGHTESAD_NOVIN, db);
        insert_bank(R.string.bnk_gadeshgari, RaghamConst.BNK_GADESHGARI, db);
        insert_bank(R.string.bnk_gharzolhassaneh_mehreiran, RaghamConst.BNK_GHARZOLHASSANEH_MEHREIRAN, db);
        insert_bank(R.string.bnk_hekmat_iranian, RaghamConst.BNK_HEKMAT_IRANIAN, db);
        insert_bank(R.string.bnk_iran_venezuela, RaghamConst.BNK_IRAN_VENEZUELA, db);
        insert_bank(R.string.bnk_iranzamin, RaghamConst.BNK_IRANZAMIN, db);
        insert_bank(R.string.bnk_keshavarzi, RaghamConst.BNK_KESHAVARZI, db);
        insert_bank(R.string.bnk_maskan, RaghamConst.BNK_MASKAN, db);
        insert_bank(R.string.bnk_mehre_eghtesad, RaghamConst.BNK_MEHRE_EGHTESAD, db);
        insert_bank(R.string.bnk_parsian, RaghamConst.BNK_PARSIAN, db);
        insert_bank(R.string.bnk_passargad, RaghamConst.BNK_PASSARGAD, db);
        insert_bank(R.string.bnk_post, RaghamConst.BNK_POST, db);
        insert_bank(R.string.bnk_refah, RaghamConst.BNK_REFAH, db);
        insert_bank(R.string.bnk_saderat, RaghamConst.BNK_SADERAT, db);
        insert_bank(R.string.bnk_saman, RaghamConst.BNK_SAMAN, db);
        insert_bank(R.string.bnk_sanat_madan, RaghamConst.BNK_SANAT_MADAN, db);
        insert_bank(R.string.bnk_sarmayeh, RaghamConst.BNK_SARMAYEH, db);
        insert_bank(R.string.bnk_sepah, RaghamConst.BNK_SEPAH, db);
        insert_bank(R.string.bnk_sina, RaghamConst.BNK_SINA, db);
        insert_bank(R.string.bnk_tejarat, RaghamConst.BNK_TEJARAT, db);
        //inserting banks
    }

    private void insert_bank(int bankName, String bankLogo, SQLiteDatabase db) {
        try {
            db.execSQL("insert into banks (bankName, bankLogo) values(" +
                    StrPross.Qoute(oldContext.getString(bankName)) + "," +
                    StrPross.Qoute(bankLogo) + ")");

        } catch (Exception e) {
            MessDlg.simpleMess(oldContext, StrPross.readableErr(e, oldContext));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
