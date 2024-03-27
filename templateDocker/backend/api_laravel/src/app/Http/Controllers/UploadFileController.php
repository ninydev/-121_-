<?php

namespace App\Http\Controllers;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class UploadFileController extends Controller
{
    public function __invoke(Request $request): JsonResponse
    {

        if(!$request->hasFile('file')) {
            return response()->json(
                ['message' => 'File not found'], 400
            );
        }

        $file = $request->file('file');
        $path = $file->getClientOriginalName();

        // Оптимальным является выделение корзины в отдельную настройку
        // так удобнее управлять наборами файлов
        Storage::disk('for_files')->put($path, $file->getContent());
        $fileUrl = Storage::disk('for_files')->url($path);

        return response()->json([
            'url' => $fileUrl
    ], 201);

        // Простые методы вызовут путаницу при нарезке приложения на микросервисы и его работу в облаке
        // $file->storeAs($path);
        // Storage::disk('public')->put($path, $file->getContent());
        // Storage::put($path, $file->getContent());

    }
}
