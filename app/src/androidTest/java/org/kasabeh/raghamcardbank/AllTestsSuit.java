package org.kasabeh.raghamcardbank;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                TestBankDB.class,
                TestBankUI.class,
                TestPersonDB.class,
                TestAddBankCard.class
        })
public class AllTestsSuit {
}
