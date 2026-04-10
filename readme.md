# BookFinder-Java - Google Books API

Aplicação CLI em Java para consulta de volumes através da API do Google Books. O projeto demonstra a integração de chamadas HTTP nativas do Java 21+, processamento de JSON com a biblioteca Gson e o mapeamento de dados estruturados utilizando Records e classes de domínio.

## Funcionalidades
- Pesquisa de obras por título via terminal.
- Retorno focado e otimizado: a requisição é limitada ao principal resultado da busca (`maxResults=1`).
- Consumo de API REST com tratamento de parâmetros de consulta (query strings).
- Extração seletiva e manual de dados de objetos JSON aninhados (sem uso de anotações como `@SerializedName`).
- Arquitetura baseada em DTOs (Data Transfer Objects) para desacoplamento de dados.

## Tecnologias
- Java 21+ (HttpClient, HttpRequest, HttpResponse).
- Google Gson (Parsing e manipulação direta de nós JSON).
- Google Books API.

## Estrutura do Projeto
O sistema está organizado em três componentes de dados:

- **Main.java**: Gerencia o fluxo de execução, desde o input do usuário até o envio da requisição. Realiza a navegação manual na árvore do JSON para extrair o primeiro e principal objeto `volumeInfo` e injetar os valores no Record.
- **ConverterVar.java (Record)**: Atua como o DTO do projeto. Armazena os dados brutos extraídos (title, authors, publishedDate) de forma imutável.
- **Livro.java (Classe)**: Classe de domínio que recebe o Record no construtor para inicializar o estado interno do objeto e definir a representação textual dos dados formatados.

## Configuração e Execução

### Configurando a API Key
Para que a aplicação se comunique corretamente com os servidores do Google, é necessário inserir a sua chave de acesso diretamente no código.

1. Abra o arquivo `Main.java`.
2. Localize a declaração da variável `apiKey`:
   `String apiKey = "SUA_CHAVE";`
3. Substitua o valor `"SUA_CHAVE"` pela sua chave de API gerada no Google Cloud Console.

### Dependências
Certifique-se de que o arquivo `.jar` da biblioteca Gson (versão 2.x.x) está configurado no classpath do seu projeto (em *Project Structure* -> *Libraries* caso utilize IntelliJ sem Maven/Gradle).

### Executando o Projeto
- Compile as classes do diretório `src`.
- Execute a classe `Main`.
- Insira o termo de pesquisa no terminal quando solicitado.

## Fluxo de Dados e Parsing
A aplicação segue a hierarquia complexa de resposta da API do Google:
`JSON Root` -> `items[0]` -> `volumeInfo` -> `Atributos`.

O mapeamento é realizado através da classe `JsonParser` do Gson. Isso permite que a extração seja feita sob demanda a partir do nó `volumeInfo` do primeiro item da lista, garantindo que a estrutura do Record Java permaneça limpa e independente das chaves originais do JSON, evitando a necessidade de espelhar toda a estrutura da API ou utilizar anotações de serialização.