# Use a more recent version of Node.js
FROM node:18 AS build
WORKDIR /app
COPY ./package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Use Nginx to serve the Angular application
FROM nginx:alpine
COPY --from=build /app/dist/transaction-ui /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
