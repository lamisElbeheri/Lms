package com.neon.lms.net;

import com.neon.lms.ResponceModel.NetAboutData;
import com.neon.lms.ResponceModel.NetBlogData;
import com.neon.lms.ResponceModel.NetBlogDataBlog;
import com.neon.lms.ResponceModel.NetCartList;
import com.neon.lms.ResponceModel.NetCourseData;
import com.neon.lms.ResponceModel.NetFaqData;
import com.neon.lms.ResponceModel.NetForgot;
import com.neon.lms.ResponceModel.NetForumData;
import com.neon.lms.ResponceModel.NetMessageData;
import com.neon.lms.ResponceModel.NetSingleCourseData;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.ResponceModel.NetTeacherData;
import com.neon.lms.ResponceModel.NetTeacherDetailData;
import com.neon.lms.ResponceModel.TokenModel;
import com.neon.lms.util.AppConstant;
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
    @POST(Constants.API_VERSION + "/messages")
    void getMessageList(@Field("thread") String thread,
                        Callback<NetMessageData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-blog")
    void getBlogList(@Field("blog_id") String blog_id,
                     Callback<NetBlogData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/forum")
    void getForumList(@Field("type") String type,
                      Callback<NetForumData> callback);

    @FormUrlEncoded
    @POST(Constants.API_VERSION + "/get-page")
    void getAboutUs(
            @Field("page") String page,
            Callback<NetAboutData> callback);


}
