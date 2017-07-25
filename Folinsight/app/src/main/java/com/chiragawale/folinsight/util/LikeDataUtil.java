package com.chiragawale.folinsight.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chira on 7/8/2017.
 */

public class LikeDataUtil extends NetworkUtil {
    /***
     *
     * @param request_url URL TO REQUEST DATA FROM INSTAGRAM API
     * @return  LIST OF USER IDS of users who liked recent self  posts
     */
    public static List<Integer> fetchUsersWhoLikedData (String request_url){
        URL usersWhoLikedDataUrl =  createUrl(request_url);
        String jsonResponse = "";
        try {
            //Connects to the Instagram website to get JSON RESPONSE
             jsonResponse = makeHttpRequest(usersWhoLikedDataUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractUserListFromJsonResponse(jsonResponse);
    }

    //Extracts User Ids from JSon reponse and returns as a list
    private static List<Integer> extractUserListFromJsonResponse(String jsonResponse){
        List<Integer> usersWhoLikedList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray dataArray = root.getJSONArray("data");
            for(int i = 0; i < dataArray.length();i++) {
                JSONObject currentUserData = dataArray.getJSONObject(i);
                int id = currentUserData.getInt("id");
                usersWhoLikedList.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return usersWhoLikedList;
    }
}
