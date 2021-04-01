IF EXISTS(select * from sysobjects where name=Testtabella) drop table Testtabella;
CREATE TABLE Testtabella(Chiaveprimariatest INT NOT NULL IDENTITY(1,1),Varchartest VARCHAR(20) NOT NULL,Integertest INT NOT NULL,PRIMARY KEY (Chiaveprimariatest));
