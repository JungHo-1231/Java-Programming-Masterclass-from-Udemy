package Section20.APIS;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            URI uri = new URI("db://username:password@myserver.com:5000/catalogue/phones?os=android#samsung");

            URL url = uri.toURL();
//            System.out.println("url = " + url);
            System.out.println("uri.getScheme() = " + uri.getScheme());
            System.out.println("uri.getSchemeSpecificPart() = " + uri.getSchemeSpecificPart());
            System.out.println("uri.getAuthority() = " + uri.getAuthority());
            System.out.println("uri.getPort() = " + uri.getPort());
            System.out.println("uri.getPath() = " + uri.getPath());
            System.out.println("uri.getQuery() = " + uri.getQuery());
            System.out.println("uri.getFragment() = " + uri.getFragment());

        } catch (URISyntaxException e) {
            System.out.println("URI Bad Syntax : "  + e.getMessage());
        } catch (MalformedURLException e) {
            System.out.println("URL Malformed : " + e.getMessage());
        }
    }
}
