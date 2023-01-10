# Getting Started

## 기존 프로젝트 README.md 참조용으로 복사하였습니다.
- Docker 파일 참고하셔서 ```/build/libs/yogig-giglink-0.0.1-SNAPSHOT.jar``` 경로로 jar파일 떨궈주시면 자동으로 배포 되게 설정하였습니다.

- DB 사용이 필요한 경우 아래 환경변수 사용하는 것으로 셋팅하시면 배포 후 파드가 DB접근이 가능합니다.
DB_USERNAME
DB_PASSWORD
DB_ENDPOINT

- Kubernetes에서는 pod 정상여부를 체그 하기 때문에 "/healthy" 경로의 헬스체크 컨트롤로가 필요합니다.

## 하단부터 기존 프로젝트 README.md 내용입니다.

## 로컬 환경 구성

로컬 Yogig-Giglink Spring boot 환경 구성

## General Project Structure

```bash
├─┬ ba_scp/                                     # Directory for 디아모 library
│ ├─ dev/                                       # Directory for 개발용 디아모 key
│ └─ prd/                                       # Directory for 운영용 디아모 key
├─ libs                                         # Directory for external jars
├─┬ src/main/

├─ .gitlab-ci.yml                               # git lab ci file link
├─ build.gradle                                 # gradle build file
├─ docker-compose.yaml                          # Local MySQL, Redis Containers
├─ Docker-entrypoint.sh                         # docker build sh in CICD
└─ Dockerfile                                   # docker build sh in CICD
```

### VS Code

IDE 설치

- VS Code(https://code.visualstudio.com/)
- 중요 단말 망에서는 https://gitlab.hldevops.com/gep/tools/devtools

### VS Code Extensions

개발을 위한 필수 Extention

- Spring Initializr Java Support - 스프링 프로젝트 만들때 템플릿
- Spring Boot Extension Pack - 스프링 개발에 필요한 익스텐션
- Spring Boot Tools - boot configuration
- Java Dependency Viewer - 의존성, 참조
- Debugger for Java - 자바 디버깅
- Java Extension Pack - 인기있는 자바 익스텐션 모음
- Language Support for Java by Red Hat - 코드 네비게이션, 리팩토링 등 생산성 향상
- Java Test Runner - JUnit등 테스트 실행
- Gradle Language Support - Gradle
- Gradle Task - Gradle Build
- Lombok Annotations Support for VS Code - 롬복

### CI

소스관리

- Git Client 설치
- Gitlab 권한 신청
- Gitlab 소스코드 clone : git clone https://gitlab.hldevops.com/gep/backend/gep-gig.git

### Branch 전략

- Infra에서 제공하는 CICD 가이드를 따름
- feature/develop/stage/master 가 기본 Branch
- feature는 jira issue할당 받으면 develop에서 분기하여 생성
- 운영 반영 전 bugfix, 운영 반영 후 hotfix branch 생성
- naming rule -> (master|stage|develop|develop2|(revert|cherry-pick)-[a-z0-9_/]+|[0-9]+-(issue|feature|feature2|bugfix|hotfix)-[-a-z0-9_/]+)
- 예시) issue번호-feature-issue제목, 103-feature-member-login

### Swagger

API 관리

- http://localhost:7070/swagger-ui.html

### Docker 설치

로컬 DB 사용 시(Windows10 && Ram 8G필요)

- Docker for window(https://hub.docker.com/editions/community/docker-ce-desktop-windows)

### DB 접속정보

아래 파일들을 생성하여 위치시킨다.

- /etc/secret/GIG_ENDPOINT : db endpoint ex) localhost:3306
- /etc/secret/GIG_USERNAME : db user name ex) root
- /etc/secret/GIG_PASSWORD : db user password ex) hlihli1!
- /etc/secret/REDIS_ENDPOINT : redis endpoint 포트없음 ex) localhost
- windows경우 C:\etc\secret\ 하위에 위치

### Local DB

Local DB 구성 (폐쇄망은 하단 "docker 세팅" 참고)

