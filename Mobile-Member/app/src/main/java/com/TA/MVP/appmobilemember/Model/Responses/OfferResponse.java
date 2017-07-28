package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
