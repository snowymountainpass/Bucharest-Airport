# Use the official Node.js image as base
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json files
COPY package.json ./
COPY package-lock.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the application code
COPY . .

# Expose the frontend port
EXPOSE 3000

# Command to start the application in development mode
CMD ["npm", "run", "dev", "--", "--host"]


