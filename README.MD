# Development Setup

1. Use JDK 21
2. Install `tailwindcss` CLI
3. Install `just`
4. Enable LiveReload extension in Chrome for Hot Swap

# Development

Use recipes defined in the `justfile`.

## CSS

`Tailwind` is used as a CSS framework, there is a group of `css` recipes for that. Use `css-watch` for generating the target css file on the fly.

Edit the `main.css` in `src/main/frontend` - it will be compiled directly into `main.css` in `src/main/resources/static` to be served with the website

## Frontend

`Thymeleaf` is used as the templating library for server-side HTML rendering.

## Backend

Standard Spring Boot with a small Database for tracking user sessions