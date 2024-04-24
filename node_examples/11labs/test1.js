const fs = require('fs');
require('dotenv').config()
filePath= './res.mp3'
let text = "Hello"
let voice_settings = {
    stability: 0.5,
    similarity_boost:0.5,
    style: 0.5,
    use_speaker_boost: false
}

let body = {text, voice_settings}

console.log(body)

const options = {
    method: 'POST',
    headers: {
        'xi-api-key': process.env.LABS_KEY,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
};


console.log(options)

fetch('https://api.elevenlabs.io/v1/text-to-speech/RzI0GdCJKAD9xMkuY0j8', options)
    .then(response => {
        // вместо response.json() и других методов
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const reader = response.body.getReader();
        const fileStream = fs.createWriteStream(filePath);

        // Функция для рекурсивного чтения данных из потока и записи в файл
        function pump() {
            reader.read().then(({ done, value }) => {
                if (done) {
                    fileStream.end();
                    return;
                }
                fileStream.write(Buffer.from(value));
                pump();
            }).catch((err) => {
                console.log("pump err")
                console.log (err)
            });
        }

        pump(); // Запускаем процесс чтения данных и записи в файл

    })
    .catch(err => console.error(err));