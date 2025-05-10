//package com.hrythym;
//
//import com.hrythym.model.Role;
//import com.hrythym.model.Song;
//import com.hrythym.model.User;
//import com.hrythym.repository.PlaybackLogRepository;
//import com.hrythym.repository.UserRepository;
//import com.hrythym.service.LogService;
//import com.hrythym.service.UserDetailsServiceImpl;
//import com.hrythym.util.JwtTokenProvider;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AppTest {
//
//    JwtTokenProvider jwt;
//
//    @BeforeEach
//    void setup() {
//        jwt = new JwtTokenProvider("testsecretkey1234567890123456789012345678", 8640000L);
//    }
//
//    @Test
//    void shouldLoadUserByUsername() {
//        User user = new User();
//        user.setUsername("testuser");
//        user.setPassword("testpass");
//        user.setRoles(Set.of(Role.ROLE_USER));
//
//        UserRepository mockRepo = mock(UserRepository.class);
//        when(mockRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
//
//        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
//        UserDetails details = service.loadUserByUsername("testuser");
//
//        assertEquals("testuser", details.getUsername());
//        assertEquals("testpass", details.getPassword());
//        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
//    }
//
//    @Test
//    void shouldThrowWhenUserNotFound() {
//        UserRepository mockRepo = mock(UserRepository.class);
//        when(mockRepo.findByUsername("unknown")).thenReturn(Optional.empty());
//
//        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
//        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown"));
//    }
//
//    @Test
//    void shouldLogActionCorrectly() {
//        PlaybackLogRepository mockRepo = mock(PlaybackLogRepository.class);
//        LogService service = new LogService(mockRepo);
//
//        User user = new User();
//        user.setId(1L);
//
//        Song song = new Song();
//        song.setId(101L);
//        song.setGenre("Rock");
//        song.setMood("Energetic");
//
//        service.logAction(user, song, "PLAY");
//
//        verify(mockRepo, times(1)).save(any());
//    }
//
//    @Test
//    void shouldGenerateAndValidateToken() {
//        String token = jwt.generateToken("tester");
//        assertNotNull(token);
//        assertTrue(jwt.validateToken(token));
//        assertEquals("tester", jwt.getUsernameFromToken(token));
//    }
//
//    @Test
//    void shouldInvalidateWrongToken() {
//        JwtTokenProvider wrongJwt = new JwtTokenProvider("differentwrongsecret1234567890abcd", 8640000L);
//        String token = jwt.generateToken("userx");
//        assertFalse(wrongJwt.validateToken(token));
//    }
//
//    @Test
//    void shouldReturnUserDetailsWithRoles() {
//        User user = new User();
//        user.setUsername("john");
//        user.setPassword("pass123");
//        user.setRoles(Set.of(Role.ROLE_USER));
//
//        UserRepository mockRepo = mock(UserRepository.class);
//        when(mockRepo.findByUsername("john")).thenReturn(Optional.of(user));
//
//        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
//        UserDetails details = service.loadUserByUsername("john");
//
//        assertEquals("john", details.getUsername());
//        assertTrue(details.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
//    }
//
//    @Test
//    void shouldLogPlaybackCorrectly() {
//        PlaybackLogRepository mockLogRepo = mock(PlaybackLogRepository.class);
//        LogService logService = new LogService(mockLogRepo);
//
//        User user = new User();
//        user.setId(10L);
//        Song song = new Song();
//        song.setId(101L);
//        song.setGenre("Pop");
//        song.setMood("Chill");
//
//        logService.logAction(user, song, "PLAY");
//
//        verify(mockLogRepo, times(1)).save(argThat(log ->
//                log.getUserId() == 10L &&
//                        log.getSongId() == 101L &&
//                        log.getGenre().equals("Pop") &&
//                        log.getMood().equals("Chill") &&
//                        log.getAction().equals("PLAY")
//        ));
//    }
//
//    @Test
//    void shouldGenerateValidJwt() {
//        JwtTokenProvider jwt = new JwtTokenProvider("mysecretkey1234567890", 100000L);
//        String token = jwt.generateToken("tester");
//
//        assertNotNull(token);
//        assertTrue(jwt.validateToken(token));
//        assertEquals("tester", jwt.getUsernameFromToken(token));
//    }
//
//    @Test
//    void shouldRejectInvalidJwt() {
//        JwtTokenProvider jwt1 = new JwtTokenProvider("secret1", 100000L);
//        JwtTokenProvider jwt2 = new JwtTokenProvider("secret2", 100000L);
//        String token = jwt1.generateToken("tester");
//
//        assertFalse(jwt2.validateToken(token)); // Should fail due to different secret
//    }
//
//    @Test
//    void shouldReturnUserWithoutRoles() {
//        User user = new User();
//        user.setUsername("noRoles");
//        user.setPassword("pwd");
//        user.setRoles(Set.of());
//
//        UserRepository mockRepo = mock(UserRepository.class);
//        when(mockRepo.findByUsername("noRoles")).thenReturn(Optional.of(user));
//
//        UserDetailsServiceImpl service = new UserDetailsServiceImpl(mockRepo);
//        UserDetails details = service.loadUserByUsername("noRoles");
//
//        assertTrue(details.getAuthorities().isEmpty());
//    }
//
//    @Test
//    void shouldHandleNullSongFieldsInLog() {
//        PlaybackLogRepository mockRepo = mock(PlaybackLogRepository.class);
//        LogService logService = new LogService(mockRepo);
//
//        User user = new User(); user.setId(1L);
//        Song song = new Song(); song.setId(99L); // no genre or mood
//
//        logService.logAction(user, song, "PAUSE");
//
//        verify(mockRepo, times(1)).save(any());
//    }
//
//}
