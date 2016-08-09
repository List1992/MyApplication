package com.example.administrator.myapplication.utils;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 网络请求
 */
public class CommonRequest {
    private RequestCallBack mRequestCallBack;

    public static CommonRequest getCommonRequest(RequestCallBack mRequestCallBack) {
        return new CommonRequest(mRequestCallBack);
    }

    private CommonRequest(RequestCallBack mRequestCallBack) {
        this.mRequestCallBack = mRequestCallBack;
    }

    public interface RequestCallBack {
        void onResponse(String result);

        void onErrorResponse(String errorMsg);
    }


    public void requestDataByGet(String url) {
        String requestUrl = Preconditions.checkNotNull(url, "request url can not be null!");
        LogUtil.e("====get request start====");
        LogUtil.e("url=" + url);
        LogUtil.e("====get request end====");
        VolleyUtil.getRequestQueue()
                .add(new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (TextUtils.isEmpty(response)) {
                            mRequestCallBack.onErrorResponse("响应数据为空！");
                        } else {
                            int bomCount = 1;
                            while (response.startsWith("\ufeff")) {
                                LogUtil.e("警告：检测到第 " + bomCount + " 个BOM！");
                                response = response.substring(1);
                                LogUtil.e("第 " + bomCount + " 个BOM已去除！");
                                bomCount++;
                            }
                            if (!isAvailableJsonStr(response)) {
                                mRequestCallBack.onErrorResponse("响应数据非标准Json数据。json=" + response);
                            } else {
                                mRequestCallBack.onResponse(response);
                                LogUtil.e("response data = " + response);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mRequestCallBack.onErrorResponse(error.toString());
                    }
                }));
    }


    /**
     * post 请求数据
     *
     * @param url
     * @param params
     * @Description:
     */
    public void requestDataByPost(String url, final Map<String, String> params) {
        String requestUrl = Preconditions.checkNotNull(url, "request url can not be null!");
        LogUtil.e("====post request start====");
        LogUtil.e("url=" + url);
        if (params != null)
            for (Entry<String, String> entry : params.entrySet()) {
                LogUtil.e(entry.getKey() + "=" + entry.getValue());
            }
        LogUtil.e("====post request end====");
        mRequestCallBack = Preconditions.checkNotNull(mRequestCallBack, "request RequestCallBack can not be null!");
        VolleyUtil.getRequestQueue()
                .add(new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        LogUtil.e("response data = " + response);
                        if (TextUtils.isEmpty(response)) {
                            mRequestCallBack.onErrorResponse("响应数据为空！");
                        } else {
                            int bomCount = 1;
                            while (response.startsWith("\ufeff")) {
                                LogUtil.e("警告：检测到第 " + bomCount + " 个BOM！");
                                response = response.substring(1);
                                LogUtil.e("第 " + bomCount + " 个BOM已去除！");
                                bomCount++;
                            }
                            if (!isAvailableJsonStr(response)) {
                                mRequestCallBack.onErrorResponse("响应数据非标准Json数据。json=" + response);
                            } else {
                                mRequestCallBack.onResponse(response);

                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.e("onErrorResponse:" + error);
                        mRequestCallBack.onErrorResponse(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        if (params == null) {
                            return super.getParams();
                        } else {
                            return params;
                        }
                    }
                });
    }


    private boolean isAvailableJsonStr(String str) {
        try {
            if (str.startsWith("{")) {
                new JSONObject(str);
                return true;
            } else if (str.startsWith("[")) {
                new JSONArray(str);
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }
}
