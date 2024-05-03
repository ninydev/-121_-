const axios = require('axios');
require('dotenv').config();

const url = 'https://api.ximilar.com/removebg/fast/removebg';
const token = process.env.XIMILAR_TOKEN;

const data = {
    records: [
        {
            _url: 'https://ninydev.com/wp-content/uploads/2024/05/blonda2-scaled.jpg',
            binary_mask: false,
            white_background: false
        }
    ]
};

const config = {
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Token ${token}`
    }
};

axios.post(url, data, config)
    .then(response => {
        console.log('Response:', response.data);
    })
    .catch(error => {
        console.error('Error:', error);
    });