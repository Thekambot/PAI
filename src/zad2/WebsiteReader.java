package zad2;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class WebsiteReader {

    private static HttpResponse<String> getHTMLContent(String websiteURL) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(websiteURL)).build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String getBodyFromResponse(HttpResponse<String> response) {
        return response.body();
    }

    private static void fillString(StringBuilder to, String from) { to.append(from).append("\n"); }

    private static String filterHTMLForUrls(HttpResponse<String> response) {

        String body = getBodyFromResponse(response);

        final String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
        StringBuilder urls = new StringBuilder();

        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        pattern.matcher(body).results().map(MatchResult::group).forEach(url -> {
            fillString(urls, url);
        });

        return urls.toString();
    }

    private static String filterHTMLForEmails(HttpResponse<String> response) {

        String body = getBodyFromResponse(response);

        final String emailRegex = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
        StringBuilder emails = new StringBuilder();

        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        pattern.matcher(body).results().map(MatchResult::group).forEach(email -> {
            fillString(emails, email);
        });

        return emails.toString();
    }

    private static String getHeaders(HttpResponse<String> response) {
        return response.headers().toString();
    }

    private static String getHeadFromHTML(HttpResponse<String> response) {

        String body = getBodyFromResponse(response);

        return body.substring(body.indexOf("<head>"), body.indexOf("</head>"));
    }

    private static void saveToFile(String filename, String data) throws IOException {

        FileWriter writer = new FileWriter(filename);
        writer.write(data);
        writer.close();
    }

    public static void main(String[] args) {

        try {
            HttpResponse<String> response = getHTMLContent(args[0]);

            String links = filterHTMLForUrls(response);
            String emails = filterHTMLForEmails(response);
            String headers = getHeaders(response);
            String headContent = getHeadFromHTML(response);

            System.out.print(links);
            System.out.print(emails);

            saveToFile("getHeaders.txt", headers);
            saveToFile("HTMLHead.txt", headContent);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
