package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Link;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
