CREATE TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    subscriber_id INT NOT NULL,
    subscribed_to_id INT NOT NULL,
    FOREIGN KEY (subscriber_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (subscribed_to_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT unique_subscription UNIQUE (subscriber_id, subscribed_to_id)
);