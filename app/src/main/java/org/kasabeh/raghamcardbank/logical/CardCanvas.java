package org.kasabeh.raghamcardbank.logical;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Saeed on 06/27/2018.
 */
public class CardCanvas extends BitmapDrawable {

    private static final float CTotalWidth = 176.39f;
    private static final float CTotalHeight = 107.95f;

    private static Paint stPaint = null;
    private static Paint getPaint(Activity act){
        if (stPaint==null){
            stPaint = new Paint();
            Typeface typeFace = Typeface.createFromAsset(act.getAssets(), "fonts/bnaz.ttf");
            stPaint.setColor(Color.BLACK);
            stPaint.setTypeface(typeFace);
            stPaint.setTextSize(65);
            stPaint.setAntiAlias(true);
        }
        return stPaint;
    }

    private Activity act;
    private String cardNO;
    private String ownerName;
    private String cvv;
    private String expirationDate;
    private String bankName;

    public CardCanvas(Bitmap bmp, String cardNO, String ownerName, String cvv, String expirationDate,
            String bankName, Activity anAct) {
        super(anAct.getResources(), bmp);
        this.act = anAct;
        this.cardNO = cardNO;
        this.ownerName = ownerName;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.bankName = bankName;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (!bankName.equals(""))
            printTxtAt(170.0f, 20.0f, bankName, getPaint(act), canvas);
        printNumAt(17.0f, 62.0f, cardNO.substring(0, 4), getPaint(act), canvas);
        printNumAt(54.0f, 62.0f, cardNO.substring(4, 8), getPaint(act), canvas);
        printNumAt(91.0f, 62.0f, cardNO.substring(8, 12), getPaint(act), canvas);
        printNumAt(128.0f, 62.0f, cardNO.substring(12, 16), getPaint(act), canvas);

        printTxtAt(156.0f, 75.0f, ownerName, getPaint(act), canvas);
        printTxtAt(156.0f, 88.0f, cvv, getPaint(act), canvas);
        printNumAt(17.0f, 88.0f, expirationDate, getPaint(act), canvas);
    }

    private void printTxtAt(float x, float y, String str, Paint paint, Canvas canvas) {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        x = (getBitmap().getWidth() * x / CTotalWidth) - bounds.width();//convert x from mm to pixel
        y = getBitmap().getHeight() * y / CTotalHeight;//convert y from mm to pixel
        canvas.drawText(str, x, y, paint);
    }

    private void printNumAt(float x, float y, String num, Paint paint, Canvas canvas) {
        x = getBitmap().getWidth() * x / CTotalWidth;//convert x from mm to pixel
        y = getBitmap().getHeight() * y / CTotalHeight;//convert y from mm to pixel
        canvas.drawText(num, x, y, paint);
    }
}