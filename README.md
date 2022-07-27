# InfluxDB 2.3 CentOS

---

## 1-1. InfluxDB UI 설치

```shell
wget https://dl.influxdata.com/influxdb/releases/influxdb2-2.3.0-x86_64.rpm # yum install wget
sudo yum localinstall influxdb2-2.3.0-x86_64.rpm
```

- 설치 확인

  ```shell
  sudo service influxdb start
  sudo service influxdb status
  influxdb.service - InfluxDB is an open-source, distributed, time series database
    Loaded: loaded (/lib/systemd/system/influxdb.service; enabled; vendor preset: enable>
    Active: active (running)
  ```

- 설정

  ```shell
  sudo nano /etc/default/influxdb2 # yum install nano
  ARG1="--http-bind-address :8087" # 추가
  sudo nano /lib/systemd/system/influxdb.service
  ExecStart=/usr/bin/influxd $ARG1 # 추가
  ```

## 1-2. InfluxDB CLI 설치

``` shell
wget https://dl.influxdata.com/influxdb/releases/influxdb2-client-2.3.0-linux-amd64.tar.gz
tar xvzf influxdb2-client-2.3.0-linux-amd64.tar.gz
sudo cp influxdb2-client-2.3.0-linux-amd64/influx /usr/local/bin/
```

## 2. InfluxDB 접속

- port 열어주기

  ```shell
   firewall-cmd --permanent --zone=public --add-port=8086/tcp
   # permanent : 지정한 옵션을 영구적으로 사용 (재부팅시 초기화X)
   firewall-cmd --reload
  ```

- IP 확인하기

  ```shell
  ifconfig # yum install net-tools
  # 192.168.4.55
  http://192.168.4.55:8086 접속
  ```
  
## 3. InfluxDB config파일 설정
```shell
influx config create --config-name jwpark_config \
  --host-url http://192.168.4.58:8086 \
  --org 조직입력 \
  --token 토큰입력 \
  --active
```
- config 파일 목록 확인
```shell
influx config list
```

## 4. JAVA 연동
###  1. Setting
```xml
<!-- /influx-client/pom.xml-->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  	<modelVersion>4.0.0</modelVersion>

  	<groupId>com.influx</groupId>
  	<artifactId>influx-client</artifactId>
 	<version>6.4.0-SNAPSHOT</version>
 	<packaging>jar</packaging>

  	<name>influx-client</name>
  	<url>http://maven.apache.org</url>

  	<properties>
    	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    	<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
  	</properties>

  	<dependencies>
    	<dependency>
      		<groupId>junit</groupId>
      		<artifactId>junit</artifactId>
      		<version>3.8.1</version>
      		<scope>test</scope>
    	</dependency>
    <!-- influxdb dependency 추가 -->
	<dependency>
    	<groupId>com.influxdb</groupId>
    	<artifactId>influxdb-client-java</artifactId>
    	<version>6.3.0</version>
	</dependency>      
  </dependencies>
</project>

```

- https://influxdata.github.io/influxdb-client-java/apidocs/com/influxdb/client/package-summary.html

### 2. 연동 확인
```java
package com.influx.influx_client;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.UsersApi;

public class ConnectCheck {
    private static char[] token = "자기 토큰 입력".toCharArray();
    private static String serverURL = "url입력";
    private static String org = "조직입력";
    private static String bucket = "bucket 입력";
    
    public static void main(final String[] args) {
    	InfluxDBClient influxDBClient = InfluxDBClientFactory.create(serverURL, token, org, bucket);
    	
    	UsersApi userapi = influxDBClient.getUsersApi();
    	System.out.println(userapi.me()); // id, name, status 출력
    	
    	influxDBClient.close();
    	}
	}
```

### 3. 
