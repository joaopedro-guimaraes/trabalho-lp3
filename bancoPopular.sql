/*insert categoria*/
INSERT INTO CATEGORIA (NOME, ATIVO) VALUES ("PERFUMES", true);

INSERT INTO CATEGORIA (NOME, ATIVO) VALUES ("MAQUIAGEM", true);

INSERT INTO CATEGORIA (NOME, ATIVO) VALUES ("CAMISAS", true);

INSERT INTO CATEGORIA (NOME, ATIVO) VALUES ("SAPATOS", true);

INSERT INTO CATEGORIA (NOME, ATIVO) VALUES ("CALÇAS", true);

/*insert funcionario*/
INSERT INTO FUNCIONARIO (NOME, CPF, RG, NASCIMENTO, SEXO, ATIVO, CEP, ESTADO, CIDADE, BAIRRO, RUA, NUMERO,
 COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL, LOGIN, SENHA, CARGO) VALUES ("JAILSON", "12345678910",
 "1111111111", "1980-01-25", true, true, "56465454", "SP", "ARARAQUARA", "PACAEMBU", "SÃO MARCOS", "548",
 "", "16-954564884", "16-546848854", "JAILSON@VERDAO", "1", "1", true);


INSERT INTO FUNCIONARIO (NOME, CPF, RG, NASCIMENTO, SEXO, ATIVO, CEP, ESTADO, CIDADE, BAIRRO, RUA, NUMERO,
 COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL, LOGIN, SENHA, CARGO) VALUES ("GABRIEL JESUS", "12545648910",
 "33333333", "1995-01-25", true, true, "56465454", "SP", "SÃO PAULO", "VILA PERI", "COPA DA RUSSIA",
	"548", "", "16-97825884", "16-546848854", "JESUSSALVA@VERDAO", "1", "1", true);

INSERT INTO FUNCIONARIO (NOME, CPF, RG, NASCIMENTO, SEXO, ATIVO, CEP, ESTADO, CIDADE, BAIRRO, RUA, NUMERO,
 COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL, LOGIN, SENHA, CARGO) VALUES ("WILLIAN BIGODE", "1111111",
 "22222222", "1990-01-25", true, true, "56465454", "SP", "SÃO CARLOS", "CENTRO", "AV. SÃO CARLOS", "148",
 "", "16-987878784", "16-1258848854", "CHUPACURINTHIA@VERDAO", "1", "1", true);


/*insert cliente*/ 
 INSERT INTO CLIENTE (NOME, CPF, RG, NASCIMENTO, SEXO, CREDITO, ATIVO, CEP, ESTADO, CIDADE, BAIRRO, RUA,
	NUMERO, COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL) VALUES ("CASIO", "171171171", "44444444",
	 "1990-10-25", true, 5648, true, "56465454", "SP", "ITAQUERA", "CENTRO", "GAVIÕES", "45648", "", 
		"16-987878784", "16-1258848854", "VAICURINTHIA@ITAQUERA");

 INSERT INTO CLIENTE (NOME, CPF, RG, NASCIMENTO, SEXO, CREDITO, ATIVO, CEP, ESTADO, CIDADE, BAIRRO, RUA,
	NUMERO, COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL) VALUES ("ROGERIO CENI", "000000", "555555",
	 "1890-10-25", true, 10, true, "56465454", "GO", "GOIANIA", "MORUMBI", "VOLTA MURICY", "8977897", "", 
		"16-546887", "16-1258812174", "ROGERIO@SP");

/*INSERT VENDA*/

INSERT INTO VENDA (FUNCIONARIO, CLIENTE, DATAVENDA, DESCONTO) VALUES (1, 2, "2018-01-28", 0);

INSERT INTO VENDA (FUNCIONARIO, CLIENTE, DATAVENDA, DESCONTO) VALUES (2, 2, "2018-05-28", 0);

INSERT INTO VENDA (FUNCIONARIO, CLIENTE, DATAVENDA, DESCONTO) VALUES (3, 1, "2018-04-25", 0);

/* insert produto */

INSERT INTO PRODUTO (REGISTRO, LOTE, VALOR, QUANTIDADEINICIAL, QUANTIDADEATUAL, ATIVO) 
	VALUES (1, 1, 10, 2, 2, 1);

/*INSERT REGISTRO */

INSERT INTO REGISTROPRODUTO (CODIGO, NOME, DESCRICAO, IMAGEM, ATIVO, CATEGORIA) 
	VALUES (10, "tv", "TV LED", "LOCAL", 1, 1);

/* INSERT LOTE */

INSERT INTO LOTE (FORNECEDOR, DATALOTE, VALOR, LUCRO, ATIVO) 
	VALUES (1, "2018-01-28", 100, 1, 1);

/* INSERT FORNECEDOR */

INSERT INTO FORNECEDOR (NOME, CNPJ, TELCELULAR, TELRESIDENCIAL, EMAIL, ATIVO) 
	VALUES ("AVON", "1234-56", "16 123", "16 456", "AVON@GMAIL", 1);
