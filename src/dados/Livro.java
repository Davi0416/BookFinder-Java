package dados;

public class Livro {
    String nomeLivro;
    String nomeAutor;
    String dataLancamento;

    public Livro(ConverterVar livroConvertido) {

        this.nomeLivro = livroConvertido.title();
        this.nomeAutor = livroConvertido.authors();
        this.dataLancamento = livroConvertido.publishedDate();
    }

    @Override
    public String toString() {
        return "Nome do livro= " + nomeLivro +
                "\nNome do autor= " + nomeAutor +
                "\nData de lançamento: =" + dataLancamento;
    }
}