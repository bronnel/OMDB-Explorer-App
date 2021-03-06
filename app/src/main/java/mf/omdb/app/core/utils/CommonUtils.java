package mf.omdb.app.core.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;

import mf.omdb.app.core.interfaces.OmdbApiInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonUtils {

	private static final String TAG = CommonUtils.class.getSimpleName();
	private static OmdbApiInterface omdbApiInterface;

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = activity.getCurrentFocus();
		if (view != null && view.getWindowToken() != null) {
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static boolean isNetworkAvailable(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			if (activeNetwork != null) {
				return activeNetwork.isConnectedOrConnecting();
			}
		}
		return false;
	}

	public static boolean isActualYear(String year) {
		Calendar calendar = Calendar.getInstance();
		return year.contains(calendar.get(Calendar.YEAR) + "");
	}

	public static OmdbApiInterface getOmdbApiInterface() {
		if (omdbApiInterface == null) {
			Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_URL)
					.addConverterFactory(GsonConverterFactory.create()).build();

			omdbApiInterface = retrofit.create(OmdbApiInterface.class);
		}
		return omdbApiInterface;
	}
}