CREATE DATABASE masterplayer;
USE masterplayer;


CREATE TABLE Usuario (
idUsuario INT NOT NULL AUTO_INCREMENT,
NomeUsuario VARCHAR(50),
EmailUsuario VARCHAR(50),
ApelidoUsuario VARCHAR(20),
SenhaUsuario VARCHAR(6),
DiretorioUsuario VARCHAR(100),
PRIMARY KEY (idUsuario)
);


CREATE TABLE Musica (
idMusica INT NOT NULL AUTO_INCREMENT,
Nome VARCHAR(50),
Artista VARCHAR(50),
Album VARCHAR(50),
Genero VARCHAR(50),
Ano INT,
DataCadastro DATE,
Caminho VARCHAR(200),
Usuario_idUsuario INT NOT NULL,
PRIMARY KEY (idMusica),  
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario (idUsuario) on delete cascade on update cascade
);


CREATE TABLE PlayList (
idPlayList INT NOT NULL AUTO_INCREMENT,
NomePlayList VARCHAR(20),
Usuario_idUsuario INT NOT NULL,
PRIMARY KEY (idPlayList),  
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario (idUsuario) on delete cascade on update cascade
);


CREATE TABLE Musica_has_PlayList (
Musica_idMusica INT NOT NULL,
PlayList_idPlayList INT,
PRIMARY KEY (Musica_idMusica, PlayList_idPlayList),  
FOREIGN KEY (Musica_idMusica) REFERENCES Musica (idMusica)on delete cascade on update cascade,
FOREIGN KEY (PlayList_idPlayList) REFERENCES PlayList (idPlayList)on delete cascade on update cascade
);


CREATE TABLE RegistroReproducao (
idRegistroReproducao INT NOT NULL AUTO_INCREMENT,
DataReprod DATE,  
Musica_idMusica INT NOT NULL,
PRIMARY KEY (idRegistroReproducao),  
FOREIGN KEY (Musica_idMusica) REFERENCES Musica (idMusica)on delete cascade on update cascade
);