
# Use the official Node.js image as base
FROM node:14-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json files
COPY package.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . /app

RUN npm build


FROM nginx:stable-alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]