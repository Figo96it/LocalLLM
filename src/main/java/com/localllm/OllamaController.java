package com.localllm;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class OllamaController {

    OllamaApi ollamaApi = new OllamaApi();

    private final ChatClient chatClient;

    public OllamaController(OllamaApi ollamaApi) {
        ChatModel chatModel = new OllamaChatModel(this.ollamaApi, OllamaOptions.create().withModel("llama3.2").withTemperature(0.9));
        this.chatClient = ChatClient.create(chatModel);
    }

    @GetMapping("/hello")
    public ResponseEntity<byte[]> get() {
        BeanOutputConverter<Book> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {
        });

        Book book = chatClient.prompt()
                .system("Jesteś pisarzem, piszesz książki romantyczne")
                .user("Napisz książkę (romans) na przynajmniej 5 rozdziałów")
                .call()
                .entity(new ParameterizedTypeReference<Book>() {
                });

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ebook.pdf");


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Dodanie tytułu książki do PDF
        document.add(new Paragraph("Tytuł: " + book.getTitle()).setBold().setFontSize(18));

        // Dodanie rozdziałów do PDF
        for (Chapter chapter : book.getChapterList()) {
            document.add(new Paragraph("Rozdział: " + chapter.getChapterTitle()).setBold().setFontSize(16));
            document.add(new Paragraph(chapter.getChapterContent()).setFontSize(12));
            document.add(new Paragraph("\n"));
        }

        // Zamknięcie dokumentu
        document.close();

        // Przekonwertowanie ByteArrayOutputStream na tablicę bajtów
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
