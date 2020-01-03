package org.kasabeh.raghamcardbank;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.utils.RaghamConst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestBankDB extends AbstractTester{

    static final String NEW_BANK = "My Bank";

    @Test
    public void addingBank() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        DBConn db = DBConn.createConn(appContext);
        try{
            Bank bank = new Bank(appContext, NEW_BANK, RaghamConst.BNK_SINA);
            BankDB bankDB = new BankDB(appContext);
            bankDB.insert(bank, db);
            bank = bankDB.loadBankByName(NEW_BANK);
            assertNotNull(bank);
            assertEquals(RaghamConst.BNK_SINA, bank.getBankLogoFileName());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            DBConn.destroyConnection();
        }
    }
}
