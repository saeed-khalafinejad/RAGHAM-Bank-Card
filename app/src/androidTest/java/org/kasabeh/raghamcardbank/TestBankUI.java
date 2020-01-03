package org.kasabeh.raghamcardbank;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.ui.ActNewBank;
import org.kasabeh.raghamcardbank.utils.RaghamConst;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestBankUI extends AbstractTester{

    private static final String NEW_BANK = "Another Bank";

    @Rule
    public ActivityScenarioRule<ActNewBank> mActivityRule = new ActivityScenarioRule<>(ActNewBank.class);

    @Test
    public void addingBankViaUI() {
        try {
            onView(withId(R.id.edtBankName))
                    .perform(typeText(NEW_BANK));
            onView(withId(R.id.mnu_save)).perform(click());
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            BankDB bankDB = new BankDB(appContext);
            Bank bank = bankDB.loadBankByName(NEW_BANK);
            assertNotNull(bank);
            assertEquals(RaghamConst.BNK_DEF, bank.getBankLogoFileName());
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
}
