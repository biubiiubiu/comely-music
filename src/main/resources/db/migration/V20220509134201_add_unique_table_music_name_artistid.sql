ALTER TABLE music
    ADD CONSTRAINT `music_name_artistid` UNIQUE (`name`,`artist_id`);