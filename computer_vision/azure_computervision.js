const axios = require('axios');
require('dotenv').config();

const AZURE_COMPUTER_VISION_END_POINT= process.env.AZURE_COMPUTER_VISION_END_POINT
const AZURE_COMPUTER_VISION_KEY=process.env.AZURE_COMPUTER_VISION_KEY
const AZURE_COMPUTER_VISION_REGION=process.env.AZURE_COMPUTER_VISION_REGION

const { ImageAnalysisClient } = require('@azure-rest/ai-vision-image-analysis');
const createClient = require('@azure-rest/ai-vision-image-analysis').default;
const { AzureKeyCredential } = require('@azure/core-auth');

const credential = new AzureKeyCredential(AZURE_COMPUTER_VISION_KEY);
const client = createClient(AZURE_COMPUTER_VISION_END_POINT, credential);

const imageUrl = 'https://learn.microsoft.com/azure/ai-services/computer-vision/media/quickstarts/presentation.png';
const features = [
    'Caption',
    'Read'
];

async function analyzeImageFromUrl() {
    const result = await client.path('/imageanalysis:analyze').post({
        body: {
            url: imageUrl
        },
        queryParameters: {
            features: features
        },
        contentType: 'application/json'
    });

    const iaResult = result.body;

    if (iaResult.captionResult) {
        console.log(`Caption: ${iaResult.captionResult.text} (confidence: ${iaResult.captionResult.confidence})`);
    }
    if (iaResult.readResult) {
        iaResult.readResult.blocks.forEach(block => console.log(`Text Block: ${JSON.stringify(block)}`));
    }
}

analyzeImageFromUrl();