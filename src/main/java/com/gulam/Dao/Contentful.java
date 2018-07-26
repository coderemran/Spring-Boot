package com.gulam.Dao;

import com.gulam.utility.ProcessJsonResponse;
import org.json.JSONObject;

public class Contentful {
    //spaceID
    private String spaceID = "3su0kqe2ind7";
    //contentfulURL
    private String contentfulURL = "https://cdn.contentful.com/";
    //accessToken
    private String accessToken = "dad2d59a42cfc7b41dbe6d492025d876cb1829940cd3e3d092a0831e3f4157e0";
    //environement
    private String environment = "master";
    //content type
    //default course
    private String contentType = "promotions";

    //get details of an asset
    //using entry id(String)
    //return entry Details(JsonObject)
    public JSONObject getEntryDetailsByID(String contentId){
        //assemble URL
        String contentfulURL = this.contentfulURL+"/spaces/"+this.spaceID+"/environments/"+this.environment+"/entries/"+contentId+"?access_token="+this.accessToken;
        //get response from contentful
        JSONObject jsonObject = ProcessJsonResponse.getJsonResponse(contentfulURL);
        //return
        return jsonObject;
    }

    //get details of an asset
    //using asset id(String)
    //return assetDetails(JsonObject)
    public JSONObject getAssetsByAssetID(String assetId){
        //assemble URL
        String contentfulURL = this.contentfulURL + "/spaces/"+this.spaceID+"/environments/"+this.environment+"/assets/"+assetId+"?access_token="+this.accessToken;
        //get response from contentful
        JSONObject jsonObject = ProcessJsonResponse.getJsonResponse(contentfulURL);
        //return
        return jsonObject;
    }

    /********************************************/
    /* GetImageById                             */
    /* @input: imageId(string)                  */
    /* @return: imageURL(string)                */
    /********************************************/


    public String getImageByID(String imageId){
        //construct JSON URL
        String assetURL = this.contentfulURL + "/spaces/"+this.spaceID+"/environments/"+this.environment+"/assets/"+imageId+"?access_token="+this.accessToken;
        String imageURL = null;
        //get image data
        JSONObject imageObject = ProcessJsonResponse.getJsonResponse(assetURL);
        imageURL = imageObject.getJSONObject("fields").getJSONObject("file").get("url").toString();
        //return
        return imageURL;
    }
}
