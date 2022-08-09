package com.influx.influx_client;

import java.util.List;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

public class QueryCheck {
    private static char[] token = "9jm46ckwe7mK4WVtykMFEkeYqAfiKtRvtfESqQcfZJogk8Om7SKXqVBj-i_ARNYIvmuALs_ZqoC7X7jAB18OBg==".toCharArray();
    private static String serverURL = "http://192.168.4.58:8086";
    private static String org = "raycom";
    private static String bucket = "test-bucket";

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(serverURL, token, org, bucket);
        String flux = "from(bucket: \"test-bucket\")\r\n" + 
        		"  |> range(start: 0)\r\n" + 
        		"  |> filter(fn: (r) => r[\"_measurement\"] == \"temperature\")\r\n" + 
        		"  |> filter(fn: (r) => r[\"_field\"] == \"value\")\r\n" + 
        		"  |> filter(fn: (r) => r[\"location\"] == \"north\")\r\n" + 
        		"  |> yield(name: \"mean\")";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }

        influxDBClient.close();
    }

}
