
package entity;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Base {

    @SerializedName("backoff")
    private Long mBackoff;
    @SerializedName("has_more")
    private Boolean mHasMore;
    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("quota_max")
    private Long mQuotaMax;
    @SerializedName("quota_remaining")
    private Long mQuotaRemaining;

    public Long getBackoff() {
        return mBackoff;
    }

    public void setBackoff(Long backoff) {
        mBackoff = backoff;
    }

    public Boolean getHasMore() {
        return mHasMore;
    }

    public void setHasMore(Boolean hasMore) {
        mHasMore = hasMore;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Long getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(Long quotaMax) {
        mQuotaMax = quotaMax;
    }

    public Long getQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(Long quotaRemaining) {
        mQuotaRemaining = quotaRemaining;
    }

}
