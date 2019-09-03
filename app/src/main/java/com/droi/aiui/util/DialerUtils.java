/* * Copyright (C) 2014 The Android Open Source Project * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */package com.droi.aiui.util;import android.content.Context;import android.content.Intent;import android.net.Uri;import android.util.Log;import com.google.i18n.phonenumbers.NumberParseException;import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;import com.google.i18n.phonenumbers.PhoneNumberUtil;import com.google.i18n.phonenumbers.Phonenumber;import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;import java.util.Locale;/** * */public class DialerUtils {    private static final String TAG = "DialerUtils";    private static PhoneNumberUtil mPhoneNumberUtil = PhoneNumberUtil.getInstance();    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();    /**     * 拨打电话     * @param context     * @param number     */    public static void doCall(Context context, String number){        Log.d(TAG,"doCall---->phoneNumber = "+number);        if (number.toString().trim() == null || number.toString().trim().equals("")) {            Log.d(TAG,"电话号码为空！");            return;        } else if (number.toString().trim() != null && !(number.toString().trim().equals(""))) {            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.toString().trim()));            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);            try{                context.startActivity(intent);            }catch (SecurityException e){                e.printStackTrace();            }        }    }    /**     * 发短信     * @param context     * @param phoneNumber     */    public static void sendMms(Context context, String phoneNumber) {        Log.d(TAG,"sendMms---->phoneNumber = "+phoneNumber);        Uri uri = Uri.parse("smsto:" + phoneNumber);        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        try{            context.startActivity(intent);        }catch (SecurityException e){            Log.d(TAG,"短信应用打开失败！");            e.printStackTrace();        }    }    // 获取国家码 “CN”    public static String getCurrentCountryIso(Context context) {        // The {@link CountryDetector} should never return null so this is safe to return as-is.        return CountryDetector.getInstance(context).getCurrentCountryIso();    }    //获取归属地信息    public static String getLocation(Context context,String phoneNumber) {        final PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();        Phonenumber.PhoneNumber structuredNumber = getStructedNumber(context,phoneNumber);        Locale locale = context.getResources().getConfiguration().locale;        return geocoder.getDescriptionForNumber(structuredNumber,locale);    }    public static Phonenumber.PhoneNumber getStructedNumber(Context context,String phoneNumber) {        try {            final Phonenumber.PhoneNumber structuredNumber =                    mPhoneNumberUtil.parse(phoneNumber,getCurrentCountryIso(context));            return structuredNumber;        } catch (NumberParseException e) {            return  null;        }    }    //获取运营商信息    public static String getCarrier(Context context,String phoneNumber) {        Phonenumber.PhoneNumber structedNumber = getStructedNumber(context,phoneNumber);        String carrier = carrierMapper.getNameForNumber(structedNumber,Locale.US);        if(carrier.equals("China Mobile")){            return "中国移动";        }else if(carrier.equals("China Unicom")){            return "中国联通";        }else if(carrier.equals("China Telecom")){            return "中国电信";        }else{            return "未知";        }    }}