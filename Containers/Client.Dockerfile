FROM node:latest
RUN npm install -g http-server
WORKDIR /webapp
COPY ./fhirclient/package*.json ./
RUN npm install
COPY ./fhirclient ./
RUN npm run build
EXPOSE 8080
CMD ["http-server", "dist"]
