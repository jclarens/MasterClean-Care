package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.ReviewOrder;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
