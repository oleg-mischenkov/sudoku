# sudoku
The Sudoku Service

The application consists of a server and a front part.
To run the application you need to start two docker containers.

1) To start server:

   - mvn clean package
   - docker build --no-cache -t sudoku-app .
   - docker run -d -p 8080:8080 sudoku-app
   - make a server test (http://localhost:8080/sudoku/)

2) To start front:
    
    - Go to https://github.com/oleg-mischenkov/sudoku-front
    - docker build -t angularjs-app .
    - docker run -p 63342:63342 angularjs-app
    - go to http://localhost:63342/