- Docker network 생성 : docker network create mysql
- Docker network 확인 : docker network ls
- Mysql Docker Container 기동 : docker-compose up -d
- Mysql Docker Container 중지 : docker-compose down
- 기동되는데 시간 걸림. 접속정보 docker-compose.yaml 참고
- database 미생성 된 상태(샘플DB) -> spring boot 기동하면 생김

### openjdk 1.11 설치

Open JDK 설치

- jdk다운로드 및 로컬 설치 : https://jdk.java.net/archive/ ( 11.0.8)
- 예시 설치 JAVA_HOME : D:/openjdk/openjdk-11.0.2_windows-x64_bin/jdk-11.0.6
- visual studio code 세팅
  - .vscode -> settings.json 클릭 후

```
"java.home": "D:/openjdk/openjdk-11.0.8_windows-x64_bin/jdk-11.0.8",
추가
```

- 위의 세팅을 했음에도 java를 인지못하는 경우
  - C:\Users\skrho\AppData\Roaming\Code\User\settings.json 항목을 찾아 코드 추가

```
"java.home": "D:/openjdk/openjdk-11.0.8_windows-x64_bin/jdk-11.0.8",
추가
```

- 그외 JAVA_HOME과 PATH 추가는 환경변수를 활용함(GIT Bash 또는 WSL, CMD는 환경변수에 따라 동작함)

### 테스트 테이블 및 데이터

flyway사용으로 아래의 자동 테이블 및 저장 데이터 생성

```
CREATE TABLE `Sample` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` VARCHAR(100) NOT NULL COMMENT '이름' COLLATE 'utf8_unicode_ci',
  `status` VARCHAR(1) NULL COMMENT '상태(A,B,C,D)' COLLATE 'utf8_unicode_ci',
  `birthDate` VARCHAR(8) NULL COMMENT '생일' COLLATE 'utf8_unicode_ci',
  `useYn` VARCHAR(1) NULL COMMENT '사용여부YN' COLLATE 'utf8_unicode_ci',
  `createdDatetime` DATETIME NOT NULL COMMENT '최초생성일시',
  `createId` INT(11) NOT NULL COMMENT '최초생성자회원ID',
  `updatedDatetime` DATETIME NOT NULL COMMENT '최종수정일시',
  `updateId` INT(11) NOT NULL COMMENT '최종수정자회원ID',
  PRIMARY KEY (`id`)
)
COMMENT='샘플'
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
;

INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트1','A', '20110101', 'Y', NOW(), -1, NOW(), -1);
INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트2','B', '20110201', 'Y', NOW(), -1, NOW(), -1);
INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트3','C', '20110301', 'Y', NOW(), -1, NOW(), -1);
INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트4','D', '20140101', 'Y', NOW(), -1, NOW(), -1);
INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트5','A', '20140201', 'N', NOW(), -1, NOW(), -1);
INSERT INTO `Sample` (`name`,`status`,`birthDate`,`useYn`,`createdDatetime`,`createId`,`updatedDatetime`,`updateId`) VALUES ('테스트6','A', '20140301', 'N', NOW(), -1, NOW(), -1);
```

### aws config 파일 생성

1. 경로

- 폴더 경로 : ~/.aws
- 파일명 : config

2. 내용

```
[default]
region = ap-northeast-2
```

### aws credential 파일 생성

1. 경로

- 폴더 경로 : ~/.aws
- 파일명 : credentials

2. 내용

```
[default]
aws_access_key_id = 키id값
aws_secret_access_key = 키값
```

### 기동

1. 로컬 db, redis 등 container 기동

   - docker-compose up -d

2. spring boot 기동

   - flyway 실행 확인
   - flyway 참고(https://flywaydb.org/)

3. health check
   - GET, http://localhost:7070/healthy
   - 응답, 200 OK, {"status": "UP"}

---

## 중요 단말망 개발 PC 환경 세팅

- 중요 단말망은 외부 네트워크 접근이 불가하여, 설치 방법을 아래에 명시함
- 설치 파일 다운로드 경로 : https://gitlab.hldevops.com/gep/tools/devtools

### 개발 PC권한 제한에 따른 프로그램 설치 방법

- 환경 변수 수정 권한 미존재로 설치 안 되는 파일 존재함 (크롬, 도커 등)
  - 권한 상승 프로그램을 이용하거나 별도의 권한을 부여 받아서 설치
- JAVA(jdk), gradle 등은 설치 버전이 아닌 binary 버전으로 설치

1. JDK 설치 및 세팅

- OpenJDK11U-jdk_x64_windows_hotspot_11.0.8_10.zip 파일 압축 풀기
- 환경변수 추가 (시스템 변수 수정 권한이 없으므로 사용자 변수에 추가)

```
  CLASSPATH = X:\ProgramFiles\AdoptOpenJDK\jdk-11.0.8+10\lib\
  JAVA_HOME = X:\ProgramFiles\AdoptOpenJDK\jdk-11.0.8+10
  PATH 추가 : %JAVA_HOME%\bin
