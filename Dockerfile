FROM tomcat:10.1-jdk17

COPY target/sudoku.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
