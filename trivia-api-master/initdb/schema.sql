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

