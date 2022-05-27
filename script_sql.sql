
create table if not exists totem(
	idTotem int not null primary key auto_increment,
	hostname varchar(45),
    sistema varchar(45),
    frequenciaCpu varchar(45),
    memoria float,
    fabricante varchar(45),
    modeloCpu varchar(45),
    ipTotem varchar(45),
    dataRegistro datetime,
    reiniciarTotem int,
    fk_empresa int
	
);

create table if not exists registro(
	idRegistro int primary key auto_increment,
    usoMemoria decimal(10,2) not null,
    usoCpu decimal(10,2) not null,
    tempoAtividade varchar(45) not null,
    dataRegistro datetime
);
