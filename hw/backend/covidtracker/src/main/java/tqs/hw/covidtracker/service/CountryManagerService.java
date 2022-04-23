package tqs.hw.covidtracker.service;


import org.apache.http.client.utils.URIBuilder;
import com.google.gson.Gson;
import tqs.hw.covidtracker.connection.ISimpleHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryManagerService {
//TODO: TOLOWERCASE ALL REQUESTS!!!!!!
    private final ISimpleHttpClient httpClient;
    private final FetchContent fetcher;
    private String hit ="Cache hit!";
    private String miss ="Cache miss!";

    public final Cache cache;
    public CountryManagerService(ISimpleHttpClient httpClient) throws URISyntaxException {
        this.httpClient = httpClient;
        this.fetcher=new FetchContent();
        this.cache = new Cache();
    }


    public String dataNotFound(){
        return """
                {
                "message": "Country not found or doesn't have any cases"
                }
                """;
    }

    public String getAllCountryData() throws URISyntaxException {
        String data;
        String origin = "api/v1/countries";
        if(cache.contains(origin)){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, hit);

            data = cache.getCache().get(origin);
        }
        else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, miss);
            ArrayList<String> tmpData = fetcher.getAllCountries();
            if (Objects.equals(tmpData.get(0), "0")) {
                data = tmpData.get(1);
                cache.put(origin, data);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Obtained All Countries.");
            }
            else{
                data = tmpData.get(1);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Couldn't obtain All Countries.");
            }
        }

        return data;
    }

    public String getCountryData(String name,String time) {
        String data;
        String filter;

        switch (time.toLowerCase(Locale.ROOT)) {
            case "1" -> {

                filter = "?yesterday=true";
            }
            case "2" -> {

                filter = "?twoDaysAgo=true";
            }
            default -> {
                filter = "";
            }
        }
        System.out.println("Time: "+ time);
        System.out.println("Time: "+ name);

        System.out.println("Filter: "+ filter);
        String origin = "api/v1/countries/"+name.toLowerCase(Locale.ROOT)+filter;
        System.out.println("link: "+ origin);

        if(cache.contains(origin)){

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, hit);
            data = cache.getCache().get(origin);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Obtained "+name +" data.");

        }
        else {

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, miss);
            ArrayList<String> tmpData = fetcher.getThisCountry(name,filter);
            if (Objects.equals(tmpData.get(0), "0")){
                data = tmpData.get(1);

                cache.put(origin, data);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Obtained "+name +" data.");

            }else{
                data = tmpData.get(1);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Couldn't obtain "+ name +" data.");

            }
        }

        return data;

    }

    public String getAllData() throws URISyntaxException {
        String data;
        String origin = "api/v1/all";
        if(cache.contains(origin)){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, hit);

            data = cache.getCache().get(origin);
        }
        else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, miss);
            ArrayList<String> tmpData = fetcher.getAllData();
            if (Objects.equals(tmpData.get(0), "0")) {
                data = tmpData.get(1);
                cache.put(origin, data);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Obtained All Data.");
            }
            else{
                data = tmpData.get(1);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Couldn't obtain All Data.");
            }
        }

        return data;
    }

    public String getCacheStats(){
        return cache.getStatsJson();
    }


    public class Cache{

        public class CacheStats {
            Integer hits;
            Integer miss;
            Integer requests;
            CacheStats(){
                this.hits = 0;
                this.miss = 0;
                this.requests = 0;
            }

            public Integer getRequests() {
                return requests;
            }

            public Integer getHits() {
                return hits;
            }

            public Integer getMiss() {
                return miss;
            }

            void incrHit(){
                hits++;
                requests++;
            }
            void incrMiss(){
                miss++;
                requests++;
            }

            void clear(){
                this.hits = 0;
                this.miss = 0;
                this.requests = 0;
            }
        }
        HashMap<String,String> cache;
        HashMap<String,Long> cacheTTL;
        CacheStats stats;
        Integer ttl;
        Cache(){
            cache=new HashMap<>();
            cacheTTL=new HashMap<>();
            stats=new CacheStats();

            ttl=10000;
        }

        void verifyCache(){
            // To avoid results getting stale.
            Long instant =System.currentTimeMillis();
            ArrayList<String> toRemove=new ArrayList<>();
            for (String key:  cacheTTL.keySet()){
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "TTL: "+this.ttl);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "It's been: "+(instant-cacheTTL.get(key)));

                if (instant-cacheTTL.get(key)>ttl){
                    toRemove.add(key);
                }
            }

            for(String key : toRemove){
                cacheTTL.remove(key);
                cache.remove(key);
            }
        }

        void put(String key, String value){
            cache.put(key,value);
            cacheTTL.put(key,System.currentTimeMillis());
        }


        boolean contains(String key){
            verifyCache();
            if (cache.containsKey(key)){
                stats.incrHit();
                return true;

            }else {
                stats.incrMiss();
                return false;
            }

        }

        HashMap<String, String> getCache() {
            return cache;
        }

        public String  getStatsJson(){
            Gson gson = new Gson();
            return gson.toJson(stats);
        }

        public CacheStats getStats(){
            return stats;
        }
        void clear(){
            this.stats.clear();
            this.cache.clear();
            this.cacheTTL.clear();
            this.stats.clear();
        }
    }

    private class FetchContent{
        String mainAPI =  "https://disease.sh/v3/covid-19/";
     URIBuilder uriBuilder;
        private final ISimpleHttpClient httpClient = CountryManagerService.this.httpClient;

        private FetchContent() throws URISyntaxException {
        }


        private ArrayList<String> getAllCountries() throws URISyntaxException {
            //String backupAPI1 = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/covid-19-qppza/service/REST-API/incoming_webhook/countries_summary?country=";
            //String backupAPI2= "&min_date=2020-04-27T00:00:00.000Z&max_date=2020-04-27T00:00:00.000Z&hide_fields=_id,%20combined_names,%20country_codes,%20country_iso2s,states,country_iso3s,uids";

            try {

                uriBuilder = new URIBuilder(mainAPI+"countries/");
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sending request for all countries to Main Api");

                String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, apiResponse);

                return new ArrayList<>(){{add("0");
                    add(apiResponse);}};
            } catch ( IOException ex){
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Main API did not respond!");

                //TODO try other API
                //uriBuilder = new URIBuilder(backupAPI1);

            }
             return new ArrayList<>(){{add("1");
                add(dataNotFound());}};

        }
        private ArrayList<String> getAllData() throws URISyntaxException {
            //String backupAPI1 = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/covid-19-qppza/service/REST-API/incoming_webhook/countries_summary?country=";
            //String backupAPI2= "&min_date=2020-04-27T00:00:00.000Z&max_date=2020-04-27T00:00:00.000Z&hide_fields=_id,%20combined_names,%20country_codes,%20country_iso2s,states,country_iso3s,uids";

            try {

                uriBuilder = new URIBuilder(mainAPI+"all");
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sending request for all data to Main Api");

                String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, apiResponse);

                return new ArrayList<>(){{add("0");
                    add(apiResponse);}};
            } catch ( IOException ex){
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Main API did not respond!");

                //TODO try other API
                //uriBuilder = new URIBuilder(backupAPI1);

            }
            return new ArrayList<>(){{add("1");
                add(dataNotFound());}};

        }
        private ArrayList<String> getThisCountry(String country, String filter){
            String backupAPI1 = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/covid-19-qppza/service/REST-API/incoming_webhook/countries_summary?country=";
            String backupAPI2= "&min_date=2020-04-27T00:00:00.000Z&max_date=2020-04-27T00:00:00.000Z&hide_fields=_id,%20combined_names,%20country_codes,%20country_iso2s,states,country_iso3s,uids";
            try {

                uriBuilder = new URIBuilder(mainAPI+"countries/"+country.toLowerCase(Locale.ROOT)+filter);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sending request for "+country+" to Main Api");

                String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, apiResponse);

                return new ArrayList<>(){{add("0");
                    add(apiResponse);}};
            } catch (IOException | URISyntaxException ex){
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Main API did not respond!");

                //TODO try other API
                //uriBuilder = new URIBuilder(backupAPI1);

            }
            return new ArrayList<>(){{add("1");
                add(dataNotFound());}};

        }


/*
        private ArrayList<String> getTimeCountry(String name) throws URISyntaxException {
            //String backupAPI1 = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/covid-19-qppza/service/REST-API/incoming_webhook/countries_summary?country=";
            //String backupAPI2= "&min_date=2020-04-27T00:00:00.000Z&max_date=2020-04-27T00:00:00.000Z&hide_fields=_id,%20combined_names,%20country_codes,%20country_iso2s,states,country_iso3s,uids";

            try {

                uriBuilder = new URIBuilder(mainAPI+"time/countries/"+name);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sending request for Time data for "+name+" to Main Api");

                String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, apiResponse);

                return new ArrayList<>(){{add("0");
                    add(apiResponse);}};
            } catch ( IOException ex){
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Main API did not respond!");

                //TODO try other API
                //uriBuilder = new URIBuilder(backupAPI1);

            }
            return new ArrayList<>(){{add("1");
                add(dataNotFound());}};

        }

 */
    }
}