# Grafos - Rede Esgoto
Implementação de Grafos para Representação de Rede de Esgotos

## Como rodar?

O código principal foi desenvolvido em Java, além de um visualizador para exibir os grafos usando Html e Javascript.

Tudo foi rodado e testando apenas no Windows 10, mas instalando as dependências equivalentes deve rodar normalmente em ambiente unix.

Foi instalado o Java 17, usando o comando winget no powershell
~~~
winget install -e --id Oracle.JDK.17
~~~
E o Html foi testado na versão 103 do Google Chrome e na versão 103 do Microsoft Edge.

Para compilar e rodar basta executar os seguintes comandos no terminal
~~~
javac -d ./bin ./src/Main.java
java -cp bin Main
~~~
Também é possível executar o comando utilizando o 
Para rodar foi utilizado o utilitário makefile, a versão para windows está disponível no utilitário MSYS2:
~~~
https://www.msys2.org/
~~~
Após a seguir todos os passo corretamente da instalação é possível usar o comando make no windows.
