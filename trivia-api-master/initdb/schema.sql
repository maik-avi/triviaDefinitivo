-- Cada room tendrá su id, una url o código y además se declarará cuando fue creada la room.
CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Cada jugador tendrá su id, se relaciona con la room_id, cada jugador tendrá un nombre,
-- se tendrá que comprobar si el jugador es el host o no,
-- y además que en una room solamente se podrá tener usernames distintos entre sí
CREATE TABLE players (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    username VARCHAR(255) NOT NULL,
    is_host BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(room_id, username)
);

CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    rounds INTEGER NOT NULL,
    questions_per_round INTEGER NOT NULL,
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    started_at TIMESTAMPTZ DEFAULT NOW(),
    ended_at TIMESTAMPTZ
);

CREATE TABLE rounds (
    id SERIAL PRIMARY KEY,
    game_id INTEGER NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    round_number INTEGER NOT NULL,
    started_at TIMESTAMPTZ,
    ended_at TIMESTAMPTZ
);

CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    round_id INTEGER NOT NULL REFERENCES rounds(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('multiple_choice', 'short_answer', 'buzzer')),
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    media_url TEXT,
    options TEXT[],
    correct_answers TEXT[]
);

CREATE TABLE answers (
    id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    player_id INTEGER NOT NULL REFERENCES players(id) ON DELETE CASCADE,
    submitted_at TIMESTAMPTZ DEFAULT NOW(),
    answer TEXT,
    correct BOOLEAN,
    points_awarded INT DEFAULT 0
);

CREATE TABLE team_scores (
    team_id INTEGER NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    game_id INTEGER NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    points INTEGER DEFAULT 0,
    PRIMARY KEY (team_id, game_id)
);
