CREATE DATABASE PANDACUB;

USE PANDACUB;

CREATE TABLE CLIENTE(
    ID          INTEGER AUTO_INCREMENT,
    NOME        VARCHAR(40),
    CPF	        VARCHAR(12) NOT NULL,
    RG          VARCHAR(10),
    CREDITO     DOUBLE,
    NASCIMENTO  DATE,
    SEXO        INTEGER,
    ATIVO       INTEGER,
    CEP         VARCHAR(10),
    ESTADO      VARCHAR(20),
    CIDADE      VARCHAR(30),
    BAIRRO      VARCHAR(30),
    RUA         VARCHAR(30),
    NUMERO      VARCHAR(10),
    COMPLEMENTO VARCHAR(40),
    TELCELULAR  VARCHAR(16),
    TELRESIDENCIAL  VARCHAR(16),
    EMAIL       VARCHAR(30),

    PRIMARY KEY (ID),
    UNIQUE KEY CONSTRAINT_CPF_CLIENT (CPF)
);

CREATE TABLE FUNCIONARIO(
    ID          INTEGER AUTO_INCREMENT,
    NOME    VARCHAR(40),
    CPF     VARCHAR(12) NOT NULL,
    RG      VARCHAR(10),
    NASCIMENTO  DATE,
    SEXO    INTEGER,
    ATIVO   INTEGER,
    CEP     VARCHAR(10),
    ESTADO      VARCHAR(20),
    CIDADE      VARCHAR(30),
    BAIRRO  VARCHAR(30),
    RUA     VARCHAR(30),
    NUMERO      VARCHAR(10),
    COMPLEMENTO VARCHAR(40),
    TELCELULAR  VARCHAR(16),
    TELRESIDENCIAL  VARCHAR(16),
    EMAIL   VARCHAR(30),
    LOGIN   VARCHAR(100),
    SENHA   VARCHAR(100),
    CARGO   INTEGER,

    PRIMARY KEY (ID),
    UNIQUE KEY CONSTRAINT_CPF_FUNC (CPF)
);

CREATE TABLE CATEGORIA(
    ID 	     INTEGER AUTO_INCREMENT,
    NOME     	VARCHAR(20),
	ATIVO      INTEGER,

    PRIMARY KEY (ID)
);

CREATE TABLE REGISTROPRODUTO(
    ID 	    	INTEGER AUTO_INCREMENT,
    CODIGO  		VARCHAR(14),
    NOME	    	VARCHAR(30),
    DESCRICAO   	VARCHAR(255),
    IMAGEM	    	VARCHAR(255),
    ATIVO	    	INTEGER,
    CATEGORIA   	INTEGER,

    PRIMARY KEY (ID),
    FOREIGN KEY (CATEGORIA) REFERENCES CATEGORIA (ID),
    UNIQUE KEY CONSTRAINT_CODIGO_PROD (CODIGO)
);

CREATE TABLE FORNECEDOR(
    ID   INTEGER AUTO_INCREMENT,
    NOME    	VARCHAR(30),
    CNPJ    	    VARCHAR(15),
    TELCELULAR	    VARCHAR(16),
    TELRESIDENCIAL    VARCHAR(16),
    EMAIL	    	VARCHAR(30),
    ATIVO	    	INTEGER,

    PRIMARY KEY (ID),
    UNIQUE KEY CONSTRAINT_CNPJ (CNPJ)
);

CREATE TABLE LOTE(
    ID  	INTEGER AUTO_INCREMENT,
    FORNECEDOR    	INTEGER NOT NULL,
    DATALOTE    	DATE,
    VALOR	    DOUBLE,
    LUCRO	    DOUBLE,
    ATIVO    INTEGER,

    PRIMARY KEY (ID),
    FOREIGN KEY (FORNECEDOR) REFERENCES FORNECEDOR (ID)
);

CREATE TABLE PRODUTO(
    ID	    	INTEGER AUTO_INCREMENT,
    REGISTRO    	INTEGER,
    LOTE	    	INTEGER,
    VALOR	    	DOUBLE,
    QUANTIDADEINICIAL  	INTEGER,
    QUANTIDADEATUAL     INTEGER,
    ATIVO	    	INTEGER,

    PRIMARY KEY (ID),
    FOREIGN KEY (REGISTRO) REFERENCES REGISTROPRODUTO(ID),
    FOREIGN KEY (LOTE) REFERENCES LOTE (ID)
);

CREATE TABLE VENDA(
    ID 		    INTEGER AUTO_INCREMENT,
    FUNCIONARIO	    INTEGER,
    CLIENTE	    	INTEGER,
    DATAVENDA   		DATE,
    DESCONTO    	DOUBLE,

    PRIMARY KEY(ID),
    FOREIGN KEY (FUNCIONARIO) REFERENCES FUNCIONARIO (ID),
    FOREIGN KEY (CLIENTE) REFERENCES CLIENTE (ID)
);

CREATE TABLE PRODUTOVENDIDO(
    VENDA		     INTEGER,
    PRODUTO	     INTEGER,
    QUANTIDADE  	 INTEGER,

    PRIMARY KEY (VENDA, PRODUTO),
    FOREIGN KEY (VENDA) REFERENCES VENDA (ID),
    FOREIGN KEY (PRODUTO) REFERENCES PRODUTO(ID)
);

CREATE TABLE PAGAMENTO(
    DATAPAG	    	DATE,
    VENDA 		    INTEGER,
    VALOR		    DOUBLE,
    ATIVO		    INTEGER,

    PRIMARY KEY(DATAPAG, VENDA),
    FOREIGN KEY (VENDA) REFERENCES VENDA(ID)
);
