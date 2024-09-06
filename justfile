[group('css')]
css-build:
    tailwindcss -i ./src/main/frontend/main.css -o ./src/main/resources/static/main.css

[group('css')]
css-watch:
    tailwindcss -i ./src/main/frontend/main.css -o ./src/main/resources/static/main.css --watch

[group('css')]
css-minify:
    tailwindcss -i ./src/main/frontend/main.css -o ./src/main/resources/static/main.css --minify