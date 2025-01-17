package com.droi.aiui.util;

import android.content.Context;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * Created by cuixiaojun on 18-2-7.
 */

public class CountryDetector {
    private static final String TAG = CountryDetector.class.getSimpleName();
    private static CountryDetector sInstance;
    private final Context mContext;
    private final TelephonyManager mTelephonyManager;
    private final LocationManager mLocationManager;
    private final LocaleProvider mLocaleProvider;

    private final String DEFAULT_COUNTRY_ISO = "CN";

    public static class LocaleProvider {
        public Locale getDefaultLocale() {
            return Locale.getDefault();
        }
    }

    private CountryDetector(Context context) {
        this(context,(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE),
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE),
                new LocaleProvider());
    }
    private CountryDetector(Context context, TelephonyManager telephonyManager,
                            LocationManager locationManager, LocaleProvider localeProvider) {
        mTelephonyManager = telephonyManager;
        mLocationManager = locationManager;
        mLocaleProvider = localeProvider;
        mContext = context;
    }
    public  static CountryDetector getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new CountryDetector(context);
        }
        return sInstance;
    }

    public String getCurrentCountryIso() {
        String result = null;
        if (isNetworkCountryCodeAvailable()) {
            result = getNetworkBasedCountryIso();
            Log.d(TAG," getNetworkBasedCountryIso");
        }
        if (TextUtils.isEmpty(result)) {
            result = getSimBasedCountryIso();
            Log.d(TAG,"getSimBasedCountryIso");
        }
        if (TextUtils.isEmpty(result)) {
            result = getLocaleBasedCountryIso();
            Log.d(TAG,"getLocaleBasedCountryIso");
        }
        if (TextUtils.isEmpty(result)) {
            result = DEFAULT_COUNTRY_ISO;
            Log.d(TAG,"DEFAULT_COUNTRY_ISO");
        }
        Log.d(TAG," result ==  " + result);
        return result.toUpperCase(Locale.US);
    }

    /**
     * @return the country code of the current telephony network the user is connected to.
     */
    private String getNetworkBasedCountryIso() {
        return mTelephonyManager.getNetworkCountryIso();
    }

    /**
     * @return the country code of the SIM card currently inserted in the device.
     */
    private String getSimBasedCountryIso() {
        return mTelephonyManager.getSimCountryIso();
    }

    /**
     * @return the country code of the user's currently selected locale.
     */
    private String getLocaleBasedCountryIso() {
        Locale defaultLocale = mLocaleProvider.getDefaultLocale();
        if (defaultLocale != null) {
            return defaultLocale.getCountry();
        }
        return null;
    }

    private boolean isNetworkCountryCodeAvailable() {
        // On CDMA TelephonyManager.getNetworkCountryIso() just returns the SIM's country code.
        // In this case, we want to ignore the value returned and fallback to location instead.
        return mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM;
    }
}