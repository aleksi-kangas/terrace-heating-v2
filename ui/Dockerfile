FROM node:26 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM node:26-alpine
RUN addgroup -S heating &&  adduser -S -G heating ui
WORKDIR /app
COPY --from=build --chown=ui:heating /app ./
USER ui
EXPOSE 3000
CMD ["npm", "start"]
