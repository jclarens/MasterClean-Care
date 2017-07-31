package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class OfferResponse extends Response {
    private Offer offer;

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
