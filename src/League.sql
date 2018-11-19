/*drop table Game_User1;
drop table Game_User2;
-- cascade delete from Game_User1
-- set null delete from Champion

drop table friendsWith;
-- cascade delete from Game_User1 and Game_User2

drop table normal_User;
-- cascade delete from Game_User1

drop table rank_User;
-- cascade delete from Game_User1

drop table clubs;
drop table Champion;

drop table owns;
-- cascade delete from Game_User1, Champions

drop table match_History;
-- set null delete from Game_User1, Champions

drop table Match1;
-- cascade delete from match_History

drop table Match2;
-- cascade set null from game_Mode1, cascade delete from match_1

drop table Game_Mode1;

drop table Game_Mode2;
-- cascade delete from Game_Mode1

drop table League_Employee1;
drop table League_Employee2;
-- on delete cascade from competitions
drop table League_Employee3;
-- on delete cascade from competitions

drop table Competitions;
-- cascade delete for some of its own rows

drop table Participation;
-- cascade delete from Clubs, Competitions

commit;*/


CREATE TABLE Game_User1 (username CHAR(16) PRIMARY KEY, loginname CHAR(16),
                         password CHAR(16));
grant select on Game_User1 to public;

CREATE TABLE Champion (championid INTEGER PRIMARY KEY, abilities CHAR(100),
                       items CHAR(200), backgroundstory CHAR(200));
grant select on Champion to public;

CREATE TABLE Game_User2 (username  CHAR(16) PRIMARY KEY, championid INTEGER,loginname CHAR(16),
  FOREIGN KEY (championid) REFERENCES Champion (championid) ON DELETE SET NULL);
grant select on Game_User2 to public;


CREATE TABLE friendsWith (username_friend1 CHAR(16), username_friend2 CHAR(16),
  PRIMARY KEY (username_friend1, username_friend2),
  FOREIGN KEY (username_friend1) REFERENCES Game_User1 (username)
  ON DELETE CASCADE,
  FOREIGN KEY (username_friend2) REFERENCES Game_User1 (username)
  ON DELETE CASCADE);

grant select on friendsWith to public;


CREATE TABLE normal_User (username CHAR(16) PRIMARY KEY, experiencelvl INTEGER,
  FOREIGN KEY (username) REFERENCES Game_User1(username)
  ON DELETE CASCADE);

grant select on normal_User to public;

CREATE TABLE clubs (clubid INTEGER PRIMARY KEY, clubname CHAR(16),
                    clubmanagerID INTEGER);

grant select on clubs to public;

CREATE TABLE rank_User (username CHAR(16) PRIMARY KEY, ranklvl integer,clubid INTEGER NOT NULL,
  FOREIGN KEY (username) REFERENCES Game_User1(username)
  ON DELETE CASCADE,
  FOREIGN KEY (clubid) REFERENCES Clubs (clubid) ON DELETE CASCADE);

grant select on rank_User to public;


CREATE TABLE Owns (username CHAR(16), championid INTEGER,
  PRIMARY KEY (username, championid),
  FOREIGN KEY (username) REFERENCES Game_User1 (username) ON DELETE CASCADE,
  FOREIGN KEY (championid) REFERENCES Champion (championid) ON DELETE CASCADE);

grant select on Owns to public;


CREATE TABLE Match_History (matchhistoryid INTEGER PRIMARY KEY, username CHAR(16),championid INTEGER,
  FOREIGN KEY (username) REFERENCES Game_User1 (username) ON DELETE SET NULL,
  FOREIGN KEY (championid) REFERENCES Champion (championid) ON DELETE SET NULL);

grant select on Match_History to public;

CREATE TABLE Game_Mode1 (modeid INTEGER PRIMARY KEY, type CHAR(16), map CHAR(16));
grant select on Game_Mode1 to public;

CREATE TABLE Game_Mode2 (map CHAR(16) PRIMARY KEY, playercount INTEGER);
grant select on Game_Mode2 to public;

CREATE TABLE Match1 (matchid INTEGER PRIMARY KEY, matchhistoryid INTEGER, datePlayed INTEGER, time INTEGER,
  FOREIGN KEY (matchhistoryid) REFERENCES Match_History (matchhistoryid) ON DELETE CASCADE);
grant select on Match1 to public;

CREATE TABLE Match2 (matchid INTEGER PRIMARY KEY, modeid INTEGER,
  FOREIGN KEY (modeid) REFERENCES Game_Mode1 (modeid) ON DELETE set null,
  FOREIGN KEY (matchid) references Match1 (matchid) ON DELETE CASCADE);

CREATE TABLE Competitions(eventname CHAR(16), seasonandyear INTEGER, location CHAR(16),
  PRIMARY KEY (eventname, seasonandyear));
grant select on Competitions to public;

CREATE TABLE League_Employee1 (employeeid INTEGER PRIMARY KEY, responsibility CHAR(16));
grant select on League_Employee1 to public;

CREATE TABLE League_Employee2 (responsibility CHAR(16) PRIMARY KEY, eventname CHAR(16),seasonandyear INTEGER,
  FOREIGN KEY (eventname,seasonandyear) REFERENCES Competitions (eventname,seasonandyear) ON DELETE CASCADE);
grant select on League_Employee2 to public;

CREATE TABLE League_Employee3  (responsibility CHAR(16) PRIMARY KEY, modeid INTEGER,
  FOREIGN KEY (modeid) REFERENCES Game_Mode1 ON delete set null );
grant select on League_Employee3 to public;

CREATE TABLE Participation (clubid INTEGER, eventname CHAR(16), seasonandyear INTEGER,
  PRIMARY KEY (clubid, eventname, seasonandyear),
  FOREIGN KEY (clubid) REFERENCES Clubs (clubid) ON delete CASCADE,
  FOREIGN KEY (eventname,seasonandyear) REFERENCES Competitions (eventname,seasonandyear) ON DELETE CASCADE);
