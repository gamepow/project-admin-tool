use defaultdb;

-- Create the user table with user_rol as a foreign key
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL
);

insert into user
(username,password,roles)
values
('test','$2a$12$.77Rq/4aVblETmPbk8cuCO7fKbtZpfUhVOQTKyxPpmZO1ZumhuHaO','')


