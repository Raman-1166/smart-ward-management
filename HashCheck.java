import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("password");
        System.out.println("Hash for 'password': " + hash);
        System.out.println("Matches: " + encoder.matches("password", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07dvq7zuyS+dzC2WC"));
    }
}
