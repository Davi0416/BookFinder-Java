import dados.ConverterVar;
import dados.Livro;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Digite o nome do livro:");
        var nomeEscolhido = reader.nextLine();

        String apiKey =  "SUA_CHAVE";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/books/v1/volumes?q=" +
                        nomeEscolhido.replaceAll("\\s+", "+") + "&maxResults=1&key=" + apiKey))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        var jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();

        if (jsonObject.has("items")) {
            var item = jsonObject.getAsJsonArray("items").get(0).getAsJsonObject();
            var info = item.getAsJsonObject("volumeInfo");


            ConverterVar dadosConvertidos = new ConverterVar(
                    info.get("title").getAsString(),
                    info.has("authors") ? info.get("authors").getAsJsonArray().get(0).getAsString() : "Autor desconhecido",
                    info.has("publishedDate") ? info.get("publishedDate").getAsString() : "Data desconhecida"
            );

            Livro meuLivro = new Livro(dadosConvertidos);
            System.out.println(meuLivro);

        } else {
            System.out.println("Nenhum livro encontrado para essa pesquisa.");
        }
    }
}