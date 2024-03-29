CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       last_login_time_utc TIMESTAMP
);

CREATE TABLE user_quota (
                           id VARCHAR(36) NOT NULL,
                           count BIGINT NOT NULL,
                           user_id VARCHAR(36),
                           PRIMARY KEY (id),
                           FOREIGN KEY (user_id) REFERENCES users (id)
);


INSERT INTO users (id, first_name, last_name, last_login_time_utc)
VALUES ('9337ac4b-108f-47d3-8c2e-4fb5287f7e0e', 'John', 'Doe', '2022-01-01T09:00:00');

INSERT INTO user_quota (id, count, user_id)
VALUES ('b5aac576-aafc-4eae-b83b-e7f3a7531f05', 5, '9337ac4b-108f-47d3-8c2e-4fb5287f7e0e');