grant select on Participation to public;

commit;

/*
number ranges:
modeID: 1,2,3
champion starts 100;
matchID starts 500;
clubID starts 1000;
managerId starts 5000:
matchHistoryID starts 10000;
EmployeeId starts 20000;
*/

-- Game_User1
insert into Game_User1 values('cylviaT','login_c','123abc');
insert into Game_User1 values('cloeM','login_cl','456def');
insert into Game_User1 values('jodhB','login_jo','789ghi');
insert into Game_User1 values('jayJ','login_ja','101jkl');

insert into Game_User1 values('joeyZ','login_joey','202yjdks');
insert into Game_User1 values('David','login_david','639jfeidlskdf');
insert into Game_User1 values('Crystal','login_crystal','485656earsfd');
insert into Game_User1 values('Kevin','login_kevin','11254fafdsed');
insert into Game_User1 values('Vivian','login_vivian','d34225rsff');
insert into Game_User1 values('Poppy','login_poppy','789gfsdgdr');
insert into Game_User1 values('Oris','login_oris','753945fdedhk');
insert into Game_User1 values('Fiona','login_fiona','6333fjlvms');



commit;

-- champions
insert into Champion values (100,'ab1','item1','backG1');
insert into Champion values (101,'ab2','item2','backG2');
insert into Champion values (102,'ab3','item3','backG3');
insert into Champion values (103,'ab4','item4','backG4');
commit;

-- clubs
insert into clubs values (1001,'club1',5000);
insert into clubs values (1002,'club2',5001);
insert into clubs values (1003,'club3',5002);
insert into clubs values (1004,'club4',5003);
commit;


-- Game_User2
insert into Game_User2 values('jayJ',100,'login_ja');
insert into Game_User2 values('cylviaT',101,'login_c');
insert into Game_User2 values('cloeM',102,'login_cl');
insert into Game_User2 values('jodhB',103,'login_jo');
commit;

-- friendsWith
insert into friendsWith values ('jayJ','cylviaT');
insert into friendsWith values ('cloeM','cylviaT');
insert into friendsWith values ('jayJ','jodhB');
insert into friendsWith values ('jodhB','cloeM');
commit;

-- normal_User
insert into normal_User values ('cloeM',15);
insert into normal_User values ('cylviaT',10);
insert into normal_User values ('Crystal',20);
insert into normal_User values ('Fiona',17);

commit;

-- rank_User
insert into rank_User values ('jayJ',50,1001);
insert into rank_User values ('jodhB',60,1002);
insert into rank_User values ('joeyZ',65,1003);
insert into rank_User values ('David',89,1004);

insert into rank_User values ('Oris',73,1001);
insert into rank_User values ('Poppy',83,1002);
insert into rank_User values ('Vivian',90,1003);
insert into rank_User values ('Kevin',64,1004);
commit;

-- owns
insert into Owns values ('jayJ',100);
insert into Owns values ('jayJ',101);
insert into Owns values ('jodhB',101);
insert into Owns values ('cloeM',103);
insert into Owns values ('cloeM',100);
insert into Owns values ('cylviaT',103);
insert into Owns values ('cylviaT',100);
insert into Owns values ('cylviaT',101);

commit;

--matchHistory
insert into Match_History values (10000,'jayJ',100);
insert into Match_History values (10001,'jodhB',100);
insert into Match_History values (10002,'cloeM',100);
insert into Match_History values (10003,'jayJ',101);
commit;

-- Game_Mode1
insert into Game_Mode1 values (2,'5V5','ARAM');
insert into Game_Mode1 values (1,'5V5','Normal');
insert into Game_Mode1 values (3,'3V3','Smallmap');
commit;

-- Game_Mode2
insert into Game_Mode2 values ('normal',5);
insert into Game_Mode2 values ('Smallmap',3);
commit;

-- Match1
insert into Match1 values (500,10001,20180110,1610);
insert into Match1 values (503,10000,20180706,1000);
insert into Match1 values (504,10005,20170912,1420);
insert into Match1 values (501,10002,20180111,1620);
insert into Match1 values (502,10003,20180210,1845);
commit;

-- Match2
insert into Match2 values (500,1);
insert into Match2 values (501,1);
insert into Match2 values (502,2);
commit;

-- Competitions
insert into Competitions values ('event1',2013,'NorthAmerica');
insert into Competitions values ('event2',2014,'Korea');
insert into Competitions values ('event3',2015,'NorthAmerica');
insert into Competitions values ('event4',2016,'Korea');
insert into Competitions values ('event5',2017,'Korea');
insert into Competitions values ('event6',2018,'NorthAmerica');

-- League_Employee1
insert into League_Employee1 values (20000,'Respon1');
insert into League_Employee1 values (20001,'Respon2');
commit;

-- League_Employee2
insert into League_Employee2 values ('Respon1','event1',2013);
insert into League_Employee2 values ('Respon2','event2',2014);
commit;

-- League_Employee3
insert into League_Employee3 values ('Respon1',1);
insert into League_Employee3 values ('Respon2',2);
commit;

-- Participation
insert into Participation values (1001,'event1',2013);
insert into Participation values (1001,'event2',2014);
insert into Participation values (1001,'event3',2015);
insert into Participation values (1001,'event4',2016);
insert into Participation values (1001,'event5',2017);

insert into Participation values (1002,'event2',2014);
insert into Participation values (1002,'event3',2015);
insert into Participation values (1002,'event4',2016);

insert into Participation values (1003,'event4',2016);
insert into Participation values (1003,'event5',2017);
insert into Participation values (1003,'event6',2018);

insert into Participation values (1004,'event6',2018);
























