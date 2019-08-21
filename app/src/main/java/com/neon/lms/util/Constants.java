package com.neon.lms.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.neon.lms.BaseAppClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * Created by rahul.patel on 11/29/2016.
 */
public class Constants {

    //--------------------------------Common CustomConstants----------------------------------------------
    public static final String FOLDER_NAME = "Lms";

    public static String BASE_URL = "https://sample.neonlms.com";
    public static final String API_VERSION = "/api/v1";
    public static final String DEVELOPER_KEY = "AIzaSyAsgtOvy1dr8jcVSUFqy63wB2X8KW4TFT0";

    //Broadcast CustomConstants
    public static final int CONNECTION_TIMEOUT = 30000;
    public static final String CLIENT_SECRET = "o03D6ITBzOgk9TK0j4sUTsmEDPgQLnFEVs1ZZOgZ";
    public static final String CLIENT_ID = "4";

    public static final int PROGRESS_IMAGE = 1;
    public static final int PROGRESS_TEXT = 2;

    public enum SignInSignUpErrorType {
        EMPTY_FNAME,
        EMPTY_NAME,
        EMPTY_USERTYPE,
        EMPTY_LANGUAGE,
        EMPTY_LNAME,
        EMPTY_MOBILE,
        INVALID_MOBILE,
        INVALID_EMAIL,
        EMPTY_EMAIL,
        EMPTY_USERNAME,
        INVALID_PASSWORD,
        INVALID_CONFORM_PASS,
        EMPTY_CONFORM_PASS,
        EMPTY_PASSWORD,
        EMAIL_OR_MOBILE,
        EMPTY_ADDRESS,
        UNCHECHED,
        NONE
    }

    public enum OtpButtonClickType {
        OTP,
        RESEND
    }

    public static final int LIMIT = 20;


    /*Usser Type*/
    public static final String SUPER_ADMIN = "1";
    public static final String ADMIN = "2";
    public static final String SUPER_MARKET = "3";
    public static final String AREA_ADMIN = "4";
    public static final String FARMER = "5";
    public static final String TRANSPORT_SERVICE = "6";
    public static final String END_USER = "7";

    public static final int MULTI_SELCETION = 9;
    public static final int ROW_CLICK = 0;
    public static final int ROW_BTN_CLICK = 1;
    public static final int DELETE = 3;
    public static final int BACK = 4;
    public static final int SETTING = 5;
    public static final int PLUSH = 6;
    public static final int MINUS = 7;
    public static final int EDIT = 8;
    public static final int MEASUREMENT = 10;
    public static final int ADD_TO_CART = 11;
    public static final int REPEAT = 12;
    public static final int REPORT = 13;
    public static final int PRIMARY = 14;
    public static final int ROW_EDIT_CHANGE = 15;
    public static final int ROW_LONG_CLICK = 16;
    public static final int UNSELECTED = 17;
    public static final int SELECTED = 18;
    public static final int SPINNER_CLICK = 19;
    public static final int UPDATE = 20;
    public static final int CLEAR_FILTER = 21;

    //Drawer Type
    public static final int ACCOUNT = 10;
    public static final int HOME = 0;
    public static final int BLOG = 1;
    public static final int COURSE = 2;
    public static final int FORUMS = 3;
    public static final int FAQ = 4;
    public static final int CONTACT = 5;
    public static final int ABOUTUS = 6;
    public static final int LOGIN = 7;
    public static final int FEEDBACK = 8;
    public static final int LANGUAGE = 9;
    public static final int MYPURCHASE = 11;
    public static final int LOGOUT = 12;



    /*Home Page Constant*/
    public static final String NEWS = "1";
    public static final String TRANDING_COURSE = "2";
    public static final String FEATURED_COURSE = "3";
    public static final String TESTIMONIAL = "4";
    public static final String TEACHER = "5";
    public static final String QUESTION = "6";
    public static final String FAQ_QUESTION = "7";
    public static final String BROWSE_COURSE = "8";
    public static final String WHY_US = "9";
    public static final String SPONSORS = "10";
    public static final String CONTACT_US = "11";


    public static final int ENGLISH = 1;
    public static final int AREBIC = 2;

    public static final int CATEGORY_TYPE = 1;
    public static final int PRODUCT_TYPE = 2;
    public static final int UOM_TYPE = 3;
    public static final int WHOLE_UOM_TYPE = 4;


    //    FILE CONSTANTS

    public static final int ATTACHMENT_TYPE_IMAGE = 1;
    public static final int ATTACHMENT_TYPE_VIDEO = 2;
    public static final int ATTACHMENT_TYPE_DOCUMENT = 3;
    public static final int ATTACHMENT_TYPE_AUDIO = 4;


    //    permission
    //    permission
    public static final int PERMISSION_WRITE_READ_CARD = 0x101;
    public static final int PERMISSION_CAMERA = 0x102;
    public static final int PERMISSION_FILE_MANAGER = 0x103;
    public static final int PERMISSION_AUDIO = 0x104;
    public static final int PERMISSION_LOCATION = 0x105;
    public static final int PERMISSION_READ_CONTACT = 0x106;

    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    public static final int REQUEST_CODE_FILE_PICK = 0x4;
    public static final int REQUEST_OPEN_SELECTED = 0x5;
    public static final int LOGIN_REQUEST_CODE = 0x6;
    public static final int REQUEST_CODE_LOCATION = 0x7;
    public static final int REQUEST_CODE_FILTER = 0x8;
    public static final int REQUEST_CODE_CHECK_OUT_BACK = 0x9;
    public static final int REQUEST_CODE_LOGIN_OTP = 0x10;
    public static final int REQUEST_CODE_FORGOT_PASSWORD = 0x11;
    public static final int REQUEST_CODE_PICK_CONTACT = 0x12;
    public static final int REQUEST_PLACE_PICKER = 0x13;
    public static final int REQUEST_CHANGE_ADDRESS = 0x14;
    public static final int REQUEST_ADD_INVENTORY = 0x15;


