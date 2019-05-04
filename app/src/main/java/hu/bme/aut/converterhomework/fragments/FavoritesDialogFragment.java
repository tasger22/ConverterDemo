package hu.bme.aut.converterhomework.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import hu.bme.aut.converterhomework.R;

/**
 * Created by Stealth on 2017. 11. 28..
 */

public class FavoritesDialogFragment extends AppCompatDialogFragment{

    public static final String TAG = "FavoritesDialogFragment";
    private FavoritePickedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(getActivity() instanceof FavoritePickedListener)) {
            throw new RuntimeException("The activity does not implement the" +
                    "FavoritePickedListener interface");
        }

        listener = (FavoritePickedListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.action_favorites))
                .setView(getFavoriteView())
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getFavoriteView() {

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.favorites_fragment_layout, null);
        String[] prefKeyBase = {"length_","weight_","area_","currency_"};
            SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
            for (final String keyBase: prefKeyBase) {
                int i = 0;
                while(!sp.getString(keyBase + i,"null").matches("null")){
                    final String convertText = sp.getString(keyBase + i,"null");
                    final String[] splitKey = keyBase.split("_");

                    View newLayout = View.inflate(getContext(),R.layout.fav_list_element_layout,null);

                    TextView firstTv = (TextView) newLayout.findViewById(R.id.firstTv);
                    TextView secondTv = (TextView) newLayout.findViewById(R.id.secondTv);

                    firstTv.setText(splitKey[0]);
                    secondTv.setText(convertText);

                    newLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.OnFavoritePicked(keyBase,convertText);
                            dismiss();
                        }
                    });

                    LinearLayout listLayout = (LinearLayout) view.findViewById(R.id.listLayout);
                    listLayout.addView(newLayout);

                    ++i;
                }
            }

        return view;

    }

    public interface FavoritePickedListener{
        public void OnFavoritePicked(String tab, String convertText);
    }
}
