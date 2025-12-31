CREATE TABLE KEN (
    県コード INT PRIMARY KEY,
    県名 VARCHAR(20),
    人口 INT,
    人口22 INT,
    面積 DECIMAL(10, 2),
    世帯数 INT,
    世帯数22 INT
);

CREATE TABLE SHI (
    県コード INT,
    市コード INT PRIMARY KEY,
    区分 INT,
    市名 VARCHAR(50),
    人口 INT,
    人口22 INT,
    面積 DECIMAL(10, 2),
    世帯数 INT,
    世帯数22 INT,
    FOREIGN KEY (県コード) REFERENCES KEN(県コード)
);

CREATE TABLE POKEMON (
    番号 INT PRIMARY KEY,
    名前 VARCHAR(50)
);

CREATE TABLE TYPE (
    番号 INT,
    タイプ VARCHAR(20),
    FOREIGN KEY (番号) REFERENCES POKEMON(番号)
);

CREATE TABLE APPEAR (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    番号 INT,
    市コード INT,
    日付 DATE,
    時刻 TIME,
    FOREIGN KEY (番号) REFERENCES POKEMON(番号),
    FOREIGN KEY (市コード) REFERENCES SHI(市コード)
);