package hu.bme.aut.converterhomework.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import hu.bme.aut.converterhomework.R;
import hu.bme.aut.converterhomework.currencyelements.CurrencyConvertValue;
import hu.bme.aut.converterhomework.currencyelements.MoneyResult;
import hu.bme.aut.converterhomework.network.NetworkInterface;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.Callback;
import retrofit.client.Response;

/**
 * Created by Stealth on 2017. 11. 21..
 */

public class CurrencyFragment extends Fragment {
    private static String currentConvert; //string to see which measure we convert from
    private static CurrencyConvertValue currentCurrency;

    private static CurrencyConvertValue huf;
    private static CurrencyConvertValue eur;
    private static CurrencyConvertValue usd;
    private static CurrencyConvertValue gbp;

    private TextView[] valueArray = new TextView[3];

    private static Context statContext;

    private static TextView currentText;
    private static TextView line1Text;
    private static TextView line2Text;
    private static TextView line3Text;

    private static EditText currentValue;

    private static boolean noConnection = true;

    public static boolean getNoConnection() {return noConnection;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://data.fixer.io/api")
                    .build();

        NetworkInterface service = restAdapter.create(
                NetworkInterface.class);

        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();



        //setCurrencyRates(service);

        currentConvert = this.getString(R.string.eurText);

        final View root = inflater.inflate(R.layout.currency_layout,container,false);

        statContext = getContext();

        valueArray[0] = (TextView) root.findViewById(R.id.line1Value);
        valueArray[1] = (TextView) root.findViewById(R.id.line2Value);
        valueArray[2] = (TextView) root.findViewById(R.id.line3Value);

        currentValue = (EditText) root.findViewById(R.id.currencyCurrentValue);
        currentValue.setEnabled(false);

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
                    //setCurrentConvertValues();
                    //Toast.makeText(root.getContext(),Double.toString(baseValue),Toast.LENGTH_SHORT).show();
                    makeCalculations(Double.parseDouble(currentValue.getText().toString()));
                    valuesLayout.setVisibility(LinearLayout.VISIBLE);

                }
                else{
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
    }


