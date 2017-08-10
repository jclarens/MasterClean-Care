package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.ReviewOrder;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class ReviewOrderResponse extends Response {
    private ReviewOrder reviewOrder;

    public ReviewOrder getReviewOrder() {
        return reviewOrder;
    }

    public void setReviewOrder(ReviewOrder reviewOrder) {
        this.reviewOrder = reviewOrder;
    }
}
