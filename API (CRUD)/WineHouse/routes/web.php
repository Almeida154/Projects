<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

use App\Http\Controllers\DrinkController;

Route::get('/', [DrinkController::class, 'index']);
Route::get('/home', [DrinkController::class, 'index']);

Route::get('/add', [DrinkController::class, 'newDrink']);
Route::get('/edit/{id}', [DrinkController::class, 'editDrink']);

Route::post('/add', [DrinkController::class, 'add']);
Route::post('/edit/{id}', [DrinkController::class, 'update']);

Route::get('/delete/{id}', [DrinkController::class, 'delete']);
