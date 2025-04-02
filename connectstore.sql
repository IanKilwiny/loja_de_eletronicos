-- Tabela base: Pessoa
CREATE DATABASE ConnectStore;
/*Modificado*/
use ConnectStore;

CREATE TABLE cliente(
	id_cliente int primary key auto_increment,
    nome varchar(50) not null,
    cpf char(11) unique not null,
    email varchar(20) not null,
    senha char(8) not null,
    telefone char(10),
    rua varchar(20),
    bairro varchar(20),
    endereco int
);

CREATE TABLE funcionario(
	id_funcionario int primary key auto_increment,
    nome varchar(50) not null,
    matricula varchar(11) unique not null,
    salario double not null,
    idade int not null,
    telefone char(10),
    hora_entrada time not null,
    hora_saida time not null,
    setor varchar(50) not null
);


CREATE TABLE produto(
	id_produto int primary key auto_increment,
    nome varchar(20) not null,
    preco double not null,
    marca varchar(20) not null,
    modelo varchar(10) not null,
    descricao text not null,
    tipo varchar(10),
    estoque int default 0
);



CREATE TABLE carrinho_produto (
	id_carrinho_produto int primary key auto_increment,
    fk_produto int,
    fk_cliente int,
    data_insercao date,
    quantidade int default 1,
    FOREIGN KEY (fk_produto) REFERENCES produto(id_produto) ON UPDATE SET NULL ON DELETE CASCADE,
    FOREIGN KEY (fk_cliente) REFERENCES cliente(id_cliente) ON UPDATE SET NULL ON DELETE CASCADE
);

CREATE TABLE venda(
	id_venda int primary key auto_increment, 
    data_venda date not null,
	fk_cliente int,
    fk_funcionario int,
    FOREIGN KEY (fk_cliente) REFERENCES cliente(id_cliente) ON UPDATE SET NULL ON DELETE CASCADE,
	FOREIGN KEY (fk_funcionario) REFERENCES funcionario(id_funcionario) ON UPDATE SET NULL ON DELETE CASCADE 
);

CREATE TABLE venda_produtos (
	id_venda_produto int primary key auto_increment,
    qtd int not null,
    fk_produto int,
    fk_venda int,
    FOREIGN KEY (fk_produto) REFERENCES produto(id_produto) ON UPDATE SET NULL ON DELETE CASCADE,
    FOREIGN KEY (fk_venda) REFERENCES venda(id_venda) ON UPDATE SET NULL ON DELETE CASCADE
	
);


/*INSERÇÃO DE CLIENTE*/
INSERT INTO cliente (nome, cpf, email, senha, telefone, rua, bairro, endereco) VALUES
('Ana Silva', '12345678901', 'ana@email.com', 'senha123', '1199999999', 'Rua das Flores', 'Centro', 101),
('Bruno Costa', '23456789012', 'bruno@email.com', 'abc12345', '2198888888', 'Av. Brasil', 'Jardins', 202),
('Carla Mendes', '34567890123', 'carla@email.com', 'carla888', '3197777777', 'Rua A', 'Vila Nova', 303),
('Daniel Rocha', '45678901234', 'daniel@email.com', 'dani2024', '4196666666', 'Rua B', 'Copacabana', 404),
('Eduarda Lima', '56789012345', 'eduarda@email.com', 'edu12345', '5195555555', 'Av. Paulista', 'Liberdade', 505);


/*INSERÇÃO DE PRODUTO*/
INSERT INTO produto (nome, preco, marca, modelo, descricao, tipo, estoque) VALUES
('Galaxy S21', 3499.90, 'Samsung', 'S21', 'Celular com 128GB, câmera tripla', 'Celular', 10),
('Aspire 5', 2999.00, 'Acer', 'A515', 'Notebook com i5, 8GB RAM, SSD 256GB', 'Notebook', 5),
('Smart TV 50"', 2799.99, 'LG', '50UQ', 'Smart TV 4K UHD com WebOS', 'Televisão', 8),
('Fone Bluetooth JBL', 349.00, 'JBL', 'Tune500BT', 'Fone de ouvido Bluetooth com graves potentes', 'Fone', 15),
('Galaxy Tab A8', 1499.00, 'Samsung', 'A8', 'Tablet com tela de 10.5", 64GB', 'Tablet', 12);

INSERT INTO funcionario(nome, matricula, salario, telefone, idade, hora_entrada, hora_saida, setor)
VALUES ("Ian Kilwiny", "1122321925", 2500, "8825263233", 23,"08:00:00", "17:00:00", "Atendimento");


INSERT INTO funcionario(nome, matricula, salario, telefone, idade, hora_entrada, hora_saida, setor)
VALUES ("Pedro da Silva", "1234567899", 2500, "8825263233", 23,"08:00:00", "17:00:00", "Atendimento"),
("Carlos Daniel", "2244557822", 2500, "99257522",32, "9:00:22", "19:22:23", "Atendimento");


/*Compra*/
INSERT INTO venda(data_venda, fk_cliente, fk_funcionario) VALUES ("2025-04-02",2,1);
INSERT INTO venda_produtos(qtd, fk_produto, fk_venda) VALUES (2,4,1);
INSERT INTO venda_produtos(qtd, fk_produto, fk_venda) VALUES (1,1,1);
INSERT INTO venda_produtos(qtd, fk_produto, fk_venda) VALUES (1,1,1);

INSERT INTO venda(data_venda, fk_cliente, fk_funcionario) VALUES ("2024-08-10",3,2);
INSERT INTO venda_produtos(qtd, fk_produto, fk_venda) VALUES (3,4,2);
INSERT INTO venda_produtos(qtd, fk_produto, fk_venda) VALUES (1,3,2);


/*inner join correto*/
SELECT cl.nome,count(pr.nome) AS qtd_produtos, vd.data_venda
FROM cliente cl
INNER JOIN venda vd ON cl.id_cliente = vd.fk_cliente
INNER JOIN venda_produtos vdp ON vd.id_venda = vdp.fk_venda
INNER JOIN produto pr ON vdp.fk_produto = pr.id_produto
group by cl.nome;

SELECT 
    fun.nome AS "Funcionário", 
    cl.nome AS "Cliente", 
    COUNT(vdp.fk_produto) AS "Quantidade de Produtos"
FROM funcionario fun
INNER JOIN venda vd ON fun.id_funcionario = vd.fk_funcionario
INNER JOIN cliente cl ON vd.fk_cliente = cl.id_cliente
INNER JOIN venda_produtos vdp ON vd.id_venda = vdp.fk_venda
INNER JOIN produto pr ON vdp.fk_produto = pr.id_produto
GROUP BY cl.nome;



select *from venda_produtos;

select *from funcionario;

select *from venda;

