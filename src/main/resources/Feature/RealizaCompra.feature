#language:pt

@Loja
Funcionalidade: Comprar Item

@CT01
Esquema do Cenário: Adicionar um item no carrinho e realizar a compra
	Dado navegador aberto na "<aplicacao>" web
	E "<item>" encontrado
	Quando preencho os dados do item com "<quantidade>", "<tamanho>", "<cor>"
	E adiciono ao carrinho
	E crio uma conta com "<email>", "<nome>", "<sobrenome>", "<senha>", "<endereco>", "<cidade>", "<estado>", "<cep>", "<pais>", "<celular>", "<referencia>"
	E realizo o pagamento via cartao de credito
	Entao visualizo a mensagem de sucesso

Exemplos:
	| aplicacao 			 				  | item							| quantidade | tamanho | cor  | email           | nome     | sobrenome  | senha  | endereco  				  | cidade   | estado   | cep   | pais          | celular        | referencia  |
	| http://automationpractice.com/index.php | Faded Short Sleeve T-shirts 	| 2 		 | M 	   | Blue | tasseaae@auto.com | Pericles | Feltrin    | 123456 | 577 Flatbush Ave, Brooklyn | New York | New York | 11226 | United States | +5555999999999 | Ref. Addres |