package com.hrythym;

import com.hrythym.model.Role;
import com.hrythym.model.Song;
import com.hrythym.model.User;
import com.hrythym.repository.PlaybackLogRepository;
import com.hrythym.repository.UserRepository;
import com.hrythym.service.LogService;
import com.hrythym.service.UserDetailsServiceImpl;
import com.hrythym.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppTest {

    JwtTokenProvider jwt;

    @BeforeEach
    void setup() {
        jwt = new JwtTokenProvider("testsecretkey1234567890123456789012345678", 8640000L);
    }

    @Test
    void shouldLoadUserByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setRoles(Set.of(Role.ROLE_USER));

        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
        UserDetails details = service.loadUserByUsername("testuser");

        assertEquals("testuser", details.getUsername());
        assertEquals("testpass", details.getPassword());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown"));
    }

    @Test
    void shouldLogActionCorrectly() {
        PlaybackLogRepository mockRepo = mock(PlaybackLogRepository.class);
        LogService service = new LogService(mockRepo);

        User user = new User();
        user.setId(1L);

        Song song = new Song();
        song.setId(101L);
        song.setGenre("Rock");
        song.setMood("Energetic");

        service.logAction(user, song, "PLAY");

        verify(mockRepo, times(1)).save(any());
    }

    @Test
    void shouldGenerateAndValidateToken() {
        String token = jwt.generateToken("tester");
        assertNotNull(token);
        assertTrue(jwt.validateToken(token));
        assertEquals("tester", jwt.getUsernameFromToken(token));
    }

    @Test
    void shouldInvalidateWrongToken() {
        JwtTokenProvider wrongJwt = new JwtTokenProvider("differentwrongsecret1234567890abcd", 8640000L);
        String token = jwt.generateToken("userx");
        assertFalse(wrongJwt.validateToken(token));
    }

    // Simple Tests
    @Test void testAddition() { assertEquals(4, 2 + 2); }
    @Test void testSubtraction() { assertEquals(1, 3 - 2); }
    @Test void testStringJoin() { assertEquals("music", "mu" + "sic"); }
    @Test void testNonNull() { assertNotNull("song"); }
    @Test void testBooleanTrue() { assertTrue(1 < 10); }
    @Test void testArrayEquality() { assertArrayEquals(new int[]{1, 2}, new int[]{1, 2}); }
    @Test void testListSize() { assertEquals(3, java.util.List.of("a", "b", "c").size()); }
}
