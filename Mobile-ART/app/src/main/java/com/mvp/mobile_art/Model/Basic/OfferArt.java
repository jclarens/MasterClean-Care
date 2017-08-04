package com.mvp.mobile_art.Model.Basic;

/**
 * Created by Zackzack on 19/07/2017.
 */

public class OfferArt {
    private Integer id;
    private Integer offer_id;
    private Integer art_id;
    private Offer offer;
    private User art;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public Integer getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        this.offer_id = offer_id;
    }

    public Integer getArt_id() {
        return art_id;
    }

    public void setArt_id(Integer art_id) {
        this.art_id = art_id;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getArt() {
        return art;
    }

    public void setArt(User art) {
        this.art = art;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
