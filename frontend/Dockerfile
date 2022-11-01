#stage 1
FROM node:16-alpine3.15 as node
WORKDIR /app
COPY . .
RUN npm install --legacy-peer-deps
RUN npm run build --prod
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
