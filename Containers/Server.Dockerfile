FROM tomcat:9.0
COPY ../FHIRestful/target/fhirserver-5.0.0.war /usr/local/tomcat/webapps
EXPOSE 4000
CMD ["catalina.sh", "run"]
