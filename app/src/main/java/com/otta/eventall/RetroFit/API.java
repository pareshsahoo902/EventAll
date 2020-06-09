package com.otta.eventall.RetroFit;

import com.otta.eventall.Activities.AddBusiness.Methods.AddNewBusiness_REQUEST;
import com.otta.eventall.Activities.AddBusiness.Methods.AddNewBusiness_RESPONSE;
import com.otta.eventall.Activities.Login.Methods.Login_REQUEST;
import com.otta.eventall.Activities.Login.Methods.Login_RESPONSE;
import com.otta.eventall.Activities.Login.Methods.Logout_RESPONSE;
import com.otta.eventall.Activities.Search.Methods.Search_REQUEST;
import com.otta.eventall.Activities.Search.Methods.Search_RESPONSE;
import com.otta.eventall.Activities.SignUp.Methods.SignUp_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.SignUp_RESPONSE;
import com.otta.eventall.Activities.SignUp.Methods.UserDetails_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.UserDetails_RESPONSE;
import com.otta.eventall.Activities.SignUp.Methods.UserProfilePicture_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.UserProfilePicture_RESPONSE;
import com.otta.eventall.Activities.ViewAll.Methods.View_REQUEST;
import com.otta.eventall.Activities.ViewAll.Methods.View_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.AddressUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.ContactPersonUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.DescUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.LandlineUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.MobileNoUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.Open24hrUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.OpenHoursUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.PhotosUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.PhotosUpdate_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.SubCategoryUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.TitleUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Methods.AllBusinesses_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_RESPONSE;
import com.otta.eventall.AllCategory_RESPONSE;
import com.otta.eventall.UserInfo_RESONSE;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    String BASE_URL = "http://eventall.in/api/ea/user/";

    String BUSINESS_URL = "http://eventall.in/api/ea/user/b/";

    String CATEGORY_BASE_URL = "http://eventall.in/api/ea/";

    @GET("categories_list")
    @Headers({"Content-Type: application/json"})
    Call<AllCategory_RESPONSE> getAllCategories();

    @POST("signup")
    @Headers({"Content-Type: application/json", "X-Requested-With: XMLHttpRequest"})
    Call<SignUp_RESPONSE> SignUp(@Body SignUp_REQUEST request);

    @POST("login")
    @Headers({"Content-Type: application/json" , "X-Requested-With: XMLHttpRequest"})
    Call<Login_RESPONSE> Login(@Body Login_REQUEST request);

    @POST("logout")
    @Headers({"Content-Type: application/json"})
    Call<Logout_RESPONSE> logout(@Header("Authorization") String token);

    @POST("info")
    @Headers({"Content-Type: application/json"})
    Call<UserInfo_RESONSE> getUserInfo(@Header("Authorization") String token);


    @POST("update_basic_info")
    @Headers({"Content-Type: application/json"})
    Call<UserDetails_RESPONSE> Save_UserDetails(@Header("Authorization") String token, @Body UserDetails_REQUEST request);

    @POST("update_photo")
    @Headers({"Content-Type: application/json"})
    Call<UserProfilePicture_RESPONSE> Save_UserProfilePicture(@Header("Authorization") String token, @Body UserProfilePicture_REQUEST request);


    @POST("new_business")
    @Headers({"Content-Type: application/json"})
    Call<AddNewBusiness_RESPONSE>  AddANewBusiness(@Header("Authorization") String token , @Body AddNewBusiness_REQUEST request);

    @POST("all_business")
    @Headers({"Content-Type: application/json"})
    Call<AllBusinesses_RESPONSE>  FetchAllBusinesses(@Header("Authorization") String token);


    @POST("view_business")
    @Headers({"Content-Type: application/json"})
    Call<SingleBusiness_RESPONSE>  FetchSingleBusiness(@Header("Authorization") String token , @Body SingleBusiness_REQUEST request);


    //Title Update
    @POST("title_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_Title(@Header("Authorization") String token , @Body TitleUpdate_REQUEST request);

    //Desc Update
    @POST("description_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_Description(@Header("Authorization") String token , @Body DescUpdate_REQUEST request);

    //Mobile No Update
    @POST("mobile_no_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_MobileNumber(@Header("Authorization") String token , @Body MobileNoUpdate_REQUEST request);

    //Landline No Update
    @POST("landline_no_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_LandlineNumber(@Header("Authorization") String token , @Body LandlineUpdate_REQUEST request);

    //Contact Person Update
    @POST("contact_person_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_ContactPerson(@Header("Authorization") String token , @Body ContactPersonUpdate_REQUEST request);

    //Timing Update
    @POST("open_24hr_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_Open24Hr(@Header("Authorization") String token , @Body Open24hrUpdate_REQUEST request);

    //Timing Update
    @POST("open_hours_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_OpenHours(@Header("Authorization") String token , @Body OpenHoursUpdate_REQUEST request);

    //Address
    @POST("address_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_Address(@Header("Authorization") String token , @Body AddressUpdate_REQUEST request);

    //Category
    @POST("subcategory_update")
    @Headers({"Content-Type: application/json"})
    Call<UniversalUpdate_RESPONSE>  Update_SubCategory(@Header("Authorization") String token , @Body SubCategoryUpdate_REQUEST request);


    //Update Photos
    @POST("photos_update")
    @Headers({"Content-Type: application/json"})
    Call<PhotosUpdate_RESPONSE>  Update_Photos(@Header("Authorization") String token , @Body PhotosUpdate_REQUEST request);


    //Search
    @POST("search_business")
    @Headers({"Content-Type: application/json"})
    Call<Search_RESPONSE>  Search(@Body Search_REQUEST request);

   // Business Show
    @POST("business_by_subcategories")
    @Headers({"Content-Type: application/json"})
    Call<View_RESPONSE>  FetchBusinessByCategory(@Body View_REQUEST request);




}
