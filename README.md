# Grafos - Rede de Esgoto
Implementação de Grafos para Representação de Rede de Esgotos

## Como rodar?

O código principal foi desenvolvido em Java, além de um visualizador para exibir os grafos usando Html e Javascript.

Tudo foi rodado e testando apenas no Windows 10, mas instalando as dependências equivalentes deve rodar normalmente em ambiente unix.

Foi instalado o Java 17, usando o comando winget no powershell
~~~
winget install -e --id Oracle.JDK.17
~~~
O Html foi testado na versão 103 do Google Chrome e na versão 103 do Microsoft Edge.

Para compilar e rodar basta executar os seguintes comandos no terminal
~~~
javac -d ./bin ./src/Main.java ./src/Vertex.java ./src/Edge.java ./src/Graph.java
java -cp bin Main
~~~
Também é possível rodar utilizando o makefile, a versão para windows está disponível no utilitário MinGW:
~~~
https://sourceforge.net/projects/mingw/
~~~
Após a seguir todos os passos corretamente da instalação e configuração, é possível usar o comando make no windows normalmente.
~~~javascript
// para compilar e executar
make runwin
// apenas para executar
make run
~~~
A linha de comando irá perguntar o nome do arquivo de grafo a ser carregado. Basta digitar o nome de um dos arquivos disponíveis na pasta 'data', ou teclar enter, para usar o arquivo 'demo.txt', o grafo de saída será salvo com o mesmo nome, mas na pasta 'dataout'.