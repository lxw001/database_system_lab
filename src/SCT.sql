#建数据库
create database SCT;
use SCT;
#建表
create table Student(`S#` char(8) Primary key, Sname char(10), Ssex char(2), Sage integer, SClass char(6));
create table Course(`C#` char(4) Primary key, Cname char(30), Credit float(1) , Chours integer, `T#` char(3));
create table SC(`S#` char(8) REFERENCES student(`S#`), `C#` char(4) REFERENCES course(`C#`), Score float(1));

