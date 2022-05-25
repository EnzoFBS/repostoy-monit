
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
    usoMemoria varchar(45),
    usoCpu varchar(45),
    temposAtividade varchar(45),
    dataRegistro datetime
);
