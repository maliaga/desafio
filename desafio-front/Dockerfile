#stage 1
FROM node:14.5.0-alpine as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/carrito /usr/share/nginx/html