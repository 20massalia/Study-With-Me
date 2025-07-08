## 네트워크 진단 명령어 정리

### 🟢 ping
- **역할**: 대상 호스트가 네트워크에 연결되어 있는지 확인
- **사용법**: `ping [도메인 또는 IP]`
- **예시**: `ping google.com`
- **출력**:
  - 응답 시간 (ms)
  - 패킷 손실률
  - TTL(Time To Live)
```shell
~ ping -c 3 google.com
PING google.com (142.250.76.142): 56 data bytes
64 bytes from 142.250.76.142: icmp_seq=0 ttl=115 time=31.741 ms
64 bytes from 142.250.76.142: icmp_seq=1 ttl=115 time=28.267 ms
64 bytes from 142.250.76.142: icmp_seq=2 ttl=115 time=32.321 ms

```
- 3번의 패킷을 보내는 명령어

```shell
➜  ~ ping -c 10 -s 1000 google.com
PING google.com (172.217.161.238): 1000 data bytes
1008 bytes from 172.217.161.238: icmp_seq=0 ttl=115 time=33.181 ms
1008 bytes from 172.217.161.238: icmp_seq=1 ttl=115 time=33.338 ms
1008 bytes from 172.217.161.238: icmp_seq=2 ttl=115 time=33.566 ms
1008 bytes from 172.217.161.238: icmp_seq=3 ttl=115 time=32.128 ms
1008 bytes from 172.217.161.238: icmp_seq=4 ttl=115 time=32.663 ms
1008 bytes from 172.217.161.238: icmp_seq=5 ttl=115 time=36.310 ms
1008 bytes from 172.217.161.238: icmp_seq=6 ttl=115 time=32.739 ms
1008 bytes from 172.217.161.238: icmp_seq=7 ttl=115 time=34.460 ms
1008 bytes from 172.217.161.238: icmp_seq=8 ttl=115 time=37.483 ms
1008 bytes from 172.217.161.238: icmp_seq=9 ttl=115 time=37.987 ms

```
- 1000 byte의 payload 와 8 byte 의 icmp 헤더를 합쳐서 총 1008 byte 의 패킷이 전송됨
---

### 🟡 netstat (network statistics)
- **역할**: 현재 시스템의 네트워크 연결 상태, 포트, 라우팅 정보 등을 확인
- **사용법**: `netstat -a`, `netstat -n`, `netstat -an`
- **예시**: `netstat -an`
- **주요 정보**:
    - 열려 있는 포트 목록
    - 연결된 IP 및 포트 상태 (LISTENING, ESTABLISHED 등)
```
➜  ~ netstat -na | grep ESTABLISHED | wc -l
      23
```
- -n 옵션 : IP 주소와 포트를 숫자 그대로 표시
- -a 옵션 : 모든 연결과 리스닝 포트 표시
- grep ESTABLISHED : 그 중에서 상태가 ESTABLISHED인 연결만 필터 (실제로 연결이 맺어진 세션만 표시)
- wc -l : 출력된 줄 수를 세서 몇 개의 ESTABLISHED 연결이 있는지 개수로 보여줌

```
➜  ~ netstat -an | grep :80 | grep ESTABLISHED | wc -l
       0
```
- 80포트에 연결되어있는 세션 수가 몇개인지 표시
---

### 🔵 nslookup (name server lookup)
- **역할**: 도메인 이름 ↔ IP 주소를 확인 (DNS 질의)
- **사용법**: `nslookup [도메인]`
- **예시**: `nslookup naver.com`
- **활용**:
    - 도메인 이름에 대한 IP 주소 확인
    - DNS 서버 응답 테스트
```
➜  ~ nslookup dandee.dev
Server:		168.126.63.1
Address:	168.126.63.1#53

Non-authoritative answer:
Name:	dandee.dev
Address: 172.67.134.222
Name:	dandee.dev
Address: 104.21.6.129

```
---

### 🔴 tracert (trace route)
- **역할**: 패킷이 목적지까지 가는 경로(라우터 홉)를 추적
- **사용법**: `tracert [도메인 또는 IP]`  
  *(macOS/Linux는 `traceroute`)*
- **예시**: `tracert google.com or tracert 8.8.8.8`
- **출력**:
    - 중간 경유지(라우터)의 IP 및 응답 시간
    - 네트워크 지연이나 병목 구간 파악 가능
    - dns 서버까지 가기위해 거쳐간 네트워크 Lan 대역들 
    - 보통 15개 내로 연결되어있다
```shell
➜  ~ traceroute 8.8.8.8
traceroute to 8.8.8.8 (8.8.8.8), 64 hops max, 40 byte packets
 1  172.30.1.254 (172.30.1.254)  8.463 ms  3.351 ms  3.112 ms
 2  192.168.0.1 (192.168.0.1)  3.656 ms  3.616 ms  3.569 ms
 3  118.45.212.254 (118.45.212.254)  5.052 ms  6.242 ms *
 4  112.190.163.1 (112.190.163.1)  9.678 ms  5.428 ms  5.551 ms
 5  112.190.134.69 (112.190.134.69)  5.642 ms
    112.190.134.1 (112.190.134.1)  4.994 ms
    112.190.133.69 (112.190.133.69)  5.182 ms
 6  112.174.48.2 (112.174.48.2)  6.344 ms
    112.190.31.249 (112.190.31.249)  6.318 ms  6.381 ms
 7  112.174.84.145 (112.174.84.145)  7.517 ms
    112.174.84.54 (112.174.84.54)  6.363 ms
    112.174.84.6 (112.174.84.6)  7.065 ms
 8  72.14.202.136 (72.14.202.136)  30.308 ms  30.878 ms  31.548 ms
 9  216.239.47.31 (216.239.47.31)  29.235 ms
    108.170.238.53 (108.170.238.53)  27.484 ms *
10  142.251.48.19 (142.251.48.19)  28.384 ms
    142.250.239.221 (142.250.239.221)  28.061 ms
    142.250.58.193 (142.250.58.193)  28.055 ms
11  dns.google (8.8.8.8)  30.945 ms  30.912 ms  28.024 ms

```
---