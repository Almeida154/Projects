<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

use App\Http\Controllers\Api\DrinkControllerApi;

Route::namespace('Api')->group(function(){
    Route::get('/drinks', [DrinkControllerApi::class, 'listAll']);
    Route::get('/drinks/{id}', [DrinkControllerApi::class, 'singleDrink']);

    Route::post('/drinks', [DrinkControllerApi::class, 'add']);
    Route::put('/drinks/{id}', [DrinkControllerApi::class, 'update']);
    Route::delete('/drinks/{id}', [DrinkControllerApi::class, 'delete']);
});