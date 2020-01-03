package org.kasabeh.raghamcardbank;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.db.CardNODB;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.logical.CardNO;
import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.StrPross;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestAddBankCard extends AbstractTester{

    private static final String CARD_NO = "6104337945675757";

    @Test
    public void addingBankCard() {
        new TestBankDB().addingBank();
        new TestPersonDB().addingPerson();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        DBConn db = DBConn.createConn(appContext);
        try{
            PersonDB personDB = new PersonDB(appContext);
            Person person = personDB.loadObject(TestPersonDB.PERSON_NAME, db);
            BankDB bankDB = new BankDB(appContext);
            Bank bank = bankDB.loadBankByName(TestBankDB.NEW_BANK);
            CardNO card = new CardNO(appContext, person, bank, CARD_NO, "", "", "");
            CardNODB cardNODB = new CardNODB(appContext);
            cardNODB.insert(card, db);
            //Read the inserted card number record from database.
            Cursor cr = db.getReadableDatabase().rawQuery("select * from vNumbers where num = " +
                            StrPross.Qoute(CARD_NO), null);
            try{
                assertTrue(cr.moveToFirst());
                NOsView accountsNumberView = new NOsView(appContext);
                card = (CardNO) accountsNumberView.loadObject(cr);
            } finally {
                cr.close();
            }
            assertNotNull(card);
            assertEquals(CARD_NO, card.getNum());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            DBConn.destroyConnection();
        }
    }
}
