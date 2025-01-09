FROM node:20.11.1
WORKDIR /angularDocker
COPY . /angularDocker
RUN npm install -g @angular/cli
RUN npm install
ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]
