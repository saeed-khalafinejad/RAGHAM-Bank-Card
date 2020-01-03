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
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Person;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestPersonDB extends AbstractTester{

    static final String PERSON_NAME = "Saeed";

    @Test
    public void addingPerson() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        DBConn db = DBConn.createConn(appContext);
        try{
            Person person = new Person(appContext, PERSON_NAME);
            PersonDB personDB = new PersonDB(appContext);
            personDB.insert(person, db);
            person = personDB.loadObject(PERSON_NAME, db);
            assertNotNull(person);
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            DBConn.destroyConnection();
        }
    }
}
