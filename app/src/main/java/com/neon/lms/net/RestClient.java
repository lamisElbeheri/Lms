package com.neon.lms.net;

import com.neon.lms.ResponceModel.NetAboutData;
import com.neon.lms.ResponceModel.NetBlogData;
import com.neon.lms.ResponceModel.NetBlogDetailData;
import com.neon.lms.ResponceModel.NetCartList;
import com.neon.lms.ResponceModel.NetCourseData;
import com.neon.lms.ResponceModel.NetCurrancyData;
import com.neon.lms.ResponceModel.NetFaqData;
import com.neon.lms.ResponceModel.NetForgot;
import com.neon.lms.ResponceModel.NetForumData;
import com.neon.lms.ResponceModel.NetMessageData;
import com.neon.lms.ResponceModel.NetMyPurchaseData;
import com.neon.lms.ResponceModel.NetNewsData;
import com.neon.lms.ResponceModel.NetOfferData;
import com.neon.lms.ResponceModel.NetSignUpData;
import com.neon.lms.ResponceModel.NetSingleCourseData;
import com.neon.lms.ResponceModel.NetSingleLession;
import com.neon.lms.ResponceModel.NetSponserData;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.ResponceModel.NetTeacherData;
import com.neon.lms.ResponceModel.NetTeacherDetailData;
import com.neon.lms.ResponceModel.NetTestimonialData;
import com.neon.lms.ResponceModel.NetUserProfile;
import com.neon.lms.ResponceModel.NetWhyusData;
import com.neon.lms.ResponceModel.TokenModel;
import com.neon.lms.util.Constants;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RestClient {

    @FormUrlEncoded
    @POST("/oauth/token")
    void passwordLogin(@Field("grant_type") String grant_type,
                       @Field("client_id") String client_id,
                       @Field("client_secret") String client_secret,
                       @Field("username") String username,
                       @Field("password") String password,
                       @Field("scope") String scope,
                       Callback<TokenModel> callback);


    @FormUrlEncoded
    @POST("/oauth/token")
    void socialLogin(@Field("grant_type") String grant_type,
                     @Field("client_id") String client_id,
                     @Field("client_secret") String client_secret,
                     @Field("scope") String scope,
                     @Field("provider") String provider,
                     @Field("access_token") String access_token,
                     Callback<TokenModel> callback);
    @FormUrlEncoded
    @POST("/oauth/token")
    void twitterLogin(@Field("grant_type") String grant_type,
                     @Field("client_id") String client_id,
                     @Field("client_secret") String client_secret,
                     @Field("scope") String scope,
                     @Field("provider") String provider,
                     @Field("access_token") String access_token,
                      @Field("secret") String secret,
                     Callback<TokenModel> callback);
    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/auth/logout")
    void logout(@Field("type") String type,
            Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/send-reset-link")
    void forgotPassword(
            @Field("email") String email,
            Callback<NetForgot> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/courses")
    void getCourseListApi(
            @Field("type") String type,
            Callback<NetCourseData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/single-course")
    void getSingleCourse(
            @Field("course_id") String course_id,
            Callback<NetSingleCourseData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/single-lesson")
    void getSingleLession(
            @Field("lesson_id") String lesson_id,
            Callback<NetSingleLession> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/teachers")
    void getTeacherListApi(
            @Field("type") String type,
            Callback<NetTeacherData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/single-teacher")
    void getTeacherDetail(
            @Field("teacher_id") String type,
            Callback<NetTeacherDetailData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-faqs")
    void getFaqListApi(
            @Field("type") String type,
            Callback<NetFaqData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/getnow")
    void getFreeCourse(
            @Field("course_id") String course_id,
            Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/add-to-cart")
    void addtocartApi(
            @Field("type") String type,
            @Field("item_id") String item_id,
            Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/remove-from-cart")
    void removeCart(
            @Field("type") String type,
            @Field("item_id") String item_id,
            Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-cart-data")
    void getCartList(
            @Field("type") String type,
            Callback<NetCartList> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/clear-cart")
    void clearCart(
            @Field("type") String type,
            Callback<NetSuccess> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/messages")
    void getMessageList(@Field("thread") String thread,
                        Callback<NetMessageData> callback);
 @FormUrlEncoded
    @POST(Constants.API_VERSION + "/reply-message")
    void replayMassage(@Field("thread_id") String thread_id,
                       @Field("message") String message,
                        Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-blog")
    void getBlogList(@Field("blog_id") String blog_id,
                     Callback<NetBlogData> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-blog")
    void getBlogDetailList(@Field("blog_id") String blog_id,
                           Callback<NetBlogDetailData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/forum")
    void getForumList(@Field("type") String type,
                      Callback<NetForumData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/create-discussion")
    void addDiscussion(@Field("title") String title,
                       @Field("body") String body,
                       @Field("chatter_category_id") String chatter_category_id,
                       Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/delete-response")
    void deleteForum(@Field("post_id") String post_id,
                     Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-page")
    void getAboutUs(
            @Field("page") String page,
            Callback<NetAboutData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/subscribe-newsletter")
    void getSubscribe(
            @Field("email") String email,
            Callback<NetSuccess> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/sponsors")
    void getSponserList(
            @Field("type") String type,
            Callback<NetSponserData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/testimonials")
    void getTestimonialList(
            @Field("type") String type,
            Callback<NetTestimonialData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/why-us")
    void getWhyUsList(
            @Field("type") String type,
            Callback<NetWhyusData> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/my-purchases")
    void getMyPurchase(
            @Field("type") String type,
            Callback<NetMyPurchaseData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/latest-news")
    void getNewsList(
            @Field("type") String type,
            Callback<NetNewsData> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/contact-us")
    void getContactUs(
            @Field("name") String name,
            @Field("email") String email,
            @Field("number") String number,
            @Field("message") String message,
            Callback<NetSuccess> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/signup-save")
    void signUp(
            @Field("first_name") String name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("city") String city,
//            @Field("phone") String phone,
//            @Field("gender") String gender,
//            @Field("address") String address,
//            @Field("city") String city,
//            @Field("pincode") String pincode,
//            @Field("state") String state,
//            @Field("country") String country,
//            @Field("avatar_type") String avatar_type,
//            @Field("avatar_location") String avatar_location,
            Callback<NetSuccess> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/signup-form")
    void getSignupForm(
            @Field("type") String type,
            Callback<NetSignUpData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/my-account")
    void getProfileData(
            @Field("type") String type,
            Callback<NetUserProfile> callback);


    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/configs")
    void getCurrancydata(
            @Field("type") String type,
            Callback<NetCurrancyData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/offers")
    void getOfferList(
            @Field("type") String type,
            Callback<NetOfferData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/apply-coupon")
    void applayPromo(
            @Field("type") String type,
            Callback<NetOfferData> callback);


}
