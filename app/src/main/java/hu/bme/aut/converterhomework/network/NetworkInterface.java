package hu.bme.aut.converterhomework.network;

import hu.bme.aut.converterhomework.currencyelements.MoneyResult;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Stealth on 2017. 11. 28..
 */

public interface NetworkInterface {
    @GET("/latest?base=USD")
    void getRatesToUsd(Callback<MoneyResult> callback);

    @GET("/latest?base=EUR")
    void getRatesToEur(Callback<MoneyResult> callback);

    @GET("/latest?base=HUF")
    void getRatesToHuf(Callback<MoneyResult> callback);

    @GET("/latest?base=GBP")
    void getRatesToGbp(Callback<MoneyResult> callback);
}
