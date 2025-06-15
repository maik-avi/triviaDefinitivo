-- Cada room tendrá su id, una url o código y además se declarará cuando fue creada la room.
CREATE TABLE rooms (
    room_id SERIAL PRIMARY KEY,
    --La URL, se tendrá que usar en JOIN ROOM
    url VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Cada jugador tendrá su id, se relaciona con la room_id, cada jugador tendrá un nombre,
-- se tendrá que comprobar si el jugador es el host o no,
-- y además que en una room solamente se podrá tener usernames distintos entre sí
CREATE TABLE players (
    player_id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    username VARCHAR(255) NOT NULL,
    is_host BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(room_id, username)
);


-- Cada equipo tendrá su id, estará asociado a una sala mediante room_id,
-- y tendrá un nombre. Se asume que los nombres de equipos pueden repetirse entre distintas salas.
CREATE TABLE teams (
    team_id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- Cada partida tendrá su id, estará asociada a una sala mediante room_id,
-- y almacenará la configuración del juego: número de rondas, preguntas por ronda,
-- dificultad de las preguntas, y los timestamps de inicio y fin de la partida.
CREATE TABLE games (
    game_id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    rounds INTEGER NOT NULL,
    questions_per_round INTEGER NOT NULL,
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    started_at TIMESTAMPTZ DEFAULT NOW(),
    ended_at TIMESTAMPTZ
);


-- Cada ronda tendrá su id, estará relacionada con una partida mediante game_id,
-- y se identificará con un número de ronda (round_number).
-- También puede almacenar el momento en que comenzó y terminó la ronda.
CREATE TABLE rounds (
    round_id SERIAL PRIMARY KEY,
    game_id INTEGER NOT NULL REFERENCES games(game_id) ON DELETE CASCADE,
    round_number INTEGER NOT NULL,
    started_at TIMESTAMPTZ,
    ended_at TIMESTAMPTZ
);


-- Cada pregunta tendrá su id, estará relacionada con una ronda mediante round_id,
-- tendrá un tipo (opción múltiple, respuesta corta o buzzer),
-- una dificultad (de 1 a 3), un posible enlace multimedia,
-- y campos para las opciones (si aplica) y las respuestas correctas.
CREATE TABLE questions (
    question_id SERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL CHECK (type IN ('multiple_choice', 'short_answer', 'buzzer')),
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    media_url TEXT,
    options TEXT[],
    correct_answers TEXT[]
);

CREATE TABLE round_questions (
    round_id INTEGER NOT NULL REFERENCES rounds(round_id) ON DELETE CASCADE,
    question_id INTEGER NOT NULL REFERENCES questions(question_id) ON DELETE CASCADE,
    PRIMARY KEY (round_id, question_id)
);


-- Cada respuesta tendrá su id, estará relacionada con una pregunta y un jugador mediante question_id y player_id,
-- registrará cuándo se envió la respuesta, el contenido de la respuesta,
-- si fue correcta o no, y los puntos obtenidos por esa respuesta.
CREATE TABLE answers (
    answer_id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL REFERENCES questions(question_id) ON DELETE CASCADE,
    player_id INTEGER NOT NULL REFERENCES players(player_id) ON DELETE CASCADE,
    submitted_at TIMESTAMPTZ DEFAULT NOW(),
    answer TEXT,
    correct BOOLEAN,
    points_awarded INT DEFAULT 0
);


-- Esta tabla registra la puntuación obtenida por cada equipo en una partida.
-- La clave primaria está compuesta por team_id y game_id, indicando a qué equipo y partida corresponde.
-- Se guarda el número total de puntos obtenidos por el equipo en dicha partida.
CREATE TABLE team_scores (
    team_id INTEGER NOT NULL REFERENCES teams(team_id) ON DELETE CASCADE,
    game_id INTEGER NOT NULL REFERENCES games(game_id) ON DELETE CASCADE,
    points INTEGER DEFAULT 0,
    PRIMARY KEY (team_id, game_id)
);
