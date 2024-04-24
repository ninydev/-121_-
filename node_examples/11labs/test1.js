const options = {
    method: 'POST',
    headers: {
        'xi-api-key': 'a2480aeb2570c2ccfe0e6e718b5ba99f',
        'Content-Type': 'application/json'
    },
    body: '{"text":"В очах твоїх я бачу край небес, Там вітер вінок зірок розплітає. Ти, моя дівчино, як тополь зелений, Що серце моє легко забуває.","voice_settings":{"stability":0.123,"similarity_boost":0.123,"style":0.123,"use_speaker_boost":true}}'
};

fetch('https://api.elevenlabs.io/v1/text-to-speech/RzI0GdCJKAD9xMkuY0j8', options)
    .then(response => response.json())
    .then(response => console.log(response))
    .catch(err => console.error(err));