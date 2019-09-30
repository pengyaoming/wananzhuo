package com.jxmfkj.www.myapplication.api;

import com.jxmfkj.www.myapplication.Entity.BannerEntity;
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
import com.jxmfkj.www.myapplication.base.BaseEntity;

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
    Observable<BaseEntity<List<SwEntity>>> getChapters();

    /**
     * 公众号
     * @param id
     * @param page
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseEntity<NewsWEntity<List<NewsEntity>>>> getArticle(@Path("id") String id, @Path("page") String page);

    /**
     * 注册接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<RegisterEntity> getRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseEntity<LoginEntity>> getLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 轮播图
     * @return
     */
    @GET("/banner/json")
    Observable<BaseEntity<List<BannerEntity>>> getBanner();

    /**
     * 首页数据
     * @param page
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseEntity<CssEntity<List<ConsultEntity>>>> getConsult(@Path("page") String page);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<BaseEntity> getCollect(@Path("id") String id);

    /**
     * 搜索
     *
     * @param page
     * @param K
     * @return
     */
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    Observable<BaseEntity<SearchEntity<List<SearchListEntity>>>> getSearch(@Path("page") String page, @Field("k") String K);


    @GET("/tree/json")
    Observable<BaseEntity<List<SystemEntity>>> getSystem();

    /**
     * 置顶数据
     *
     * @return
     */
    @GET("/article/top/json")
    Observable<BaseEntity<List<ConsultEntity>>> getTop();

    /**
     * 体系列表文章
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseEntity<SearchEntity<List<NewsEntity>>>> getArtcle(@Path("page") String page, @Query("cid") String cid);

    /**
     * 收藏列表
     *
     * @param page
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<BaseEntity<SearchEntity<List<CollectionEntity>>>> getCollection(@Path("page") String page);

    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<BaseEntity> getCilckCollection(@Path("id") String id);

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<BannerEntity> getIsCollection(@Path("id") String id);
}