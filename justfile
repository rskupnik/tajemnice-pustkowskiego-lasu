[group('css')]
css-build:
    cd src/main/frontend && tailwindcss -i main.css -o ../resources/static/main.css

[group('css')]
css-watch:
    cd src/main/frontend && tailwindcss -i main.css -o ../resources/static/main.css --watch

[group('css')]
css-minify:
    cd src/main/frontend && tailwindcss -i main.css -o ../resources/static/main.css --minify