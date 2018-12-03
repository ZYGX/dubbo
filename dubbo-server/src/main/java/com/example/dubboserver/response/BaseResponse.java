package com.example.dubboserver.response;


/**
 * Created by zygx on 2018/5/28.
 */
public class BaseResponse<T> {

    private  String errCode;//错误码

    private String errMsg;//错误信息

    private T result;//返回结果

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return  this.errCode.equals(BaseResponseCode.SUCCESS.getErrCode());
    }

    public boolean isFail(){
        return  !isSuccess();
    }

    public BaseResponse(BaseResponseCode baseArbitrationResponseCode){
        this.errCode=baseArbitrationResponseCode.getErrCode();
        this.errMsg=baseArbitrationResponseCode.getErrMsg();
    }

    public BaseResponse(){
        this.errCode= BaseResponseCode.SUCCESS.getErrCode();
        this.errMsg= BaseResponseCode.SUCCESS.getErrMsg();
    }

    public BaseResponse ok(T data){
        BaseResponse baseArbitrationResponse=new BaseResponse(BaseResponseCode.SUCCESS);
        baseArbitrationResponse.setResult(data);
        return baseArbitrationResponse;
    }

    public void sysError(){
        this.errCode= BaseResponseCode.SYS_ERROR.getErrCode();
        this.errMsg= BaseResponseCode.SYS_ERROR.getErrMsg();
    }


    public enum BaseResponseCode{
        SUCCESS("0000","success"),
        PARAM_CHECK_NULL("1001","param check null"),
        PARAM_CHECK_FAIL("1002","param check fail"),
        SYS_ERROR("9999","system error");
        private  String errCode;//错误码

        private String errMsg;//错误信息

        public String getErrCode() {
            return errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        BaseResponseCode(String errCode, String errMsg) {
            this.errCode = errCode;
            this.errMsg = errMsg;
        }
    }
}
