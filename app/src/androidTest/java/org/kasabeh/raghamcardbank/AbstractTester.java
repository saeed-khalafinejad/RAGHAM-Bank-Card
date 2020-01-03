package org.kasabeh.raghamcardbank;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public abstract class AbstractTester {

    @Before
    public void deleteDB(){
        File f = new File(DirectoryMng.getDataDir()+ DBConn.DB_NAME);
        Assert.assertTrue(f.delete());

        f = new File(DirectoryMng.getDataDir()+ DBConn.DB_NAME+"-journal");
        Assert.assertTrue(f.delete());
    }

}
