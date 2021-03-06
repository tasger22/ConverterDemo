package hu.bme.aut.converterhomework.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import hu.bme.aut.converterhomework.ConvertValue;
import hu.bme.aut.converterhomework.R;

/**
 * Created by Stealth on 2017. 11. 21..
 */

public class LengthFragment extends Fragment {

    private double baseValue = 0; //always in meters to make calculating values easy
    private static String currentConvert; //string to see which measure we convert from

    //First value of array: Base convert values from meters to any, second: position in the line up, zero means it is the current
    private static ConvertValue meter ;
    private static ConvertValue foot;
    private static ConvertValue yard;
    private static ConvertValue inch;
    private static ConvertValue kilometers;
    private static ConvertValue miles;

    private static double currentConvertValue = 1; //default is one because 1 meter is 1 meter :)
    private TextView[] valueArray = new TextView[5];

    private static Context statContext;

    private static TextView currentText;
    private static TextView line1Text;
    private static TextView line2Text;
    private static TextView line3Text;
    private static TextView line4Text;
    private static TextView line5Text;

    private static EditText currentValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        meter = new ConvertValue(1,0,getString(R.string.meterPref));
        foot = new ConvertValue(3.2808399,1,getString(R.string.footPref));
        yard = new ConvertValue(1.0936133,3,getString(R.string.yardPref));
        inch = new ConvertValue(39.3700787,2,getString(R.string.inchPref));
        kilometers = new ConvertValue(0.001,5,getString(R.string.kilometerPref));
        miles = new ConvertValue (0.000621371192,4,getString(R.string.milesPref));

        currentConvert = this.getString(R.string.meterText);

        final View root = inflater.inflate(R.layout.length_layout,container,false);

        statContext = getContext();

        valueArray[0] = (TextView) root.findViewById(R.id.line1Value);
        valueArray[1] = (TextView) root.findViewById(R.id.line2Value);
        valueArray[2] = (TextView) root.findViewById(R.id.line3Value);
        valueArray[3] = (TextView) root.findViewById(R.id.line4Value);
        valueArray[4] = (TextView) root.findViewById(R.id.line5Value);


        currentValue = (EditText) root.findViewById(R.id.lengthCurrentValue);
        currentValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LinearLayout valuesLayout = (LinearLayout) root.findViewById(R.id.valuesLayout);

