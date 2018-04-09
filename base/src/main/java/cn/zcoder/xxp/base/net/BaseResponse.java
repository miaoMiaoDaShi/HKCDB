package cn.zcoder.xxp.base.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/10/16
 * Description :
 */


public class BaseResponse implements Parcelable {
    /**
     * resmsg : token已过期
     * status : 106
     * totalrow : 1
     */

    private String resmsg;
    private int status;
    private int totalrow;

    public String getResmsg() {
        return resmsg;
    }

    public void setResmsg(String resmsg) {
        this.resmsg = resmsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalrow() {
        return totalrow;
    }

    public void setTotalrow(int totalrow) {
        this.totalrow = totalrow;
    }

    public boolean isOk() {
        return status == 0;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resmsg='" + resmsg + '\'' +
                ", status=" + status +
                ", totalrow=" + totalrow +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.resmsg);
        dest.writeInt(this.status);
        dest.writeInt(this.totalrow);
    }

    public BaseResponse() {
    }

    protected BaseResponse(Parcel in) {
        this.resmsg = in.readString();
        this.status = in.readInt();
        this.totalrow = in.readInt();
    }

    public static final Parcelable.Creator<BaseResponse> CREATOR = new Parcelable.Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}
