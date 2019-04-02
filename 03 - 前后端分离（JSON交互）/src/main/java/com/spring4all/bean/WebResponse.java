package com.spring4all.bean;


import com.spring4all.constant.CommonResponse;
import com.spring4all.constant.HttpResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP 统一响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse {

    /**
     * 响应码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 结果
     */
    private Object result;

    public WebResponse(HttpResponseStatusEnum httpResponseStatusEnum) {
        this.code = httpResponseStatusEnum.getCode();
        this.message = httpResponseStatusEnum.getMessage();
    }

    public WebResponse(CommonResponse commonResponse) {
        this.code = commonResponse.getCode();
        this.message = commonResponse.getMessage();
    }

    /**
     * 成功响应
     */
    public static WebResponse success() {
        return new WebResponse(HttpResponseStatusEnum.SUCCESS.getCode(), HttpResponseStatusEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功响应
     */
    public static WebResponse success(Object result) {
        return new WebResponse(HttpResponseStatusEnum.SUCCESS.getCode(), HttpResponseStatusEnum.SUCCESS.getMessage(), result);
    }

    /**
     * 禁止操作
     */
    public static WebResponse forbidden() {
        return new WebResponse(HttpResponseStatusEnum.FORBIDDEN_OPERATION.getCode(), HttpResponseStatusEnum.FORBIDDEN_OPERATION.getMessage(), null);
    }

}
