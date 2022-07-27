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
  --org raycom \
  --token 9jm46ckwe7mK4WVtykMFEkeYqAfiKtRvtfESqQcfZJogk8Om7SKXqVBj-i_ARNYIvmuALs_ZqoC7X7jAB18OBg== \
  --active
```
- config 파일 목록 확인
```shell
influx config list
```