    private void makeCalculations(Double editTextValue) {

        CurrencyConvertValue[] orderedConvertValues = new CurrencyConvertValue[4];
        CurrencyConvertValue[] convertValues = {usd,eur,huf,gbp};

        for (CurrencyConvertValue d:
                convertValues) {
            int pos = d.getPos(); //we are sure that the value is perfectly castable
            orderedConvertValues[pos] = d;
        }

        for (int i = 1; i < orderedConvertValues.length; i++) {
            double finalValue = 0;

            if(orderedConvertValues[i].getName() .matches( statContext.getString(R.string.eurText))) {
                finalValue = editTextValue*currentCurrency.getToEur();
            }

            else if (orderedConvertValues[i].getName() .matches( statContext.getString(R.string.usdText))){
                finalValue = editTextValue*currentCurrency.getToUsd();
            }

            else if (orderedConvertValues[i].getName() .matches( statContext.getString(R.string.gbpText))){
                finalValue = editTextValue*currentCurrency.getToGbp();
            }

            else if (orderedConvertValues[i].getName() .matches( statContext.getString(R.string.hufText))){
                finalValue = editTextValue*currentCurrency.getToHuf();
            }

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
        if(currentConvert .matches( context.getString(R.string.hufText))){
            currentCurrency = huf;
            huf.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.gbpText))){
            currentCurrency = gbp;
            gbp.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.usdText))){
            currentCurrency = usd;
            usd.setPos(0);
        }

        else if(currentConvert .matches( context.getString(R.string.eurText))){
            currentCurrency = eur;
            eur.setPos(0);
        }
    }



    private void setValueOrder(String s, int pos){
        Context context = getContext();

        if(s .matches( context.getString(R.string.hufText))){
            huf.setPos(pos);
        }

        else if(s .matches( context.getString(R.string.gbpText))){
            gbp.setPos(pos);
        }

        else if(s .matches( context.getString(R.string.usdText))){
            usd.setPos(pos);
        }

        else if(currentConvert .matches( context.getString(R.string.eurText))){
            eur.setPos(pos);
        }
    }

    private void setCurrencyRates(NetworkInterface service){

        service.getRatesToUsd(
                new Callback<MoneyResult>() {

                    @Override
                    public void success(MoneyResult moneyResult, Response response) {
                        usd = new CurrencyConvertValue(statContext.getString(R.string.usdText),
                                1,
                                moneyResult.getRates().getEUR(),
                                moneyResult.getRates().getGBP(),
                                moneyResult.getRates().getHUF(),
                                2);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        currentValue.setEnabled(false);
                        Toast.makeText(getContext(),
                                "error: "+error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        service.getRatesToEur(
                new Callback<MoneyResult>() {

                    @Override
                    public void success(MoneyResult moneyResult, Response response) {
                        eur = new CurrencyConvertValue(statContext.getString(R.string.eurText),
                                moneyResult.getRates().getUSD(),
                                1,
                                moneyResult.getRates().getGBP(),
                                moneyResult.getRates().getHUF(),
                                0);
                        currentCurrency = eur;
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        currentValue.setEnabled(false);
                        Toast.makeText(getContext(),
                                "error: "+error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        service.getRatesToHuf(
                new Callback<MoneyResult>() {
                    @Override
                    public void success(MoneyResult moneyResult, Response response) {
                        huf = new CurrencyConvertValue(statContext.getString(R.string.hufText),
                                moneyResult.getRates().getUSD(),
                                moneyResult.getRates().getEUR(),
                                moneyResult.getRates().getGBP(),
                                1,
                                1);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        currentValue.setEnabled(false);
                        Toast.makeText(getContext(),
                                "error: "+error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        service.getRatesToGbp(
                new Callback<MoneyResult>() {

                    @Override
                    public void success(MoneyResult moneyResult, Response response) {
                        gbp = new CurrencyConvertValue(statContext.getString(R.string.gbpText),
                                moneyResult.getRates().getUSD(),
                                moneyResult.getRates().getEUR(),
                                1,
                                moneyResult.getRates().getHUF(),
                                3);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        currentValue.setEnabled(false);
                        Toast.makeText(getContext(),
                                "error: "+error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public static String getCurrentConvertPref(){ //the values returned are uniform for all languages to keep the SharedPreferences consistent

        if(currentConvert .matches( statContext.getString(R.string.eurText))){
            return statContext.getString(R.string.eurText);
        }

        else if(currentConvert .matches( statContext.getString(R.string.hufText))){
            return statContext.getString(R.string.hufText);
        }

        else if(currentConvert .matches( statContext.getString(R.string.gbpText))){
            return statContext.getString(R.string.gbpText);
        }

        else if(currentConvert .matches( statContext.getString(R.string.usdText))){
            return statContext.getString(R.string.usdText);
        }

        else return statContext.getString(R.string.nullPref);
    }

    public static void setFavoriteConvert(String fav){
        CurrencyConvertValue[] orderedConvertValues = new CurrencyConvertValue[4];
        CurrencyConvertValue[] convertValues = {huf, usd, gbp, eur};
        TextView[] textLines = {currentText,
                line1Text,
                line2Text,
                line3Text,
                };

        for (CurrencyConvertValue d:
                convertValues) {
            int pos = d.getPos(); //we are sure that the value is perfectlz castable
            orderedConvertValues[pos] = d;
        }

        for (CurrencyConvertValue c : orderedConvertValues) {
            if(c.getName().matches(fav)){

                currentValue.setText("");
                int cPos = c.getPos();
                orderedConvertValues[0].setPos(cPos);
                c.setPos(0);
                currentCurrency = c;

                String temp = textLines[0].getText().toString();
                currentConvert = c.getName();
                textLines[0].setText(textLines[cPos].getText().toString());
                textLines[cPos].setText(temp);

                break;
            }
        }
    }
}
