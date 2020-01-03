package org.kasabeh.raghamcardbank.utils;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
public interface IFeedback {	

	void choiceAction(int theOperation, int selected, int pos);
	void yesAction(int theOperation, int pos);
	void noAction(int theOperation, int pos);

}
