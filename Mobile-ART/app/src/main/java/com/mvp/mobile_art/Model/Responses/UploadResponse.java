package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.Link;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by jcla123ns on 03/08/17.
 */

public class UploadResponse extends Response {
    private String image;
    private Link links;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
}
