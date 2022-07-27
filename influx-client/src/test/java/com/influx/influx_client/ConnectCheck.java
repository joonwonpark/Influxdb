package com.influx.influx_client;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.UsersApi;



public class ConnectCheck {
    private static char[] token = "9jm46ckwe7mK4WVtykMFEkeYqAfiKtRvtfESqQcfZJogk8Om7SKXqVBj-i_ARNYIvmuALs_ZqoC7X7jAB18OBg==".toCharArray();
    private static String serverURL = "http://192.168.4.58:8086";
    private static String org = "raycom";
    private static String bucket = "test-bucket";
    
    public static void main(final String[] args) {
    	InfluxDBClient influxDBClient = InfluxDBClientFactory.create(serverURL, token, org, bucket);
    	
    	UsersApi userapi = influxDBClient.getUsersApi();
    	System.out.println(userapi.me()); // id, name, status 출력
    	
    	influxDBClient.close();
    	}
	}