FROM node:latest
RUN yard add global http-server
WORKDIR /webapp
COPY ../fhirclient/package*.json ./
RUN yarn install
COPY ../fhirclient ./
RUN yarn build
EXPOSE 8080
CMD ["http-server", "dist"]
