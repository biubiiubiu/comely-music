ALTER TABLE entity_tag
    ADD CONSTRAINT `tag_entityid_tagname` UNIQUE (`entity_id`,`tag_name`);