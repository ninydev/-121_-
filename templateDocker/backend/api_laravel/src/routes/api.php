<?php

use App\Http\Controllers\UploadFileController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/


Route::get('laravel/entity', function () {
    return " Hello Laravel";
});

Route::post('laravel/file', UploadFileController::class)->name('api.file.upload');

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
