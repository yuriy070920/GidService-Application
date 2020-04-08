package com.cio.gidservice.UIServices;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CodeInput {

    protected List<EditText> textList = new ArrayList<>();
    protected int textPosition;
    private int maxAmountDigits;
    FinalAct finalAct;

    public CodeInput(List<EditText> texts, FinalAct finalAct) {
        textList = texts;
        maxAmountDigits = texts.size();
        textPosition = 0;
        this.finalAct = finalAct;
    }

    public void enterDigit(String digit) {
        if(textPosition < 0)
            textPosition = 0;
        if(textPosition < maxAmountDigits) {
            textList.get(textPosition).setText(digit);
            textList.get(textPosition).setFocusable(true);
            textPosition++;
            if(textPosition == maxAmountDigits){
                finalAct.act();
            }
        }
    }

    public void eraseDigit() {
        if(textPosition >= maxAmountDigits)
            textPosition = maxAmountDigits - 1;
        if(textPosition - 1 >= 0) {
            textPosition--;
            textList.get(textPosition).setText("");
        }
    }

    public void clearAll() {
        for (EditText text: textList) {
            text.setText("");
        }
        textPosition = 0;
    }

    public String toBase64(BitmapDrawable bitmapDrawable) {
        // Получаем изображение из ImageView
        Bitmap bitmap = bitmapDrawable.getBitmap();

        // Записываем изображение в поток байтов.
        // При этом изображение можно сжать и / или перекодировать в другой формат.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Получаем изображение из потока в виде байтов
        byte[] bytes = byteArrayOutputStream.toByteArray();

        // Кодируем байты в строку Base64 и возвращаем

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
