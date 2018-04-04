DROP SCHEMA IF EXISTS SafeStay;
CREATE SCHEMA IF NOT EXISTS SafeStay;
USE SafeStay;

DROP TABLE IF EXISTS Requests;
DROP TABLE IF EXISTS Maintains;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Recommendation;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS EndUsers;
DROP TABLE IF EXISTS User_address_log;
DROP TABLE IF EXISTS Administrators;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Incidents;
DROP TABLE IF EXISTS Offense;
 
 CREATE TABLE Users(
UserName VARCHAR(255) NOT NULL,
UserPassword VARCHAR(255) NOT NULL,
FirstName VARCHAR(255) NOT NULL,
LastName VARCHAR(255) NOT NULL,
Age INT NOT NULL,
Email VARCHAR(255) NOT NULL,
Phone VARCHAR(10) NOT NULL,
CONSTRAINT pk_Users_UserName PRIMARY KEY (UserName)
 );
 
 CREATE TABLE Administrators (
UserName VARCHAR(255) NOT NULL,
LastLogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_Administrators_UserName
PRIMARY KEY (UserName),
CONSTRAINT fk_Administrators_UserName
FOREIGN KEY (UserName)
REFERENCES Users(UserName)
ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE EndUsers (
UserName VARCHAR(255), 
DateOfBirth Date,
CONSTRAINT pk_BlogUsers_UserName
PRIMARY KEY (UserName),
CONSTRAINT fk_BlogUsers_UserName
FOREIGN KEY (UserName)
REFERENCES Users(UserName)
ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Offense(
OffenseCode INT,
Description VARCHAR(255) NOT NULL,
CONSTRAINT pk_Offense_OffenseCode PRIMARY KEY(OffenseCode)
);

CREATE TABLE Incidents(
IncidentId INT AUTO_INCREMENT,
OffenseCode INT,
District VARCHAR(255) NOT NULL,
ReportingArea INT NOT NULL,
Shooting ENUM('Y','N'),
OccuredOnDate TIMESTAMP NOT NULL,
DayOfWeek VARCHAR(255) NOT NULL,
Hours INT NOT NULL,
UCR ENUM('PartOne','PartTwo', 'PartThree') NOT NULL,
Location VARCHAR(255) NOT NULL,
CONSTRAINT pk_Incidents_IncidentId PRIMARY KEY(IncidentId),
CONSTRAINT fk_Incidents_Offense FOREIGN KEY (OffenseCode) REFERENCES Offense(OffenseCode)
);


CREATE TABLE Maintains(
UserName VARCHAR(255),
IncidentId INT NOT NULL,
Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
CONSTRAINT pk_Maintains_UserName PRIMARY KEY(UserName),
CONSTRAINT fk_Maintains_UserName FOREIGN KEY(UserName) REFERENCES Administrators(UserName),
CONSTRAINT fk_Maintains_IncidentId FOREIGN KEY(IncidentId) REFERENCES Incidents(IncidentId)
);

CREATE TABLE Comments(
CommentId INT AUTO_INCREMENT,
UserName VARCHAR(255) NOT NULL,
IncidentId INT NOT NULL,
CONSTRAINT pk_Comments_CommentId PRIMARY KEY(CommentId),
CONSTRAINT fk_Comments_UserName FOREIGN KEY(UserName) REFERENCES EndUsers(UserName),
CONSTRAINT fk_Comments_IncidentId FOREIGN KEY(IncidentId) REFERENCES Incidents(IncidentId)
);

CREATE TABLE Address(
Location VARCHAR(255)NOT NULL,
Street VARCHAR(255) NOT NULL,
Latitude DECIMAL(10,8) NOT NULL,
Longitude DECIMAL(10,8) NOT NULL,
CONSTRAINT pk_Address_Location PRIMARY KEY(Location)
);

drop table if exists review;
CREATE TABLE Review(
ReviewId INTEGER AUTO_INCREMENT,
Content VARCHAR(255) NOT NULL,
Username VARCHAR(255) NOT NULL,
Location VARCHAR(255) NOT NULL,
CONSTRAINT pk_Review_ReviewId PRIMARY KEY(ReviewId),
constraint fk_Review_Username foreign key (Username) references endusers(username),
CONSTRAINT fk_Review_Location FOREIGN KEY (Location) REFERENCES Address(Location)
);

DROP TABLE IF EXISTS recommendation;
CREATE TABLE Recommendation(
RecommendationId INTEGER AUTO_INCREMENT,
Rating DECIMAL(2,1) NOT NULL,
PetFriendly DECIMAL(2,1) NOT NULL,
ChildFriendly DECIMAL(2,1) NOT NULL,
UserName VARCHAR(255) NOT NULL,
Location VARCHAR(255) NOT NULL,
CONSTRAINT pk_Recommendation_RecommendationId PRIMARY KEY(RecommendationId),
CONSTRAINT fk_Recommendation_Location FOREIGN KEY (Location) REFERENCES Address(Location),
CONSTRAINT fk_UserName FOREIGN KEY (UserName) REFERENCES EndUsers(UserName)
);

CREATE TABLE Requests(
RequestId INT AUTO_INCREMENT,
UserName VARCHAR(255),
Location VARCHAR(255),
CONSTRAINT pk_Requests_RequestId PRIMARY KEY(RequestID),
CONSTRAINT fk_Requests_USerName FOREIGN KEY(UserName) REFERENCES EndUsers(UserName)
);

CREATE TABLE User_address_log(
User_address_id INT auto_increment,
UserName VARCHAR(255),
Location VARCHAR(255),
Start_date DATE,
End_date DATE,
CONSTRAINT ual_primary_key PRIMARY KEY (User_address_id),
CONSTRAINT ual_foreign_key FOREIGN KEY (UserName)
REFERENCES endusers(UserName),
CONSTRAINT ual_foreign_key_location FOREIGN KEY (Location)
REFERENCES Address(Location)
);




/*
LOAD DATA LOCAL INFILE 'C:/Users/Prachi/Desktop/DBMS/Offense.csv' 
INTO TABLE Offense 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Prachi/Desktop/DBMS/NewIncidents.csv' 
INTO TABLE Incidents 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,DayOfWeek,Hours,UCR,Location);


LOAD DATA LOCAL INFILE 'C:/Users/Prachi/Desktop/DBMS/Address.csv' 
INTO TABLE Address 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

SELECT SUM(TABLE_ROWS) 
     FROM INFORMATION_SCHEMA.TABLES 
     WHERE TABLE_SCHEMA = 'SafeStay';
     
insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('prachi','prachi','Prachi','Ved',23,'ved.p@husky.neu.edu',2384729383);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('sonal','singh','Sonal','Singh',23,'singh.son@husky.neu.edu',2384729384);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('grishma','grishma','Grishma','Thakkar',23,'thakkar.g@husky.neu.edu',2384729385);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('oneil','oneil','ONeil','Contracctor',23,'contracctor.o@husky.neu.edu',2384729386);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('shyam','padia','Shyam','Padia',23,'padia.s@husky.neu.edu',2384792387);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('kartik','dave','Kartik','Dave',23,'dave.ka@husky.neu.edu',2384729388);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('trachi','trachi','Trachi','Ved',23,'ved.t@husky.neu.edu',2384729393);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('monal','singh','Monal','Singh',23,'singh.mon@husky.neu.edu',2384729394);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('Brishma','brishma','Grishma','Thakkar',23,'thakkar.b@husky.neu.edu',2384729395);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('neil','neil','Neil','Contracctor',23,'contracctor.n@husky.neu.edu',2384729396);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('ram','padia','Ram','Padia',23,'padia.r@husky.neu.edu',2384792397);

insert into users(Username,UserPassword,FirstName,LastName,Age,Email,Phone)
values('Fartik','dave','Fartik','Dave',23,'dave.fa@husky.neu.edu',2384729388);



insert into endusers(username,dob)
values('prachi','1994-01-01');

insert into endusers(username,dob)
values('sonal','1994-01-02');

insert into endusers(username,dob)
values('grishma','1994-01-03');

insert into endusers(username,dob)
values('oneil','1994-01-04');

insert into endusers(username,dob)
values('kartik','1994-01-05');

insert into endusers(username,dob)
values('shyam','1994-01-06');

insert into endusers(username,dob)
values('trachi','1994-01-07');

insert into endusers(username,dob)
values('monal','1994-01-08');

insert into endusers(username,dob)
values('brishma','1994-01-09');

insert into endusers(username,dob)
values('neil','1994-01-10');

insert into recommendation (rating,petfriendly,childfriendly,username,location)
values(4.0,3.0,3.0,'prachi','(42.23241330, -71.12971531)');

insert into recommendation (rating,petfriendly,childfriendly,username,location)
values(4.0,5.0,5.0,'sonal','(42.23265556, -71.13069992)');

insert into recommendation (rating,petfriendly,childfriendly,username,location)
values(5.0,5.0,5.0,'grishma','(42.23287025, -71.13004959)');

insert into recommendation (rating,petfriendly,childfriendly,username,location)
values(5.0,4.0,5.0,'oneil','(42.23290729, -71.13167059)');

insert into recommendation (rating,petfriendly,childfriendly,username,location)
values(5.0,4.5,5.0,'shyam','(42.23308580, -71.12815697)');

insert into review (content,username,location)
values('Its a safe area','prachi','(42.23241330, -71.12971531)');

insert into review (content,username,location)
values('Pet friendly area','sonal','(42.23265556, -71.13069992)');

insert into review (content,username,location)
values('Get restaurants in the area','grishma','(42.23312147, -71.13102697)');

insert into review (content,username,location)
values('This area is unsafe. Lots of mugging incidents','oneil','(42.23315741, -71.13265354)');

insert into review (content,username,location)
values('Its a good and peaceful area','shyam','(42.23334151, -71.13368781)');


insert into requests(username,location)
values('prachi','(42.23334151, -71.13368781)');

insert into requests(username,location)
values('sonal','(42.23315741, -71.13265354)');

insert into requests(username,location)
values('grishma','(42.23315741, -71.13265354)');

insert into user_address_log(username,location,start_date,end_date)
values('prachi','(42.23241330, -71.12971531)','2015-09-21','2016-02-20');

insert into user_address_log(username,location,start_date,end_date)
values('sonal','(42.23290729, -71.13167059)','2015-09-21','2016-02-20');

insert into user_address_log(username,location,start_date,end_date)
values('grishma','(42.23241330, -71.12971531)','2015-09-21','2016-02-20');



#PM3 Queries:

#1.	Top 20 safest areas to live in.
select i.location ,count(*) from incidents i
left  join address a on 
i.Location = a.Location
group by i.Location
order by count(*)
limit 20;




select i.Location,a.Street, o.Description as Most_Occurred,
count(*) as CNT from offense as o
inner join incidents as i on o.OffenseCode = i.OffenseCode
inner join address as a on a.Location = i.Location
group by i.Location
order by CNT desc;

#2. In a Location, which offence occurs the most?
select i1.OffenseCode,i1.Location
from incidents i1
where i1.IncidentId=(  
select 
 i.IncidentId 
from incidents i 
where i.location = "(42.23241330, -71.12971531)"
group by i.OffenseCode
order by count(IncidentId) desc
limit 1);


#3. Ratio of total number of recommendations to reviews for Boston?

SELECT (SELECT COUNT(*) FROM recommendation) as totalRecommendations ,
 (SELECT COUNT(*) FROM review)as totalReviews,
 ((SELECT COUNT(*) FROM recommendation) / (SELECT COUNT(*) FROM review)) as ratio;


#4. Highest number of requests for a location? Percentage of those requests.
select a.Street, count(r.requestid) as HighestRequest,
(select count(*) from requests) as TotalRequests,
 (count(r.requestid) / (select count(*) from requests) * 100)
 as percentage 
 from requests r
inner join address a 
on r.location = a.Location
group by r.location;




#5.	Which day of the week are most incidents occuring?
select i.DayOfWeek as IncidentDay,
count(*) as IncidentsOccured
from incidents i
group by (i.DayOfWeek)
order by count(*)
desc
LIMIT 1;

#6.	What is the ratio of shooting incidents to the overall incidents occurring in an area?
select num/den as ratio, overall.location
from (select count(*) as num,i.Location from incidents i
where i.Shooting = 'Y'
group by i.location) as shooting inner join 
(select count(*) as den, i.location from incidents i
group by i.location) as overall
on overall.location = shooting.location; 

#7. Top 20 most unsafe streets?
select street,count(*) as incidentcnt from
incidents i inner join address a 
on i.Location = a.Location
where a.Street is not null
group by a.street
order by incidentcnt desc
limit 20;

#8.	Given a location, which hour is most unsafe?
select i1.Hours,i1.Location
from incidents i1
where i1.IncidentId=(  
select 
 i.IncidentId 
from incidents i 
where i.location = "(42.23241330, -71.12971531)"
group by i.Hours
order by count(IncidentId) desc
limit 1);


#9. Safety index of all areas. (Number of incidents in that area to total number of incidents)

select i.Location,a.Street,
(count(*)/(select count(*) from incidents)) as safetyIndex
from incidents i inner join address a
on i.Location = a.Location
group by i.Location;

#10. What percentage of users that have stayed in a location recommended the same location? 

DROP FUNCTION IF EXISTS resident_user;
DELIMITER //
CREATE FUNCTION resident_user(location varchar(255))
RETURNS INT 
BEGIN
RETURN (select count(username) from user_address_log  as ual where 
ual.location = location);
END; 
//
DELIMITER ;

DROP FUNCTION IF EXISTS recommendation_user;
DELIMITER //
CREATE FUNCTION recommendation_user(location varchar(255))
RETURNS INT 
BEGIN
RETURN ((select count(ual.username) from
user_address_log as ual inner join recommendation as r
on ual.username = r.username and
ual.location = r.Location
and ual.location = location
group by r.location));
END; 
//
DELIMITER ;

DROP FUNCTION IF EXISTS percentage;
DELIMITER //
CREATE FUNCTION percentage(location varchar(255))
RETURNS INT 
BEGIN
SET @resident_user = (select resident_user(location));
SET @recommendation_user = (select recommendation_user(location));
RETURN (@recommendation_user / @resident_user) * 100;
END; 
//
DELIMITER ;


##We plan to have a drop down menu listing every location with its name
# so that user will only give name of the location and get the percentage
select percentage('(42.23241330, -71.12971531)') as percent;
*/

select * from incidents;
select * from Offense;
select * from address;
select * from users;
select * from endusers;
select * from administrators;
select * from maintains;
select * from review;
select * from recommendation;
select * from requests;