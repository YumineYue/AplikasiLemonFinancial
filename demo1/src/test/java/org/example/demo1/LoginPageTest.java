package org.example.demo1;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;

public class LoginPageTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Buat instance dari aplikasi Anda
        LoginPage app = new LoginPage();
        app.start(stage); // Panggil metode start
    }

    @Test
    void testSuccessfulLogin() {
        // Masukkan username dan password
        clickOn(".text-field").write("admin");
        clickOn(".password-field").write("admin123");

        // Klik tombol login
        clickOn(".button");

        // Tunggu sebentar agar alert muncul
        sleep(500);  // Tunggu agar alert muncul (opsional)

        // Verifikasi alert muncul dengan mencari dialog pane
        Node alertPane = lookup(".alert-pane").query();  // .alert-pane adalah class untuk DialogPane di Alert
        Assertions.assertThat(alertPane).isNotNull();

        // Memastikan konten alert sesuai dengan yang diharapkan
        Node content = lookup(".label").query();  // Label adalah tempat untuk menampilkan teks di dalam DialogPane
        Assertions.assertThat(content).isNotNull();

        String contentText = content.getAccessibleText();  // Dapatkan teks yang ada di dalam Label
        Assertions.assertThat(contentText).isEqualTo("Login Successful!");  // Sesuaikan dengan konten alert Anda
    }

    @Test
    void testFailedLogin() {
        // Masukkan username dan password salah
        clickOn(".text-field").write("wrongUser");
        clickOn(".password-field").write("wrongPass");

        // Klik tombol login
        clickOn(".button");

        // Tunggu sebentar agar alert muncul
        sleep(500);  // Tunggu agar alert muncul (opsional)

        // Verifikasi alert muncul dengan mencari dialog pane
        Node alertPane = lookup(".alert-pane").query();  // .alert-pane adalah class untuk DialogPane di Alert
        Assertions.assertThat(alertPane).isNotNull();

        // Memastikan konten alert sesuai dengan yang diharapkan
        Node content = lookup(".label").query();  // Label adalah tempat untuk menampilkan teks di dalam DialogPane
        Assertions.assertThat(content).isNotNull();

        String contentText = content.getAccessibleText();  // Dapatkan teks yang ada di dalam Label
        Assertions.assertThat(contentText).isEqualTo("Invalid username or password.");
    }
}
