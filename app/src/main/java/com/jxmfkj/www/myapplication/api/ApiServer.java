package com.jxmfkj.www.myapplication.api;

import com.jxmfkj.www.myapplication.Entity.BannerEntity;
import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.CollectionEntity;
import com.jxmfkj.www.myapplication.Entity.ConsultEntity;
import com.jxmfkj.www.myapplication.Entity.CssEntity;
import com.jxmfkj.www.myapplication.Entity.HistoryEntity;
import com.jxmfkj.www.myapplication.Entity.LoginEntity;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.Entity.NewsWEntity;
import com.jxmfkj.www.myapplication.Entity.RegisterEntity;
import com.jxmfkj.www.myapplication.Entity.SearchEntity;
import com.jxmfkj.www.myapplication.Entity.SearchListEntity;
import com.jxmfkj.www.myapplication.Entity.SwEntity;
import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.Entity.ThereEntity;
import com.jxmfkj.www.myapplication.Entity.Translation1;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiServer {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<ThereEntity> getCall();

    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation1> getCall(@Field("i") String targetSentence);
    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段

    /**
     * 玩安卓开放接口-公众号接口
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Call<SwEntity> getSwCall();

    /**
     * 公众号历史记录
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Call<HistoryEntity> getHistCall(@Path("id") String id, @Path("page") String page);

    /**
     * 玩安卓开放接口-公众号接口
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Observable<BaseResponse<List<SwEntity>>> getChapters();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<NewsWEntity<List<NewsEntity>>>> getArticle(@Path("id") String id, @Path("page") String page);

    /**
     * 注册接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<RegisterEntity> getRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResponse<LoginEntity>> getLogin(@Field("username") String username, @Field("password") String password);


    @GET("/banner/json")
    Observable<BaseResponse<List<BannerEntity>>> getBanner();

    @GET("/article/list/{page}/json")
    Observable<BaseResponse<CssEntity<List<ConsultEntity>>>> getConsult(@Path("page") String page);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<BaseResponse> getCollect(@Path("id") String id);

    /**
     * 搜索
     *
     * @param page
     * @param K
     * @return
     */
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    Observable<BaseResponse<SearchEntity<List<SearchListEntity>>>> getSearch(@Path("page") String page, @Field("k") String K);


    @GET("/tree/json")
    Observable<BaseResponse<List<SystemEntity>>> getSystem();

    /**
     * 置顶数据
     *
     * @return
     */
    @GET("/article/top/json")
    Observable<BaseResponse<List<ConsultEntity>>> getTop();

    /**
     * 体系列表文章
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<SearchEntity<List<NewsEntity>>>> getArtcle(@Path("page") String page, @Query("cid") String cid);

    /**
     * 收藏列表
     * @param page
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<BaseResponse<SearchEntity<List<CollectionEntity>>>> getCollection(@Path("page") String page);
}