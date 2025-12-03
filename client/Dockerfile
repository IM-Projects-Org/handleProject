# ---------- Stage 1 : Build Angular App ----------
FROM node:18-alpine AS build

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy entire Angular project
COPY . .

# Build Angular production build
RUN npm run build --prod

# ---------- Stage 2 : Serve with Nginx ----------
FROM nginx:1.25-alpine

# Remove default nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy Angular dist build
COPY --from=build /app/dist/* /usr/share/nginx/html/

# Copy custom nginx config (optional)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