```

2. Gradle 설치 및 세팅

- gradle-6.4-bin.zip 파일 압축 풀기
- 환경변수 추가

```
  GRADLE_USER_HOME = X:\ProgramFiles\gradle-6.4  (압축을 풀었던 경로)
  PATH 추가 : %GRADLE_USER_HOME%\bin
```

3. vscode 설치 및 익스텐션 설치

- VSCode-win32-x64-1.49.0.zip 압축 풀기
- vscode-extension.zip 압축 풀기
- 이외에 필요한 익스텐션 설치
- 설치 방법 : vscode 좌측 익스텐션 선택하여, 상단 '...' 클릭 - Install from VSIX - 압축 풀었던 extension 파일 선택

### gradle zip 파일 경로 수정

- 기본적으로 외부 웹url로 세팅되어있어서, gradle build시 외부 접속을 시도하기 때문에 수정 필요
- 대상파일 : gradle-wrapper.properties
- 파일경로 : gep-gig/gradle/wrapper/gradle-wrapper.properties
- 수정사항 : 기존 웹url대신 gradle-6.4-bin.zip파일이 저장되어있는 로컬 경로로 수정

```
  distributionUrl=file\:/{$파일경로}
  예) distributionUrl=file\:/C:/Users/Administrator/Documents/devtools/gradle-6.4-bin.zip
```

### docker 세팅

1. image 파일 다운로드

- mysql.tar : mysql 이미지 파일
- redis.tar : redis 이미지 파일
- adminer.tar : adminer 이미지 파일 (필수 아님)

2. image load

- 터미널에서 실행

```
docker load -i mysql.tar
docker load -i redis.tar
docker load -i adminer.tar
```

3. 컨테이너 생성

- 터미널에서 실행

```
docker run -dit --name gep-gig_adminer_1 -p 8080:8080 adminer
docker run -dit --name gep-gig_redis_1 -p 6379:6379 redis:5.0.6
docker run -dit --name gep-gig_mysql_1 -p 3306:3306 -e MYSQL_ROOT_PASSWORD={{root설정비번}} -e TZ=Asia/Seoul mysql:8.0.19
예) docker run -dit --name gep-gig_sqs_1 -p 9324:9324 -p 9325:9325 -v C:/gep/gep-gig/config/elasticmq.conf:/opt/config/elasticmq.conf roribio16/alpine-sqs
```

### aws config 파일 생성

1. 경로

- 폴더 경로 : C:\사용자\Administrator\\.aws
- 파일명 : config

2. 내용

```
[default]
region = ap-northeast-2
```

### aws credential 파일 생성

1. 경로

- 폴더 경로 : C:\사용자\Administrator\\.aws
- 파일명 : credentials

2. 내용

```
[default]
aws_access_key_id = 키id값
aws_secret_access_key = 키값
```


### Tailwind CSS
1 . 지원 명령어

```
- npm run start // scripts를 통한 파일 감시하여 실시간 변한 부분 dist.css 생성
- npm run build // scripts를 통한 1회 빌드하여 dist.css 생성
```

2 . 핵심 설정파일

```
- /tailwind.xe.css (tailwind 문법외 별도 css작성.)
- /tailwind.xe.json (tailwind color, bg등 system 설정.)
- 두 파일 tailwind.xe.json, tailwind.xe.css는 항상 작업 프로젝트 루트에 존재해야함.
```

