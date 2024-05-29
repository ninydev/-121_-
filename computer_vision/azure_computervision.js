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
    'DenseCaptions',
    'Objects',
    'People',
    'Read',
    'SmartCrops',
    'Tags'
];

async function getImageAllFromUrl() {
    const result =
        await client.path('/imageanalysis:analyze').post({
        body: {
            url: imageUrl
        },
        queryParameters: {
            features: features,
            'language': 'en',
            'gender-neutral-captions': 'true',
            'smartCrops-aspect-ratios': [0.9, 1.33]
        },
        contentType: 'application/json'
    });

    const iaResult = result.body;

    console.log(`Model Version: ${iaResult.modelVersion}`);
    console.log(`Image Metadata: ${JSON.stringify(iaResult.metadata)}`);
    if (iaResult.captionResult) {
        console.log(`Caption: ${iaResult.captionResult.text} (confidence: ${iaResult.captionResult.confidence})`);
    }
    if (iaResult.denseCaptionsResult) {
        iaResult.denseCaptionsResult.values.forEach(denseCaption =>
            console.log(`Dense Caption: ${JSON.stringify(denseCaption)}`));
    }
    if (iaResult.objectsResult) {
        iaResult.objectsResult.values.forEach(object =>
            console.dir(object, {depth: null, colors: true}));
    }
    if (iaResult.peopleResult) {
        iaResult.peopleResult.values.forEach(person =>
            console.dir(person, {depth: null, colors: true}));
    }
    if (iaResult.readResult) {
        iaResult.readResult.blocks.forEach(block =>
            console.dir(block, {depth: null, colors: true}));
    }
    if (iaResult.smartCropsResult) {
        iaResult.smartCropsResult.values.forEach(smartCrop =>
            console.dir(smartCrop, {depth: null, colors: true}));
    }

    console.log("------------------------- TAGS ------------------------- ")
    if (iaResult.tagsResult) {
        iaResult.tagsResult.values.forEach(tag =>
            console.dir(tag, {depth: null, colors: true}));
    }
}

async function analyzeImageFromUrl() {
    const result =
        await client.path('/imageanalysis:analyze').post({
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
        console.dir(
            `Caption: ${iaResult.captionResult.text} (confidence: ${iaResult.captionResult.confidence})`);
    }
    if (iaResult.readResult) {
        iaResult.readResult.blocks.forEach(block =>
            console.dir(block, {depth: null, colors: true}));
    }
}

getImageAllFromUrl();