    //User CustomConstants
    public static final int USER_TYPE_ADMIN = 1;
    public static final int USER_TYPE_CORPORATE = 2;
    public static final int USER_TYPE_ADVISORE = 3;
    public static final int USER_TYPE_INVESTORE = 4;


    //Notification
    public static final int interruptNotificationID = 1;

    //Demo type
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_DROPDOWN_VIEW = 7;
    public static final int TYPE_DROPDOWN_VIEW_WITH_TEXT = 8;
    public static final int TYPE_DATE_VIEW = 9;
    public static final int TYPE_SWITCH_VIEW = 10;
    public static final int TYPE_ATTACHMENT = 11;
    public static final int TYPE_LOCATION_VIEW = 12;
    public static final int TYPE_BTN_VIEW = 13;
    public static final int TYPE_DROPDOWN_MULTI = 14;
    public static final int TYPE_FROM_TO_VIEW = 15;
    public static final int TYPE_SELECT_CITY_VIEW = 16;

    public static final String PROFILE_PIC_FILE_NAME = "profilePic.jpg";

    public static final String GET_TYPE_DEAL = "DEAL";
    public static final String GET_TYPE_INTEREST = "INTEREST";
    public static final String GET_LOCATION = "LOCATION";
    public static final String GET_FUND_TYPE = "FUND_TYPE";
    public static final String GET_CURRENCY = "CURRENCY";
    public static final String GET_PROJECT_LIFE = "PROJECT_LIFE";
    public static final String GET_INDUSTRY = "INDUSTRY";
    public static final String GET_ORGANIZATION = "ORGANIZATION";
    public static final String GET_INVESTOR_TYPE = "INVESTOR_TYPE";
    public static final String GET_SUB_TYPE_FUND = "FUND";
    public static final String GET_SUB_TYPE_BUY_BUSINESS = "BUY_BUSINESS";
    public static final String GET_SUB_TYPE_SALE_BUSINESS = "SALE_BUSINESS";

    public static final int IMAGE = 1;
    public static final int VIDEO = 2;
    public static final int DOCUMENT = 3;
    public static final int AUDIO = 4;

    public static final String FOLDER_TOPIC_IMAGE = "topic-image";
    public static final String FOLDER_TOPIC_DOCUMENT = "topic-docs";

    public static final int INPUT_TEXT = 1;
    public static final int INPUT_NUMBER = 2;
    public static final int INPUT_PASSWORD = 3;


    /*order status*/
    public static final int PENDING = 1;
    public static final int ORDER_PACKED = 2;
    public static final int AWAITING_PICKUP = 3;
    public static final int OUT_OF_DELIVERY = 4;
    public static final int DELIVERED = 5;
    public static final int CANCELLED = 6;
    public static final int DECLINED = 7;
    public static final int REFUNDED = 8;
    public static final int DISPUTED = 9;
    public static final int VERIFICATION_REQUIRED = 10;

    //location type
    public static final int COUNTRY = 1;
    public static final int STATE = 2;
    public static final int CITY = 3;


    public static String getPath(Activity context, Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public static SimpleDateFormat DF_DDMMYY = new SimpleDateFormat("dd-MM-yyyy");


    //Social Follow Us Url
    public static String FB_URL = "urbantrend.co.in";
    public static String TWITTER_URL = "urbantrend.co.in";
    public static String GPLUS_URL = "urbantrend.co.in";
    public static String LINKED_IN_URL = "urbantrend.co.in";
    public static String PINTEREST_URL = "https://in.pinterest.com/urbantrendcoin";
    public static String INSTAGRAM_URL = "https://www.instagram.com/urbantrend.co.in";
    public static String TUMBLER_URL = "http://blogurbantrend.tumblr.com/";
    public static String YOU_TUBE = "https://www.youtube.com/channel/UCJPvBVqn8Yv-k27hQ2hAWJQ";


    /*wallet item Consatnt */
    public static final int LABLE_VIEW = 0;
    public static final int LIST_VIEW = 1;
    public static final int WALLET_VIEW = 2;
    public static final int HEADER_VIEW = 3;
    public static final int PROFILE_IMAGE_VIEW = 4;
    public static final int PROFILE_EDITVIEW = 5;
    public static final int SELECTION_VIEW = 6;
    public static final int ATTACHMENT_VIEW = 7;

    public static final int VT_LIST = 0;
    public static final int VT_MONEY_CLICK = 1;

    /*Request Code*/
    public static final int REQUEST_CODE_OPERATOR = 0x1;

    //    Address types
    public static final int ADDRESS_TYPE_OFFICE = 1;
    public static final int ADDRESS_TYPE_HOME = 2;
    public static final int ADDRESS_TYPE_WORK = 3;
    public static final int ADDRESS_TYPE_FARM = 4;


    //Master Code
    public static final String LANG = "LANGUAGE";
    public static final String UOM = "UOM";

    //    rent types
    public static final int RENT_TYPE_PER_HOUR = 1;
    public static final int RENT_TYPE_PER_DISTANCE = 2;

    //    user App Status types
    public static final int APP_STATUS_TYPE_NOT_INSTALLED = 1;
    public static final int APP_STATUS_TYPE_INSTALLED = 2;
    public static final int APP_STATUS_TYPE_UN_INSTALLED = 3;

}
