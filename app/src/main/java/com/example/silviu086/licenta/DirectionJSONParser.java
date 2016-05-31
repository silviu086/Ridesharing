package com.example.silviu086.licenta;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Silviu086 on 12.05.2016.
 */
public class DirectionJSONParser {

    private String durata;
    private String distanta;

    public List<List<HashMap<String, String>>> parse(JSONObject jsonObject){
        List<List<HashMap<String, String>>> directionRoutes = new ArrayList<>();
        JSONArray jsonRoutes = null;
        JSONArray jsonLegs = null;
        JSONArray jsonSteps = null;

        try{
            jsonRoutes = jsonObject.getJSONArray("routes");

            for(int i=0;i<jsonRoutes.length();i++){
                List<HashMap<String, String>> path = new ArrayList();
                jsonLegs = jsonRoutes.getJSONObject(i).getJSONArray("legs");

                for(int j=0;j<jsonLegs.length();j++){
                    distanta = jsonLegs.getJSONObject(j).getJSONObject("distance").getString("text");
                    durata = jsonLegs.getJSONObject(j).getJSONObject("duration").getString("text");
                    jsonSteps = jsonLegs.getJSONObject(j).getJSONArray("steps");

                    for(int k=0;k<jsonSteps.length();k++){
                        String polyline = "";
                        polyline = jsonSteps.getJSONObject(k).getJSONObject("polyline").getString("points");
                        List<LatLng> lista = decodePoly(polyline);

                        for(int l=0;l<lista.size();l++){
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("lat", String.valueOf(lista.get(l).latitude));
                            hashMap.put("lng", String.valueOf(lista.get(l).longitude));
                            path.add(hashMap);
                        }
                    }
                }
                directionRoutes.add(path);
            }
        }catch (Exception ex){

        }
        return directionRoutes;
    }

    public String getDurata() {
        return durata;
    }

    public String getDistanta() {
        return distanta;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
