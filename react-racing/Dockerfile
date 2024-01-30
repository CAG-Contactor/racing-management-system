# build environment
FROM node:20-alpine AS build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm ci --silent
RUN npm install react-scripts@3.4.1 -g --silent
COPY . ./
ENV NODE_OPTIONS=--openssl-legacy-provider
RUN npm run build

# production environment
FROM nginx:stable-alpine
# CVE fix for NGINX
RUN apk --no-cache add openssl>=3.0.12-r0
RUN apk --no-cache upgrade libx11

COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["sh", "-c", "cd /usr/share/nginx/html/ && nginx -g 'daemon off;'"]