                if(currentValue.getText().toString().contentEquals(".")){
                    currentValue.setText("");
                }
                else if (!currentValue.getText().toString().isEmpty()){
                    makeCalculations(Double.parseDouble(currentValue.getText().toString()));
                    valuesLayout.setVisibility(LinearLayout.VISIBLE);
                }
                else{
                    baseValue = 0;
                    valuesLayout.setVisibility(LinearLayout.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setUpListeners(root);

        return root;
    }

    private void setUpListeners(final View root){

        currentText = (TextView) root.findViewById(R.id.currentText);
        line1Text = (TextView) root.findViewById(R.id.line1Text);
        final TextView line1Value = (TextView) root.findViewById(R.id.line1Value);
        line2Text = (TextView) root.findViewById(R.id.line2Text);
        final TextView line2Value = (TextView) root.findViewById(R.id.line2Value);
        line3Text = (TextView) root.findViewById(R.id.line3Text);
        final TextView line3Value = (TextView) root.findViewById(R.id.line3Value);
        line4Text = (TextView) root.findViewById(R.id.line4Text);
        final TextView line4Value = (TextView) root.findViewById(R.id.line4Value);
        line5Text = (TextView) root.findViewById(R.id.line5Text);
        final TextView line5Value = (TextView) root.findViewById(R.id.line5Value);
        final EditText convertEdit = currentValue;


        LinearLayout line1 = (LinearLayout) root.findViewById(R.id.Line1Layout);
        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValueOrder(currentConvert,1);
                currentConvert = line1Text.getText().toString();
                line1Text.setText(currentText.getText().toString());
                currentText.setText(currentConvert);
                setCurrentConvertValues();
                String temp = line1Value.getText().toString();
                line1Value.setText(convertEdit.getText().toString());
                convertEdit.setText(temp);

            }
        });

        LinearLayout line2 = (LinearLayout) root.findViewById(R.id.Line2Layout);
        line2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValueOrder(currentConvert,2);
                currentConvert = line2Text.getText().toString();
                line2Text.setText(currentText.getText().toString());
                currentText.setText(currentConvert);
                setCurrentConvertValues();
                String temp = line2Value.getText().toString();
                line2Value.setText(convertEdit.getText().toString());
                convertEdit.setText(temp);
            }
        });

        LinearLayout line3 = (LinearLayout) root.findViewById(R.id.Line3Layout);
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValueOrder(currentConvert,3);
                currentConvert = line3Text.getText().toString();
                line3Text.setText(currentText.getText().toString());
                currentText.setText(currentConvert);
                setCurrentConvertValues();
                String temp = line3Value.getText().toString();
                line3Value.setText(convertEdit.getText().toString());
                convertEdit.setText(temp);
            }
        });

        LinearLayout line4 = (LinearLayout) root.findViewById(R.id.Line4Layout);
        line4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValueOrder(currentConvert,4);
                currentConvert = line4Text.getText().toString();
                line4Text.setText(currentText.getText().toString());
                currentText.setText(currentConvert);
                setCurrentConvertValues();
                String temp = line4Value.getText().toString();
                line4Value.setText(convertEdit.getText().toString());
                convertEdit.setText(temp);
            }
        });

        LinearLayout line5 = (LinearLayout) root.findViewById(R.id.Line5Layout);
        line5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValueOrder(currentConvert,5);
                currentConvert = line5Text.getText().toString();
                line5Text.setText(currentText.getText().toString());
                currentText.setText(currentConvert);
                setCurrentConvertValues();
                String temp = line5Value.getText().toString();
                line5Value.setText(convertEdit.getText().toString());
                convertEdit.setText(temp);
            }
        });
    }


    private void makeCalculations(Double editTextValue) {
        baseValue = editTextValue / currentConvertValue;

        ConvertValue[] orderedConvertValues = new ConvertValue[6];
        ConvertValue[] convertValues = {foot,meter,yard,miles,kilometers,inch};

        for (ConvertValue d:
             convertValues) {
            int pos = (int) d.getPos(); //we are sure that the value is perfectlz castable
            orderedConvertValues[pos] = d;
        }

        for (int i = 1; i < orderedConvertValues.length; i++) {
            double finalValue = baseValue * orderedConvertValues[i].getValue();
            int intFinal = (int) finalValue;
            String s = String.format("%.4f", finalValue);
            s = s.replace(',','.');
            if(finalValue > (double)intFinal){
                valueArray[i - 1].setText(s);
            }
            else if (finalValue == intFinal)
                valueArray[i - 1].setText(Integer.toString(intFinal));

        }
    }

    private void setCurrentConvertValues(){
        Context context = getContext();
        if(currentConvert .matches(context.getString(R.string.footText))){
            currentConvertValue = foot.getValue();
            foot.setPos(0);
        }
        
        else if(currentConvert.matches(context.getString(R.string.yardText))){
            currentConvertValue = yard.getValue();
            yard.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.milesText))){
            currentConvertValue = miles.getValue();
            miles.setPos(0);
        }

        else if(currentConvert .matches(context.getString(R.string.kilometersText))){
            currentConvertValue = kilometers.getValue();
            kilometers.setPos(0);
        }

        else if(currentConvert .matches(context.getString(R.string.inchText))){
            currentConvertValue = inch.getValue();
            inch.setPos(0);
        }

        else if(currentConvert .matches(context.getString(R.string.meterText))){
            currentConvertValue = meter.getValue();
            meter.setPos(0);
        }
    }



    private void setValueOrder(String s, int pos){
        Context context = getContext();
        
        if(s.matches(context.getString(R.string.footText))){
            foot.setPos(pos);
        }

        else if(s.matches(context.getString(R.string.yardText))){
            yard.setPos(pos);
        }

        else if(s.matches(context.getString(R.string.milesText))){
            miles.setPos(pos);
        }

        else if(currentConvert.matches(context.getString(R.string.kilometersText))){
            kilometers.setPos(pos);
        }

        else if(s.matches(context.getString(R.string.inchText))){
            inch.setPos(pos);
        }

        else if(s.matches(context.getString(R.string.meterText))){
            meter.setPos(pos);
        }
    }

    public static String getCurrentConvertPref(){ //the values returned are uniform for all languages to keep the SharedPreferences consistent

        if(currentConvert.matches(statContext.getString(R.string.footText))){
           return statContext.getString(R.string.footPref);
        }

        else if(currentConvert.matches(statContext.getString(R.string.yardText))){
            return statContext.getString(R.string.yardPref);
        }

        else if(currentConvert.matches(statContext.getString(R.string.milesText))){
            return statContext.getString(R.string.milesPref);
        }

        else if(currentConvert.matches(statContext.getString(R.string.kilometersText))){
            return statContext.getString(R.string.kilometerPref);
        }

        else if(currentConvert.matches(statContext.getString(R.string.inchText))){
            return statContext.getString(R.string.inchPref);
        }

        else if(currentConvert.matches(statContext.getString(R.string.meterText))){
            return statContext.getString(R.string.meterPref);
        }

        else return statContext.getString(R.string.nullPref);
    }

    public static void setFavoriteConvert(String fav){
        ConvertValue[] orderedConvertValues = new ConvertValue[6];
        ConvertValue[] convertValues = {foot,meter,yard,miles,kilometers,inch};
        TextView[] textLines = {currentText,
        line1Text,
        line2Text,
        line3Text,
        line4Text,
        line5Text};

        for (ConvertValue d:
                convertValues) {
            int pos = (int) d.getPos(); //we are sure that the value is perfectlz castable
            orderedConvertValues[pos] = d;
        }

        for (ConvertValue c : orderedConvertValues) {
            if(c.getPrefName().matches(fav)){

                currentValue.setText("");
                int cPos = c.getPos();
                orderedConvertValues[0].setPos(cPos);
                c.setPos(0);
                currentConvertValue = c.getValue();

                String temp = textLines[0].getText().toString();

                if(fav.matches(statContext.getString(R.string.footPref))){
                    currentConvert =  statContext.getString(R.string.footText);
                }

                else if(fav.matches(statContext.getString(R.string.yardPref))){
                    currentConvert =  statContext.getString(R.string.yardText);
                }

                else if(fav.matches(statContext.getString(R.string.milesPref))){
                    currentConvert =  statContext.getString(R.string.milesText);
                }

                else if(fav.matches(statContext.getString(R.string.kilometerPref))){
                    currentConvert =  statContext.getString(R.string.kilogramText);
                }

                else if(fav.matches(statContext.getString(R.string.inchPref))){
                    currentConvert =  statContext.getString(R.string.inchText);
                }

                else if(fav.matches(statContext.getString(R.string.meterPref))){
                    currentConvert =  statContext.getString(R.string.meterText);
                }

                textLines[0].setText(textLines[cPos].getText().toString());
                textLines[cPos].setText(temp);

                break;
            }
        }
    }
}