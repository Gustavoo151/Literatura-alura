package com.alura.literatura.service;

import com.alura.literatura.entity.Author;
import com.alura.literatura.entity.Book;
import com.alura.literatura.repository.AuthorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GutendexService {

    private static final String API_URL = "https://gutendex.com/books/";

    @Autowired
    private AuthorRepository authorRepository;

    public Optional<Book> fetchBookByTitle(String query) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(API_URL + "?search=" + query);
        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode resultsNode = rootNode.path("results");

        if (resultsNode.isArray() && resultsNode.size() > 0) {
            JsonNode firstResult = resultsNode.get(0);
            Book book = mapper.treeToValue(firstResult, Book.class);

            // Consider only the first author
            JsonNode authorsNode = firstResult.path("authors");
            if (authorsNode.isArray() && authorsNode.size() > 0) {
                JsonNode firstAuthorNode = authorsNode.get(0);
                Author author = mapper.treeToValue(firstAuthorNode, Author.class);
                author = authorRepository.save(author);
                book.setAuthor(author);
            }

            httpClient.close();
            return Optional.of(book);
        }

        httpClient.close();
        return Optional.empty();
    }
}
