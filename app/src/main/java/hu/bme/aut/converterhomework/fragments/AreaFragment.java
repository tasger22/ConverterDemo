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

public class AreaFragment extends Fragment {
    private double baseValue = 0; //always in meters to make calculating values easy
    private static String currentConvert; //string to see which measure we convert from

    private static ConvertValue sqmeter ;
    private static ConvertValue sqkmmeter;
    private static ConvertValue sqfoot;
    private static ConvertValue acre;
    private static ConvertValue hectar;

    private static double currentConvertValue = 1; //default is one because 1 sqmeter is 1 sqmeter
    private TextView[] valueArray = new TextView[4];

    private static Context statContext;

    private static TextView currentText;
    private static TextView line1Text;
    private static TextView line2Text;
    private static TextView line3Text;
    private static TextView line4Text;

    private static EditText currentValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sqmeter = new ConvertValue(1,0,getString(R.string.sqMeterPref));
        sqkmmeter = new ConvertValue(0.000001,1,getString(R.string.sqKmeterPref));
        sqfoot = new ConvertValue(10.76,2,getString(R.string.sqFootPref));
        acre = new ConvertValue(0.0002471,3,getString(R.string.acrePref));
        hectar = new ConvertValue(0.0001,4,getString(R.string.hectarPref));
       
        currentConvert = this.getString(R.string.sqMeterText);

        final View root = inflater.inflate(R.layout.area_layout,container,false);

        statContext = getContext();

        valueArray[0] = (TextView) root.findViewById(R.id.line1Value);
        valueArray[1] = (TextView) root.findViewById(R.id.line2Value);
        valueArray[2] = (TextView) root.findViewById(R.id.line3Value);
        valueArray[3] = (TextView) root.findViewById(R.id.line4Value);


        currentValue = (EditText) root.findViewById(R.id.areaCurrentValue);
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

    }


    private void makeCalculations(Double editTextValue) {
        baseValue = editTextValue / currentConvertValue;

        ConvertValue[] orderedConvertValues = new ConvertValue[5];
        ConvertValue[] convertValues = {sqmeter,sqkmmeter,sqfoot,acre,hectar};

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
        if(currentConvert .matches( context.getString(R.string.sqMeterText))){
            currentConvertValue = sqmeter.getValue();
            sqmeter.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.sqKmeterText))){
            currentConvertValue = sqkmmeter.getValue();
            sqkmmeter.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.sqFootText))){
            currentConvertValue = sqfoot.getValue();
            sqfoot.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.acreText))){
            currentConvertValue = acre.getValue();
            acre.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.hectarText))){
            currentConvertValue = hectar.getValue();
            hectar.setPos(0);
        }

    }

    private void setValueOrder(String s, int pos){
        Context context = getContext();

        if(s .matches( context.getString(R.string.sqMeterText))){
            sqmeter.setPos(pos);
        }

        else if(s .matches( context.getString(R.string.sqKmeterText))){
            sqkmmeter.setPos(pos);
        }

        else if(s .matches( context.getString(R.string.sqFootText))){
            sqfoot.setPos(pos);
        }

        else if(currentConvert .matches( context.getString(R.string.acreText))){
            acre.setPos(pos);
        }

        else if(s .matches( context.getString(R.string.hectarText))){
            hectar.setPos(pos);
        }

    }

    public static String getCurrentConvertPref(){
        if(currentConvert .matches( statContext.getString(R.string.sqMeterText))){
            return statContext.getString(R.string.sqMeterPref);
        }

        else if(currentConvert .matches( statContext.getString(R.string.sqKmeterText))){
            return statContext.getString(R.string.sqKmeterPref);
        }

        else if(currentConvert .matches( statContext.getString(R.string.sqFootText))){
            return statContext.getString(R.string.sqFootPref);
        }

        else if(currentConvert .matches( statContext.getString(R.string.acreText))){
            return statContext.getString(R.string.acrePref);
        }

        else if(currentConvert .matches( statContext.getString(R.string.hectarText))){
            return statContext.getString(R.string.hectarPref);
        }

        else return statContext.getString(R.string.nullPref);
    }

    public static void setFavoriteConvert(String fav){
        ConvertValue[] orderedConvertValues = new ConvertValue[5];
        ConvertValue[] convertValues = {sqmeter , sqkmmeter, sqfoot, acre, hectar};
        TextView[] textLines = {currentText,
                line1Text,
                line2Text,
                line3Text,
                line4Text,
                };

        for (ConvertValue d:
                convertValues) {
            int pos = (int) d.getPos(); //we are sure that the value is perfectly castable
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

                if(fav.matches( statContext.getString(R.string.sqMeterPref))){
                    currentConvert =  statContext.getString(R.string.sqMeterText);
                }

                else if(fav.matches( statContext.getString(R.string.sqKmeterPref))){
                    currentConvert =  statContext.getString(R.string.sqKmeterText);
                }

                else if(fav.matches( statContext.getString(R.string.acrePref))){
                    currentConvert =  statContext.getString(R.string.acreText);
                }

                else if(fav.matches( statContext.getString(R.string.hectarPref))){
                    currentConvert =  statContext.getString(R.string.hectarText);
                }

                else if(fav.matches( statContext.getString(R.string.sqFootPref))){
                    currentConvert =  statContext.getString(R.string.sqFootText);
                }

                textLines[0].setText(textLines[cPos].getText().toString());
                textLines[cPos].setText(temp);

                break;
            }
        }
    }
}
