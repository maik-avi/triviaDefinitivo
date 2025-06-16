package com.example.trivia.controller;

import com.example.trivia.dto.GameCreationRequest;
import com.example.trivia.model.Game;
import com.example.trivia.model.Player;
import com.example.trivia.model.Question;
import com.example.trivia.model.Room;
import com.example.trivia.model.Round;
import com.example.trivia.repository.AnswerRepository;
import com.example.trivia.repository.GameRepository;
import com.example.trivia.repository.PlayerRepository;
import com.example.trivia.repository.QuestionRepository;
import com.example.trivia.repository.RoomRepository;
import com.example.trivia.repository.RoundQuestionRepository;
import com.example.trivia.repository.RoundRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {
    @Mock
    private AnswerRepository answerRepo;
    
    @Mock
    private GameRepository gameRepo;
    
    @Mock
    private PlayerRepository playerRepo;
    
    @Mock
    private QuestionRepository questionRepo;
    
    @Mock
    private RoomRepository roomRepo;
    
    @Mock
    private RoundQuestionRepository roundQuestionRepo;
    
    @Mock
    private RoundRepository roundRepo;
    
    @InjectMocks
    private GameController controller;
    
    private Game testGame;
    private Room testRoom;
    private Player testPlayer;
    private Question testQuestion;
    private Round testRound;
    
    @BeforeEach
    void setUp() {
        testGame = new Game();
        testGame.setGameId(1);
        testGame.setRoomId(1);
        testGame.setStartedAt(OffsetDateTime.from(Instant.now()));
        
        testRoom = new Room();
        testRoom.setRoomId(1);
        testRoom.setHostId(1);
        
        testPlayer = new Player();
        testPlayer.setPlayerId(1);
        testPlayer.setRoomId(1);
        testPlayer.setUsername("testUser");
        
        testQuestion = new Question();
        testQuestion.setQuestionId(1);
        testQuestion.setType("multiple_choice");
        testQuestion.setType("Test question");
        
        testRound = new Round();
        testRound.setRoundId(1);
        testRound.setGameId(1);
        testRound.setRoundNumber(1);
        testRound.setStartedAt(OffsetDateTime.from(Instant.now()));
        testRound.setEndedAt(OffsetDateTime.from(Instant.now().plus(Duration.ofMinutes(10))));
    }
    
    @Test
    void getGames_returnsGamesList() throws Exception {
        Game game1 = new Game();
        game1.setGameId(1);
        game1.setRoomId(1);
        
        Game game2 = new Game();
        game2.setGameId(2);
        game2.setRoomId(1);
        
        Page<Game> gamesPage = new PageImpl<>(Arrays.asList(game1, game2), PageRequest.of(0, 10), 2);
        when(gameRepo.findByRoomId(1L, PageRequest.of(0, 10))).thenReturn(gamesPage);
        
        ResponseEntity<List<Game>> response = controller.getGames(1L, 0, 10);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(gameRepo).findByRoomId(1L, PageRequest.of(0, 10));
    }
    
    @Test
    void createGame_createsNewGameAndReturns201() {
        GameCreationRequest request = new GameCreationRequest(1L, 2, 60, 0);
        
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("1")).thenReturn(1L);
        
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        Game savedGame = new Game();
        savedGame.setGameId(1);
        savedGame.setRoomId(1);
        savedGame.setStartedAt(OffsetDateTime.from(Instant.now()));
        savedGame.setEndedAt(savedGame.getStartedAt().plus(Duration.ofMinutes(120))); // 2 rounds * 60 seconds
        when(gameRepo.save(any(Game.class))).thenReturn(savedGame);
        
        ResponseEntity<Game> response = controller.createGame(request, session);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedGame.getGameId(), response.getBody().getGameId());
        assertEquals(1, response.getBody().getRoomId());
        assertNotNull(response.getBody().getStartedAt());
        assertNotNull(response.getBody().getEndedAt());
        
        verify(gameRepo).save(any(Game.class));
        verify(roundRepo, times(2)).save(any(Round.class));
        verify(session).getAttribute("1");
    }
    
    @Test
    void createGame_throws401WhenNotAuthenticated() {
        GameCreationRequest request = new GameCreationRequest(1L, 2, 60, 3);
        
        HttpSession session = mock(HttpSession.class);
        
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.createGame(request, session);
        });
        
        verify(roomRepo).findById(1L);
    }
    
    @Test
    void createGame_throws403WhenNotHost() {
        GameCreationRequest request = new GameCreationRequest(1L, 2, 60, 3);
        
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("1")).thenReturn(2L);
        
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.createGame(request, session);
        });
        
        verify(roomRepo).findById(1L);
    }
    
    @Test
    void getGame_returnsGameWhenFound() {
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        
        ResponseEntity<Game> response = controller.getGame(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testGame, response.getBody());
        verify(gameRepo).findById(1L);
    }
    
    @Test
    void getGame_throws404WhenGameNotFound() {
        when(gameRepo.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.getGame(1L);
        });
        
        verify(gameRepo).findById(1L);
    }
    
    @Test
    void deleteGame_deletesGameWhenHost() {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("1")).thenReturn(1L);
        
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        ResponseEntity<Void> response = controller.deleteGame(1L, session);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(gameRepo).deleteById(1L);
    }
    
    @Test
    void deleteGame_throws401WhenNotAuthenticated() {
        HttpSession session = mock(HttpSession.class);
        
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.deleteGame(1L, session);
        });
        
        verify(gameRepo).findById(1L);
        verify(roomRepo).findById(1L);
    }
    
    @Test
    void deleteGame_throws403WhenNotHost() {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("1")).thenReturn(2L);
        
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(testRoom));
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.deleteGame(1L, session);
        });
        
        verify(gameRepo).findById(1L);
        verify(roomRepo).findById(1L);
    }
    
    @Test
    void getRounds_returnsRoundsList() {
        Round round1 = new Round();
        round1.setRoundId(1);
        round1.setGameId(1);
        round1.setRoundNumber(1);
        
        Round round2 = new Round();
        round2.setRoundId(2);
        round2.setGameId(1);
        round2.setRoundNumber(2);
        
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roundRepo.findByGameId(1L)).thenReturn(Arrays.asList(round1, round2));
        
        ResponseEntity<List<Round>> response = controller.getRounds(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(roundRepo).findByGameId(1L);
    }
    
    @Test
    void getRoundQuestions_returnsQuestionsList() {
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roundRepo.findById(1L)).thenReturn(Optional.of(testRound));
        when(questionRepo.findByRoundId(1L)).thenReturn(List.of(testQuestion));
        
        ResponseEntity<List<Question>> response = controller.getRoundQuestions(1L, 1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(questionRepo).findByRoundId(1L);
    }
    
    @Test
    void getRoundQuestions_throws403WhenRoundNotStarted() {
        Round round = new Round();
        round.setRoundId(1);
        round.setGameId(1);
        round.setRoundNumber(1);
        round.setStartedAt(OffsetDateTime.from(Instant.now().plus(Duration.ofMinutes(10))));
        
        when(gameRepo.findById(1L)).thenReturn(Optional.of(testGame));
        when(roundRepo.findById(1L)).thenReturn(Optional.of(round));
        
        assertThrows(ResponseStatusException.class, () -> {
            controller.getRoundQuestions(1L, 1L);
        });
        
        verify(gameRepo).findById(1L);
        verify(roundRepo).findById(1L);
    }
}
