package application.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by kkurowska on 18.01.2017.
 */


public class PasswordEncoderImpl implements PasswordEncoder {

    private ShaPasswordEncoder passwordEncoder;

    public PasswordEncoderImpl (ShaPasswordEncoder shaPasswordEncoder) {
        this.passwordEncoder = shaPasswordEncoder;
    }

    @Override
    public String encode(CharSequence plainPassword) {
        return passwordEncoder.encodePassword(plainPassword.toString(), null);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return passwordEncoder.isPasswordValid(s, charSequence.toString(), null);
    }
}
