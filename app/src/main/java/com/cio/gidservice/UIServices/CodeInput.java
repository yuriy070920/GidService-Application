package com.cio.gidservice.UIServices;

import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CodeInput {

    protected List<EditText> textList = new ArrayList<>();
    protected int textPosition;
    private int maxAmountDigits = 4;
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
            textPosition++;
            if(textPosition == maxAmountDigits)
                finalAct.act();
        }
    }

    public void eraseDigit() {
        if(textPosition == maxAmountDigits)
            textPosition -= 1;
        if(textPosition >= 0) {
            textList.get(textPosition).setText("");
            textPosition--;
        }
    }

    public void clearAll() {
        for (EditText text: textList) {
            text.setText("");
        }
        textPosition = 0;
    }

}
