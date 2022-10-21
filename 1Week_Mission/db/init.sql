# 테스트 DB 생성
DROP DATABASE IF EXISTS mutbooks__test;
CREATE DATABASE fmutbooks__test;
USE mutbooks__test;

# 개발 DB 생성
DROP DATABASE IF EXISTS mutbooks__dev;
CREATE DATABASE mutbooks__dev;
USE mutbooks__dev;