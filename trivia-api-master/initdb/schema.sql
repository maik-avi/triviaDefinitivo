--Cada room tendrá su id, una url o código y además se declarará cuando fue creada la room.

CREATE TABLE rooms (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    url VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

--Cada jugador tendrá su id, se relaciona con la room_id, cada jugador tendrá un nombre, se tendrá que comprobar
--si el jugador es el host o no, y además que en una room solamente se podrá tener usernames distintos entre sí

CREATE TABLE players (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    username VARCHAR(255) NOT NULL,
    is_host BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(room_id, username)
);

CREATE TABLE teams (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES rooms(id),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE games (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES rooms(id),
    rounds INTEGER NOT NULL,
    questions_per_round INTEGER NOT NULL,
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    started_at TIMESTAMPTZ DEFAULT NOW(),
    ended_at TIMESTAMPTZ
);


CREATE TABLE rounds (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    game_id BIGINT NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    round_number INTEGER NOT NULL,
    started_at TIMESTAMPTZ,
    ended_at TIMESTAMPTZ
);

CREATE TABLE questions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    round_id BIGINT NOT NULL REFERENCES rounds(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('multiple_choice', 'short_answer', 'buzzer')),
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 3),
    media_url TEXT,
    options TEXT[],
    correct_answers TEXT[]
);