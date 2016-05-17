CREATE TABLE t_song (
    _id         INTEGER PRIMARY KEY,
    tilte       VARCHAR(128),
    album       VARCHAR(128),
    artist      VARCHAR(128),
    url         VARCHAR(128),
    name        VARCHAR(128),
    duration    INTEGER
);
CREATE TABLE t_song_list (
    _id         INTEGER PRIMARY KEY
);